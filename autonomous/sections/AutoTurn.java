package frc.robot.autonomous.sections;

import frc.robot.components.Drivetrain;
import frc.robot.Robot;
import frc.robot.PIDControl;
import frc.robot.Constants;

public class AutoTurn extends AutoSection{

    Drivetrain drivetrain;
    double current;
    double turningDist; // rotaions
    PIDControl rTurnPIDController;
    PIDControl lTurnPIDController;
    boolean isAngleBased;
    boolean isRight;
    double power;
    double angle;


    /** angle = degrees */
    public AutoTurn(double angle, Robot robot/*, double length*/){
        //super(length);
        this.drivetrain = robot.drivetrain;
        this.angle = angle;
        // this.turningDist = (((2 * Math.PI * Constants.WHEEL_TO_WHEEL_RADIUS) * (angle / 360 )) * 0.001) * Constants.DRIVETRAIN_POSITION_SCALE;
        // this.rTurnPIDController = new PIDController("angle", drivetrain.frontRight, angle);
        // this.lTurnPIDController = new PIDController("angle", drivetrain.frontleft, angle);
        // drivetrain.frontLeft.initPID(Constants.MP_DRIVE_FF, Constants.MP_DRIVE_KP, Constants.MP_DRIVE_KI, Constants.MP_DRIVE_KD);
        // drivetrain.frontRight.initPID(Constants.MP_DRIVE_FF, Constants.MP_DRIVE_KP, Constants.MP_DRIVE_KI, Constants.MP_DRIVE_KD);
        this.isAngleBased = true;

    }

    public AutoTurn(int length, boolean isRight, double power, Robot robot){
        super(length);
        this.drivetrain = robot.drivetrain;
        this.isAngleBased = false;
        this.isRight = isRight;
        this.power = power;

    }

    @Override
    public void init(){
        super.init();
        if (isAngleBased){
            // drivetrian.frontLeft.getEncoder().setPosition(0);
            // drivetrain.frontRight.getEncoder().setPosition(0);

            //drivetrain.initPID();
            drivetrain.gyro.reset();
            //drivetrain.pid.setTolerance(1);

            // drivetrain.getFrontLeft().set(0.25);
            // drivetrain.getFrontRight().set(-0.25);
        }

    }

    @Override
    public void update() {
        if (isAngleBased){
            // rturnPIDController.rotateToAngle(turningDist);
            // lturnPIDController.rotateToAngle(turningDist * -1);

            //drivetrain.anglePID(angle);

            drivetrain.getFrontLeft().set(0.25);
            drivetrain.getFrontRight().set(-0.25);

        } else {
            if(isRight){
                drivetrain.getFrontLeft().set(power);
                drivetrain.getFrontRight().set(-power);
            } else {
                drivetrain.getFrontLeft().set(power);
                drivetrain.getFrontRight().set(power);
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
        if (isAngleBased){
            //return super.disableCondition();
            return drivetrain.getAnglePIDStatus();
        } else{
            return super.disableCondition();
        }
    }
}
