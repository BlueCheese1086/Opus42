package frc.robot.controlInterfaces;

import frc.robot.Control;
import frc.robot.Robot;

public abstract class Interface {
    Robot robot;
    Control c;
    
    public Interface(Robot robot, Control c){
        this.robot = robot;
        this.c = c;
    }

    public abstract void tick();
}
