package frc.robot.autonomous.sections;

import frc.robot.components.Drivetrain;
import frc.robot.Robot;
import frc.robot.PIDController;
import frc.robot.Constants;

public class AutoDrive extends AutoSection {

    Drivetrain drivetrain;
    double current;
    double distance; // rotaions
    PIDController rDrivePIDController;
    PIDController lDrivePIDController;

    /** distance = cm */
    public AutoDrive(double distance){
        this.drivetrain = Robot.drivetrain;
        this.distance = (distance * 0.001) * Constants.DRIVETRAIN_POSITION_SCALE;
        this.rDrivePIDController = new PIDController("distance", drivetrain.getFrontRight(), distance);
        this.lDrivePIDController = new PIDController("distance", drivetrain.getFrontLeft(), distance);
        lDrivePIDController.initPID(Constants.MP_DRIVE_FF, Constants.MP_DRIVE_KP, Constants.MP_DRIVE_KI, Constants.MP_DRIVE_KD);
        rDrivePIDController.initPID(Constants.MP_DRIVE_FF, Constants.MP_DRIVE_KP, Constants.MP_DRIVE_KI, Constants.MP_DRIVE_KD);

    }

    @Override
    public void init(){
        super.startTime = System.currentTimeMillis();
        super.started = true;
        drivetrain.getFrontLeft().getEncoder().setPosition(0);
        drivetrain.getFrontRight().getEncoder().setPosition(0);

    }

    @Override
    public void update() {
        rDrivePIDController.driveDistance(distance);
        lDrivePIDController.driveDistance(distance);
    }

    @Override
    public void disabled() {
        drivetrain.drive(0, 0);
        super.endTime = System.currentTimeMillis();
    }

    @Override
    public boolean disableCondition() {
        if(distance - Constants.DRIVE_ERROR < drivetrain.getFrontRight().getEncoder().getPosition() && drivetrain.getFrontRight().getEncoder().getPosition() < distance + Constants.DRIVE_ERROR){
            return true;
        } else{
            return false;
        }
    }
}
