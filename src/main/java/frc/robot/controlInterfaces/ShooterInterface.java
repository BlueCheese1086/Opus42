package frc.robot.controlInterfaces;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
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
            robot.limelight.setLights(3);
            robot.getShooter().setVelocity(SmartDashboard.getNumber("Shooter Velocity", 0));
            //shooter.shoot();
            robot.getShooter().shoot();
        }
        else{
            robot.limelight.setLights(1);
            robot.getShooter().stopEverything();
        }

        if (c.primary.getPOV() == 270) {
            robot.getShooter().putData(robot.limelight.getYAngle(), SmartDashboard.getNumber("Shooter Velocity", 0));
        }
        
        if (c.primary.getPOV() == 0) {
            robot.hood.setMax();
        } else if (c.primary.getPOV() == 180) {
            robot.hood.setMin();
        }
    }
    
}
