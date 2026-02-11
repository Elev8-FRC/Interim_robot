// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicDutyCycle;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.controls.StrictFollower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
  public TalonFX intakeRoller_1 = new TalonFX(1);
  public TalonFX intakeRoller_2 = new TalonFX(2);
  public TalonFX intakeExtender = new TalonFX(3);
  public TalonFX indexer = new TalonFX(4);
  public double currentmax = 10.0;
  public boolean extenderAligned = false;
  public boolean intakeRunning = false;

  /** Creates a new IntakeSubsystem. */
  public IntakeSubsystem() {
    TalonFXConfiguration RollerTalonConfigs = new TalonFXConfiguration();
    RollerTalonConfigs.MotorOutput.withPeakForwardDutyCycle(0.6);
    RollerTalonConfigs.MotorOutput.withPeakReverseDutyCycle(-0.6);
    RollerTalonConfigs.MotorOutput.withNeutralMode(NeutralModeValue.Coast);
    RollerTalonConfigs.MotorOutput.withInverted(InvertedValue.Clockwise_Positive);

    intakeRoller_1.getConfigurator().apply(RollerTalonConfigs, 0.050);
    intakeRoller_2.getConfigurator().apply(RollerTalonConfigs, 0.050);
    indexer.getConfigurator().apply(RollerTalonConfigs, 0.050);

    intakeRoller_1.setControl(new Follower(intakeRoller_2.getDeviceID(), MotorAlignmentValue.Aligned));

    TalonFXConfiguration ExtenderTalonConfigs = new TalonFXConfiguration();
    ExtenderTalonConfigs.MotorOutput.withPeakForwardDutyCycle(0.6);
    ExtenderTalonConfigs.MotorOutput.withPeakReverseDutyCycle(-0.6);
    ExtenderTalonConfigs.Slot0.withKP(0.1);
    ExtenderTalonConfigs.Slot0.withKG(0.0);
    ExtenderTalonConfigs.Slot0.withKI(0);
    ExtenderTalonConfigs.Slot0.withKD(0);
    ExtenderTalonConfigs.MotorOutput.withNeutralMode(NeutralModeValue.Brake);
    ExtenderTalonConfigs.MotorOutput.withInverted(InvertedValue.Clockwise_Positive);
    ExtenderTalonConfigs.MotionMagic.withMotionMagicAcceleration(150*1.5);
    ExtenderTalonConfigs.MotionMagic.withMotionMagicCruiseVelocity(200*1.5);

    intakeExtender.getConfigurator().apply(ExtenderTalonConfigs, 0.050);
  }

  @Override
  public void periodic() {
    if((!extenderAligned)&&(getExtenderCurrent()<currentmax)){
      intakeExtender.setVoltage(-1);
    }
    else if((!extenderAligned)&&(getExtenderCurrent()>=currentmax)){
      extenderAligned = true;
    }
  }

  public void setIndexerpwr(double power){
    indexer.setVoltage(power*12);
  }

  public void setIntakepwr(double power){
    intakeRoller_2.setVoltage(power*12);
  }

  public void setExtenderVoltage(int volts){
    intakeExtender.setVoltage(volts);
  }

  public void setExtenderPosition(double position){
    intakeExtender.setPosition(position);
  }

  public double getExtenderCurrent(){
    return intakeExtender.getSupplyCurrent().getValueAsDouble();
  }

  public void runIntake(){
      setExtenderPosition(1);
      setIntakepwr(1.0);
      setIndexerpwr(1.0);
  }
}
