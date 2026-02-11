// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TorqueCurrentConfigs;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicDutyCycle;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.controls.StrictFollower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase {
  /** Creates a new Turret. */
  public TalonFX feeder = new TalonFX(0);
  public TalonFX turret_motor = new TalonFX(1);

  public final MotionMagicDutyCycle m_TurretmagicDutyCycle;
  public final PositionDutyCycle m_TurretPosDutyCycle;
  public final TorqueCurrentConfigs m_TurretCurrentConfigs = new TorqueCurrentConfigs();

  /** Creates a new hood_subsystem. */
  public Turret() {
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
    m_TurretmagicDutyCycle = new MotionMagicDutyCycle(0);
    m_TurretPosDutyCycle = new PositionDutyCycle(0);

    turret_motor.getConfigurator().apply(TurretTalonConfigs, 0.050);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void start_feeder(double power) {
    feeder.setVoltage(power*12);
  }

  public void stop_feeder() {
    feeder.setVoltage(0);
  }

  public void setHoodAngle(double angle) {
    turret_motor.setPosition(angle/360);
  }
}
