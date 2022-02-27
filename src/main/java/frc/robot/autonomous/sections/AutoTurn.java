package frc.robot.autonomous.sections;

import frc.robot.components.Drivetrain;
import frc.robot.Robot;
import frc.robot.PIDController;
import frc.robot.Constants;

public class AutoTurn extends AutoSection{

    Drivetrain drivetrain;
    double current;
    double turningDist; // rotaions
    PIDController rTurnPIDController;
    PIDController lTurnPIDController;

    /** angle = degrees */
    public AutoTurn(double angle){
        this.drivetrain = Robot.drivetrain;
        this.turningDist = (((2 * Math.PI * Constants.WHEEL_TO_WHEEL_RADIUS) * (angle / 360 )) * 0.001) * Constants.DRIVETRAIN_POSITION_SCALE;
        this.rTurnPIDController = new PIDController("angle", drivetrain.getFrontRight(), angle);
        this.lTurnPIDController = new PIDController("angle", drivetrain.getFrontLeft(), angle);
        lTurnPIDController.initPID(Constants.MP_DRIVE_FF, Constants.MP_DRIVE_KP, Constants.MP_DRIVE_KI, Constants.MP_DRIVE_KD);
        rTurnPIDController.initPID(Constants.MP_DRIVE_FF, Constants.MP_DRIVE_KP, Constants.MP_DRIVE_KI, Constants.MP_DRIVE_KD);

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
        rTurnPIDController.rotateToAngle(turningDist);
        lTurnPIDController.rotateToAngle(turningDist * -1);

    }

    @Override
    public void disabled() {
        drivetrain.drive(0,0);
        super.endTime = System.currentTimeMillis();

    }

    @Override
    public boolean disableCondition() {
        if(turningDist - Constants.TURN_ERROR < drivetrain.getFrontRight().getEncoder().getPosition() &&  drivetrain.getFrontRight().getEncoder().getPosition() < turningDist + Constants.TURN_ERROR){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public double getStartTime(){
        return super.getStartTime();
    }

    @Override
    public double getEndTime(){
        return super.getEndTime();
    }
}
