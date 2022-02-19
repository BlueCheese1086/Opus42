package frc.robot.controlInterfaces;

import frc.robot.Control;
import frc.robot.Robot;
import frc.robot.components.Drivetrain;

public class DrivetrainInterface extends Interface{
    Drivetrain drivetrain;

    public DrivetrainInterface(Robot robot, Control c){
        super(robot, c);
        drivetrain = robot.drivetrain;
    }

    /**
     * what the drive train will do every tick
     */
    public void tick(){
        
    }
}
