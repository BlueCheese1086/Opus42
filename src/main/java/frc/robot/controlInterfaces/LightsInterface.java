package frc.robot.controlInterfaces;

import frc.robot.Control;
import frc.robot.Robot;

public class LightsInterface extends Interface {

    public enum ControlMode {
        StaticColor,
        Alliance,
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

    public void setStatic(int r, int g, int b) {
        robot.lights.setLights(r, g, b);
    }

    public void tick() {
        //robot.lights.rainbow();
        switch (control) {
            case Rainbow:
                robot.lights.rainbow();
                break;
            case Alliance:
                robot.lights.setAlliance();
                break;
            case StaticColor:
                break;
        }
        robot.lights.update();
    }
    
}
