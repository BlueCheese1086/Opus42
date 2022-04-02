package frc.robot.controlInterfaces;

import frc.robot.Control;
import frc.robot.Robot;

public class LightsInterface extends Interface {

    public enum ControlMode {
        StaticColor,
        Rainbow;
    }

    ControlMode control;

    public LightsInterface(Robot robot, Control c) {
        super(robot, c);
        control = ControlMode.StaticColor;
    }

    public void setControlMode(ControlMode con) {
        control = con;
    }

    public void tick() {
        //robot.lights.rainbow();
        robot.lights.update();
    }
    
}
