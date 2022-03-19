package frc.robot.autonomous.sections;

import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.DifferentialDrive.WheelSpeeds;
import frc.robot.Robot;
import frc.robot.components.Drivetrain;

public class AutoPath extends AutoSection {

    Drivetrain drivetrain;
    Trajectory t;
    double relativeTime;
    DifferentialDriveOdometry odomee;
    RamseteController controller;
    DifferentialDriveWheelSpeeds wheelSpeeds;
    
    public AutoPath(String pathName, Robot robot){
        this.drivetrain = robot.drivetrain;
        t = Robot.trajectories.getTrajectories().get(pathName);
        odomee = new DifferentialDriveOdometry(new Rotation2d(drivetrain.gyro.getAngle()), new Pose2d(0, 0, new Rotation2d()));
        controller = new RamseteController();

    }

    @Override
    public void init(){
        super.init();
        this.relativeTime = 0.0;
    }

    @Override
    public void update(){
        this.relativeTime = (System.currentTimeMillis() - startTime) / 1000.0;
        Trajectory.State target = t.sample(relativeTime);
        odomee.update(new Rotation2d(drivetrain.gyro.getAngle()), (drivetrain.getFrontLeft().getEncoder().getPosition() * Units.inchesToMeters(6*Math.PI)), (drivetrain.getFrontRight().getEncoder().getPosition() * Units.inchesToMeters(6*Math.PI)));
        Pose2d pose = odomee.getPoseMeters();
        ChassisSpeeds speed = controller.calculate(pose, target);
        wheelSpeeds = drivetrain.kinematics.toWheelSpeeds(speed);  
        drivetrain.leftPID.setVelocity((wheelSpeeds.leftMetersPerSecond / Units.inchesToMeters(6*Math.PI)) * 60.0);
        drivetrain.rightPID.setVelocity((wheelSpeeds.rightMetersPerSecond / Units.inchesToMeters(6*Math.PI)) * 60.0);
        

    }

    @Override
    public void disabled(){

    }
}
