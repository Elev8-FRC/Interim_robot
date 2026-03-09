// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.geometry.Translation2d;


public class interpolationLogic extends SubsystemBase {

  public static InterpolatingDoubleTreeMap tofmap = new InterpolatingDoubleTreeMap();
  public static InterpolatingDoubleTreeMap rpmMap = new InterpolatingDoubleTreeMap();
  public static InterpolatingDoubleTreeMap hoodMap = new InterpolatingDoubleTreeMap();
  public static final double hub_x = 182.11;
  public static final double hub_y = 158.84;
  public static double robot_x = 0;
  public static double robot_y = 0;


  

  /** Creates a new Shooter. */
  public interpolationLogic() {

  rpmMap.put(1.575, 3000.0); 
  rpmMap.put(2.0, 3000.0);
  rpmMap.put(2.25, 3000.0);
  rpmMap.put(2.5, 3000.0);
  rpmMap.put(2.75, 3000.0);
  rpmMap.put(3.0, 3000.0);
  rpmMap.put(3.25, 3000.0);
  rpmMap.put(3.5, 3000.0);
  rpmMap.put(3.75, 3000.0);
  rpmMap.put(4.0, 3000.0);
  rpmMap.put(4.25, 3000.0);
  rpmMap.put(4.55, 3500.0);

  hoodMap.put(1.575, 0.294);
  hoodMap.put(2.0, 0.438);
  hoodMap.put(2.25, 3.8219);
  hoodMap.put(2.5, 5.809);
  hoodMap.put(2.75, 7.791);
  hoodMap.put(3.0, 9.766);
  hoodMap.put(3.25, 11.755);
  hoodMap.put(3.5, 13.732);
  hoodMap.put(3.75, 15.765);
  hoodMap.put(4.0, 16.858);
  hoodMap.put(4.25, 17.73);
  hoodMap.put(4.55, 17.799);

  tofmap.put(1.575, 0.5);
  tofmap.put(2.0, 0.5);  
  tofmap.put(2.25, 0.5);
  tofmap.put(2.5, 0.5);
  tofmap.put(2.75, 0.5);
  tofmap.put(3.0, 0.5);
  tofmap.put(3.25, 0.5);
  tofmap.put(3.5, 0.5);
  tofmap.put(3.75, 0.5);
  tofmap.put(4.0, 0.5);
  tofmap.put(4.25, 0.5);

  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Shooter RPM", predictedRPM());
    SmartDashboard.putNumber("Shooter Hood Angle", predictedHoodAngle());
    SmartDashboard.putNumber("Shooter TOF", predictedTOF(robot_x, robot_y));
  }

  public static double distance(double robot_x, double robot_y){
    return Math.sqrt(Math.pow((robot_x-hub_x), 2) + Math.pow((robot_y-hub_y), 2))*0.0254;
  }

  public static double predictedRPM(){
    return rpmMap.get(distance(robot_x, robot_y));
  
  }
  public static double predictedHoodAngle(){
    return hoodMap.get(distance(robot_x, robot_y));
  }
  public static double predictedTOF(double x, double y){
    return tofmap.get(distance(x, y));
  }
}