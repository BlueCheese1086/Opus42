package frc.robot.autonomous.sections;

import frc.robot.Drivetrain;
import frc.robot.Robot;
import frc.robot.PIDController;
import frc.robot.Constants;

public class AutoDrive extends AutoSection {

    Drivetrian drivetrain;
    double current;
    double distance; // rotaions
    PIDController rDrivePIDController;
    PIDController lDrivePIDController;

    /** distance = cm */
    public AutoDrive(double distance){
        this.drivetrain = Robot.drivetrain;
        this.distance = (distance * 0.001) * Constants.DRIVETRAIN_POSITION_SCALE;
        this.rDrivePIDController = new PIDController("distance", drivetrain.frontRight, distance);
        this.lDrivePIDController = new PIDController("distance", drivetrain.frontLeft, distance);
        drivetrain.frontLeft.initPID(Constants.MP_DRIVE_FF, Constants.MP_DRIVE_KP, Constants.MP_DRIVE_KI, Constants.MP_DRIVE_KD);
        drivetrain.frontRight.initPID(Constants.MP_DRIVE_FF, Constants.MP_DRIVE_KP, Constants.MP_DRIVE_KI, Constants.MP_DRIVE_KD);

    }

    @Override
    public void init(){
        super.startTime = System.currentTimeMillis();
        super.started = true;
        drivetrian.frontLeft.getEncoder().setPosition(0);
        drivetrain.frontRight.getEncoder().setPosition(0);

    }

    @Override
    public void update(double distance) {
        rdrivePIDConroller.driveDistance(distance);
        ldrivePIDConroller.driveDistance(distance);
    }

    @Override
    public void disabled() {
        drivetrain.drive(0, 0);
        super.endTime = System.currentTimeMillis();
    }

    @Override
    public boolean disableCondition() {
        return distance - Constant.DRIVE_ERROR < drivetrain.frontRight.getEncoder().getPosition() < distance + Constants.DRIVE_ERROR: false;
    }
}

