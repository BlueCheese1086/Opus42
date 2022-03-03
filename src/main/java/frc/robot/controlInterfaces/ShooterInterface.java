package frc.robot.controlInterfaces;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Control;
import frc.robot.Robot;
import frc.robot.components.Shooter;

public class ShooterInterface extends Interface{
    Shooter shooter;

    public ShooterInterface(Robot robot, Control c) {
        super(robot, c);
        //shooter = Robot.shooter;
    }

    /**
     * what the pewpew will do every tick
     */
    public void tick(){
        if(c.getLauncherShoot()){
            robot.getShooter().setVelocity(SmartDashboard.getNumber("Shooter Velocity", 0));
            //shooter.shoot();
            robot.getShooter().shoot();
        }
        else{
            robot.getShooter().stopEverything();
        }
    }
    
}
