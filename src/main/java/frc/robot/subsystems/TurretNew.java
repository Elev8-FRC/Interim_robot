// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicDutyCycle;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

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
    SmartDashboard.putNumber("pos", getTurretPosDegrees());
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
    turret.setPosition(getTurretPosDegrees()/360);
  }

  public void setTurretControl(double anglepos) {
    if (anglepos >= 0 && anglepos <= 500) {
      turret.setControl(motionMagicControl.withPosition(anglepos/360).withSlot(0));

    }
  }

  // public double getTurretPosDegrees() {
  //   double p1 = getEncoder1Pos();
  //   double p2 = getEncoder2Pos();

  //   for (int i = 0; i < e2_teeth; i++) {
  //     double r1 = (i + p1) * ratio1;
  //     for (int j = 0; j < e1_teeth; j++) {
  //       double r2 = (j + p2) * ratio2;
  //       if (Math.abs(r1 - r2) < 0.01) {
  //         return r1*360;
  //       }
  //     }
  //   }
  //   return 0;
  // }
  // AI CODE BUT ITS MORE EFFICIENT AND PROBABLY MORE ACCURATE THAN THE ABOVE NESTED LOOP APPROACH
//   public double getTurretPos() {
//   double p1 = getEncoder1Pos(); // Returns 0.0 to 1.0
//   double p2 = getEncoder2Pos(); // Returns 0.0 to 1.0

//   // 1. Calculate how many "teeth" each sensor thinks it has passed
//   // relative to the start of its own gear.
//   double teeth1 = p1 * e1_teeth; // e.g., 0.5 turns * 21 teeth = 10.5 teeth
//   double teeth2 = p2 * e2_teeth; // e.g., 0.5 turns * 22 teeth = 11.0 teeth

//   // 2. Use the Chinese Remainder Theorem formula for coprime numbers (21 and 22)
//   // The magic formula for these specific gears is: (22 * a) - (21 * b)
//   double totalTeeth = (e2_teeth * teeth1) - (e1_teeth * teeth2);

//   // 3. Handle the "wrapping" logic (Modulo)
//   // The pattern repeats every 462 teeth (21 * 22). 
//   // If the number is negative, we add 462 to make it positive.
//   double maxTeethRange = e1_teeth * e2_teeth; // 462
//   totalTeeth = totalTeeth % maxTeethRange;
  
//   if (totalTeeth < 0) {
//     totalTeeth += maxTeethRange;
//   }

//   // 4. Convert total teeth moved into Turret Rotations
//   // The turret has 100 teeth.
//   return totalTeeth / t_teeth;
// }

//   public double getTurretPosDegrees() {
//     return getTurretPos() * 360;
// }
}