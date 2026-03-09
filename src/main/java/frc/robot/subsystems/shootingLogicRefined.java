package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class shootingLogicRefined extends SubsystemBase {
    // Resulting target position to aim at
    public static Translation2d posImaginary = new Translation2d();
    
    public boolean flag = false;

    public shootingLogicRefined() {}

    @Override
    public void periodic() {
        if (flag) {
            // 1. Get current robot state from constants/sensors
            double robotX = shootingConstants.x; 
            double robotY = shootingConstants.y;
            double robotHeading = shootingConstants.robotHeading;
            
            // Velocity of the robot in field coordinates
            double vxField = shootingConstants.velx;
            double vyField = shootingConstants.vely;

            // Turret offsets relative to robot center (if any)
            double offsetX = shootingConstants.offsetX; 
            double offsetY = shootingConstants.offsetY;

            // 2. Calculate Turret pivot position in field coordinates
            double turretX = robotX + offsetX * Math.cos(robotHeading) - offsetY * Math.sin(robotHeading);
            double turretY = robotY + offsetX * Math.sin(robotHeading) + offsetY * Math.cos(robotHeading);

            // 3. Initialize prediction with static hub location
            double predictedX = shootingConstants.hubx;
            double predictedY = shootingConstants.huby;

            double tof = 0.0;
            final int MAX_ITER = 5;
            final double TOF_TOL = 0.002;

            // 4. Recursive TOF Loop
            for (int i = 0; i < MAX_ITER; i++) {
                double dx = predictedX - turretX;
                double dy = predictedY - turretY;
                double distance = Math.hypot(dx, dy);

                // Get TOF based on distance (from your lookup/interpolation)
                double newTOF = interpolationLogic.predictedTOF(distance);
                
                // Clamp TOF to reasonable physical bounds (0 to 1.5 seconds)
                newTOF = Math.max(0.0, Math.min(1.5, newTOF));

                if (Math.abs(newTOF - tof) < TOF_TOL) {
                    break;
                }

                tof = newTOF;

                /* * 5. Predict where the hub 'effectively' is.
                 * If the robot is moving toward the hub, the ball travels a shorter 
                 * distance relative to the floor. We subtract (Velocity * TOF) 
                 * to compensate for robot movement.
                 */
                predictedX = shootingConstants.hubx - vxField * tof;
                predictedY = shootingConstants.huby - vyField * tof;
            }

            // Store result for the turret to aim at
            posImaginary = new Translation2d(predictedX, predictedY);
        }
    }

    /**
     * Calculates the field-relative angle to the predicted virtual target.
     */
    public static double getShootAngle(Translation2d predictedTarget, Pose2d currentRobotPose) {
        // Calculate angle from current robot position to the imaginary target
        double dx = predictedTarget.getX() - currentRobotPose.getX();
        double dy = predictedTarget.getY() - currentRobotPose.getY();
        return Math.atan2(dy, dx);
    }
}