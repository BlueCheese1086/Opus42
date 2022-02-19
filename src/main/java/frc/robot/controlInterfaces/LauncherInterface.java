package frc.robot.controlInterfaces;

import frc.robot.Control;
import frc.robot.Robot;
import frc.robot.components.Launcher;

public class LauncherInterface extends Interface{
    Launcher launcher;

    public LauncherInterface(Robot robot, Control c) {
        super(robot, c);
        launcher = robot.launcher;
        //TODO Auto-generated constructor stub
    }

    /**
     * what the pewpew will do every tick
     */
    public void tick(){
        
    }
    
}
