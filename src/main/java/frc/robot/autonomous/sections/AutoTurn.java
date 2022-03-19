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
    Robot robot;

    /** angle = degrees */
    public AutoTurn(double angle, Robot robot){
        this.robot = robot;
        this.drivetrain = robot.drivetrain;
        this.turningDist = (((2 * Math.PI * Constants.WHEEL_TO_WHEEL_RADIUS) * (angle / 360 )) * 0.001) * Constants.DRIVETRAIN_POSITION_SCALE;
        this.rTurnPIDController = new PIDControl("angle", drivetrain.frontRight, angle);
        this.lTurnPIDController = new PIDControl("angle", drivetrain.frontLeft, angle);
        //drivetrain.frontLeft.initPID(Constants.MP_DRIVE_FF, Constants.MP_DRIVE_KP, Constants.MP_DRIVE_KI, Constants.MP_DRIVE_KD);
        //drivetrain.frontRight.initPID(Constants.MP_DRIVE_FF, Constants.MP_DRIVE_KP, Constants.MP_DRIVE_KI, Constants.MP_DRIVE_KD);

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
            drivetrain.frontLeft.getEncoder().setPosition(0);
            drivetrain.frontRight.getEncoder().setPosition(0);
        }

    }

    //@Override
    public void update() {
        //KAI HERE! i think the problem is that all of this test code itself doesn't actually do anything?
        //Robot.limelight.setLights(0);
        /*drivetrain.getFrontLeft().set(0.25);
        drivetrain.getFrontRight().set(-0.25);*/

        //this may not do what you want, but at least it will do something
        drivetrain.drive(0.25, 0.5);

        /*if (isAngleBased){
            // rturnPIDController.rotateToAngle(turningDist);
            // lturnPIDController.rotateToAngle(turningDist * -1);

            //drivetrain.anglePID(angle);

            // drivetrain.getFrontLeft().set(0.25);
            // drivetrain.getFrontRight().set(-0.25);
        } else {
            if(isRight){
                drivetrain.getFrontLeft().set(power);
                drivetrain.getFrontRight().set(-power);
            } else {
                drivetrain.getFrontLeft().set(power);
                drivetrain.getFrontRight().set(power);
            }
        }*/

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
