// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.subsystems;
// import edu.wpi.first.math.geometry.Translation2d;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;

// public class shootingLogic extends SubsystemBase {
//   public static Translation2d posImaginary;
//   public Translation2d pos;
//   public double estx; 
//   public double esty;
//   public boolean flag = false;
//   public double[] tofs = new double[5];
//   public double sum;
//   public shootingLogic() {}

// @Override
// public void periodic() {
//   if (flag) {
//     estx = shootingConstants.x;
//     esty = shootingConstants.y;
// double prevPrevTof = Double.NaN;
// double prevTof     = Double.NaN;
// double tof         = interpolationLogic.predictedTOF(estx, esty);


// double contraction = 1.0;
// final double contractionThreshold = 0.2; // tune

// for (int iter = 0; iter < 5 && (iter < 3 || contraction > contractionThreshold); iter++) {
//     this.pos = pos(
//         shootingConstants.x,
//         shootingConstants.y,
//         shootingConstants.velx,
//         shootingConstants.vely,
//         tof,
//         0,
//         shootingConstants.accelx,
//         shootingConstants.accely
//     );
    
//     estx = this.pos.getX();
//     esty = this.pos.getY();

//     prevPrevTof = prevTof;
//     prevTof     = tof;
//     tof         = interpolationLogic.predictedTOF(estx, esty);
//     tofs[iter]  = tof;
//     sum += tof;

//     if (!Double.isNaN(prevPrevTof) && Math.abs(prevTof - prevPrevTof) > 1e-6) {
//         contraction = Math.abs((tof - prevTof) / (prevTof - prevPrevTof));
//     }
// }
// sum = sum/tofs.length;
// posImaginary = pos(shootingConstants.x, shootingConstants.y, shootingConstants.velx, shootingConstants.vely, sum, 0, shootingConstants.accelx, shootingConstants.accely);
//   }
// }
// //   @Override
// // public void periodic() {
// //   if (flag) {
// //     estx = shootingConstants.x;
// //     esty = shootingConstants.y;

// //     double tof = interpolationLogic.predictedTOF(estx, esty);
// //     for (int i = 0; i < 5; i++) {
// //       this.pos = pos(
// //           shootingConstants.x,
// //           shootingConstants.y,
// //           shootingConstants.velx,
// //           shootingConstants.vely,
// //           tof,
// //           0,   
// //           shootingConstants.accelx,
// //           shootingConstants.accely
// //       );

// //       estx = this.pos.getX();
// //       esty = this.pos.getY();

// //       tof = interpolationLogic.predictedTOF(estx, esty);
// //     }

// //     posImaginary = new Translation2d(estx, esty);
// //   }
// // }


//   public static Translation2d pos(double x, double y, double velx, double vely, double tof, double delay, double accelx, double accely){
//     double estimatedx = x+velx*tof+0.5*accelx*Math.pow(delay, 2);
//     double estimatedy = y+vely*tof+0.5*accely*Math.pow(delay, 2);
//     return new Translation2d(estimatedx, estimatedy);
//   }


// // Use posImaginary to get angle.
//   public static double shootangle(Translation2d pos){
//     double x1 = pos.getX();
//     double y1 = pos.getY();
//     return Math.atan2((shootingConstants.huby-y1),(shootingConstants.hubx-x1));
//   }
// }

