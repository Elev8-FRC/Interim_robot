// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TurretNew extends SubsystemBase {
  public TalonFX turret = new TalonFX(54);
  public CANcoder encoder1 = new CANcoder(60);
  public CANcoder encoder2 = new CANcoder(61);
  public double t_teeth = 100;
  public double e1_teeth = 21;
  public double e2_teeth = 22;
  public double ratio1 = e1_teeth / t_teeth;
  public double ratio2 = e2_teeth / t_teeth;

  public TurretNew() {

  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("pos", getTurretPos());
    SmartDashboard.putNumber("pos1", getEncoder1Pos()*360);
    SmartDashboard.putNumber("pos2", getEncoder2Pos()*360);
    SmartDashboard.putNumber("turret_pos", turret.getPosition().getValueAsDouble()*360);
    
  }

  public double getEncoder1Pos() {
    return encoder1.getAbsolutePosition().getValueAsDouble();

  }

  public double getEncoder2Pos() {
    return encoder2.getAbsolutePosition().getValueAsDouble();

  }
  public void turretPosInit() {
    turret.setPosition(getTurretPos());
  }

  public double getTurretPos() {
    double p1 = getEncoder1Pos();
    double p2 = getEncoder2Pos();

    for (int i = 0; i < e2_teeth; i++) {
      double r1 = (i + p1) * ratio1;
      for (int j = 0; j < e1_teeth; j++) {
        double r2 = (j + p2) * ratio2;
        if (Math.abs(r1 - r2) < 0.05) {
          return r1;
        }
      }
    }
    return 0;
  }
}