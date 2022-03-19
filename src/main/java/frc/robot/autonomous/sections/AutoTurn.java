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
    boolean isAngleBased;
    boolean isRight;
    double power;
    Robot robot;

    /** angle = degrees */
    public AutoTurn(double angle, Robot robot){
        this.robot = robot;
        this.drivetrain = robot.drivetrain;
        this.turningDist = (((2 * Math.PI * Constants.WHEEL_TO_WHEEL_RADIUS) * (angle / 360 )) * 0.001) * Constants.DRIVETRAIN_POSITION_SCALE;
        this.rTurnPIDController = new PIDController("angle", drivetrain.frontRight, angle);
        this.lTurnPIDController = new PIDController("angle", drivetrain.frontLeft, angle);
        //drivetrain.frontLeft.initPID(Constants.MP_DRIVE_FF, Constants.MP_DRIVE_KP, Constants.MP_DRIVE_KI, Constants.MP_DRIVE_KD);
        //drivetrain.frontRight.initPID(Constants.MP_DRIVE_FF, Constants.MP_DRIVE_KP, Constants.MP_DRIVE_KI, Constants.MP_DRIVE_KD);
        this.isAngleBased = true;

    }

    public AutoTurn(int length, boolean isRight, double power){
        super(length);
        this.drivetrain = robot.drivetrain;
        this.isAngleBased = false;
        this.isRight = isRight;
        this.power = power;

    }

    @Override
    public void init(){
        super.startTime = System.currentTimeMillis();
        super.started = true;
        if (isAngleBased){
            drivetrain.frontLeft.getEncoder().setPosition(0);
            drivetrain.frontRight.getEncoder().setPosition(0);
        }

    }

    @Override
    public void update() {
        if (isAngleBased){
            //rturnPIDController.rotateToAngle(turningDist);
            //lturnPIDController.rotateToAngle(turningDist * -1);
        } else {
            if(isRight){
                drivetrain.frontLeft.set(power);
                drivetrain.frontRight.set(-power);
            } else {
                drivetrain.frontLeft.set(power);
                drivetrain.frontRight.set(power);
            }
        }

    }

    @Override
    public void disabled() {
        drivetrain.drive(0,0);
        super.endTime = System.currentTimeMillis();

    }

    @Override
    public boolean disableCondition() {
        return false;
        //return turningDist - Constants.TURN_ERROR < drivetrain.frontRight.getEncoder().getPosition() < angle + Constants.TURN_ERROR : false;
    }
}
