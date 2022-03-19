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
        control = ControlMode.Rainbow;
    }

    public void setControlMode(ControlMode con) {
        control = con;
    }

    public void tick() {
        switch (control) {
            case StaticColor:
                robot.lights.setLights(0, 0, 255);
            case Rainbow:
                robot.lights.rainbow();
        }
        robot.lights.update();
    }
    
}
