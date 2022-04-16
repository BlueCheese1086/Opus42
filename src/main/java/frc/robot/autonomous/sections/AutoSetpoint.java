package frc.robot.autonomous.sections;

import frc.robot.Robot;
import frc.robot.controlInterfaces.LightsInterface.ControlMode;

public class AutoSetpoint extends AutoSection {

    Robot robot;
    double y;

    public AutoSetpoint(double time, Robot robot) {
        super(time);
        this.robot = robot;
        this.y = 42069;
    }


    public AutoSetpoint(double time, double y, Robot robot) {
        super(time);
        this.robot = robot;
        this.y = y;
    }

    @Override
    public void init() {
        super.init();
        if (y == 42069) {
            y = robot.shooter.shooterConstants.getNearestSetpoint(robot.limelight.getYAngle())[0];
        }
        robot.hood.set(robot.shooter.shooterConstants.getNearestSetpoint(robot.limelight.getYAngle())[1]);
    }

    @Override
    public void update() {
        if (robot.drivetrain.xAlign()) {
            robot.drivetrain.yAlign(y);
        }
    }

    @Override
    public void disabled() {
        robot.lightsInter.setControlMode(ControlMode.Rainbow);
    }

    public boolean disableCondition() {
        //return false;
        return super.disableCondition() || (robot.drivetrain.isXAligned() && robot.drivetrain.isYAligned(y));
    }
    
}
