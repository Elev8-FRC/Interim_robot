// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class shootingConstants extends SubsystemBase {
  /** Creates a new shootingConstants. */
  public shootingConstants() {}
  public static double x;
  public static double y;
  public static double velx;
  public static double vely;
  public static double accelx;
  public static double accely;
  public static final double hubx = 182.11;
  public static final double huby = 158.84;
  @Override
  public void periodic() {
    
  }
}
