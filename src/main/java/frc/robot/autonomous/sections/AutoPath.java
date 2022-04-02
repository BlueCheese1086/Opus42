package frc.robot.autonomous.sections;

import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.util.Units;
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
        super(5.0);
        System.out.println("AutoPath Stuff contructor...");
        this.drivetrain = robot.drivetrain;

        //robot.trajectories.init();
        //not inintailizing path object correctly
        
        //robot.trajectories.test();
        
        odomee = new DifferentialDriveOdometry(new Rotation2d(drivetrain.gyro.getAngle()), new Pose2d(0, 0, new Rotation2d()));
        System.out.println("odomee :)");
        controller = new RamseteController();
        System.out.println("controller :)");
        //robot.trajectories.init();
        //t = robot.trajectories.getTrajectories().get(pathName);

    }

    @Override
    public void init(){
        System.out.println("AutoPath init stuff...");
        super.init();
        System.out.println("StartTime:" + super.startTime);
        this.relativeTime = 0.0;
    }

    @Override
    public void update(){
        System.out.println("AutoPath Update stuff...");
        this.relativeTime = (System.currentTimeMillis() - startTime) / 1000.0;
        System.out.println("relativeTime:" + this.relativeTime);
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
        System.out.println("AutoPath Disabled");
    }
}