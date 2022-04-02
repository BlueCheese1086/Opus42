package frc.robot.autonomous.sections;

import frc.robot.Robot;

public class AutoHood extends AutoSection {
    
    double hoodAngle;

    Robot robot;

    public AutoHood(double angle, Robot robot) {
        super(1.7);
        hoodAngle = angle;
        this.robot = robot;
    }

    @Override
    public void update() {
        robot.hood.set(hoodAngle);
    }

    @Override
    public void disabled() {

    }
}