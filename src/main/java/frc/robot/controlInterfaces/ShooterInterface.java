package frc.robot.controlInterfaces;

import frc.robot.Control;
import frc.robot.Robot;
import frc.robot.components.Shooter;

public class ShooterInterface extends Interface{
    Shooter shooter;

    public ShooterInterface(Robot robot, Control c) {
        super(robot, c);
        shooter = robot.shooter;
    }

    /**
     * what the pewpew will do every tick
     */
    public void tick(){
        if(c.getLauncherShoot()){
            shooter.shoot();
        }
    }
    
}
