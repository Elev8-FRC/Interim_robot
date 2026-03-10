// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Rotations;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicDutyCycle;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

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
  public double actually_teeth = 306;
  public MotionMagicDutyCycle motionMagicControl;
  public TurretNew() {
    motionMagicControl = new MotionMagicDutyCycle(0);
    TalonFXConfiguration TurretTalonConfigs = new TalonFXConfiguration();
    TurretTalonConfigs.MotorOutput.withPeakForwardDutyCycle(0.6);
    TurretTalonConfigs.MotorOutput.withPeakReverseDutyCycle(-0.6);
    TurretTalonConfigs.Slot0.withKP(0.1);
    TurretTalonConfigs.Slot0.withKG(0.0);
    TurretTalonConfigs.Slot0.withKI(0);
    TurretTalonConfigs.Slot0.withKD(0);
    TurretTalonConfigs.MotorOutput.withNeutralMode(NeutralModeValue.Brake);
    TurretTalonConfigs.MotorOutput.withInverted(InvertedValue.Clockwise_Positive);
    TurretTalonConfigs.MotionMagic.withMotionMagicAcceleration(150*1.5);
    TurretTalonConfigs.MotionMagic.withMotionMagicCruiseVelocity(200*1.5);

    turret.getConfigurator().apply(TurretTalonConfigs, 0.050);


  }

  @Override
  public void periodic() {
    // SmartDashboard.putNumber("pos", getTurretPosDegrees());
    // SmartDashboard.putNumber("pos1", getEncoder1Pos()*360);
    // SmartDashboard.putNumber("pos2", getEncoder2Pos()*360);
    // SmartDashboard.putNumber("turret_pos", turret.getPosition().getValueAsDouble()*360);
    
  }

  public double getEncoder1Pos() {
    return encoder1.getAbsolutePosition().getValueAsDouble();

  }

  public double getEncoder2Pos() {
    return encoder2.getAbsolutePosition().getValueAsDouble();

  }
  public void turretPosInit() {
    turret.setPosition(convert_teeth_to_degrees(find_teeth_rotated())/360);
  }

  public void setTurretControl(double anglepos) {
    if (anglepos >= 0 && anglepos <= 500) {
      turret.setControl(motionMagicControl.withPosition(anglepos/360).withSlot(0));

    }
  }

  public double convert_teeth_to_degrees(double teeth_rotated){
    double degrees = ((find_teeth_rotated()/100.0) * 360);
    return degrees;
  }

  public double find_teeth_rotated() {
    double val =  (actually_teeth*(getEncoder1Pos()-getEncoder2Pos()));
    double t = (val%actually_teeth+actually_teeth)%actually_teeth;
    return t;
  }
}


