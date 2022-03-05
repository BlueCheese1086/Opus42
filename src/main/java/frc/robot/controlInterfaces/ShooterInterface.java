package frc.robot.controlInterfaces;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Control;
import frc.robot.Robot;
import frc.robot.components.Shooter;
import frc.robot.components.ShooterConstants;

public class ShooterInterface extends Interface{
    Shooter shooter;
    ShooterConstants constants;

    public ShooterInterface(Robot robot, Control c) {
        super(robot, c);
        //shooter = Robot.shooter;
        constants = new ShooterConstants();
    }

    /**
     * what the pewpew will do every tick
     */
    public void tick(){
        if(c.getLauncherShoot()) {
            robot.limelight.setLights(3);
            //robot.shooter.setVelocity(SmartDashboard.getNumber("Shooter Velocity", 0));
            //shooter.shoot();
            robot.shooter.shoot();
        } 
        else if(c.getLauncherAlign()){
            robot.limelight.setLights(3);
            robot.shooter.alignSetpoint();
        }
        else {
            //robot.limelight.setLights(1);
            robot.shooter.stopEverything();
        }

        if (c.primary.getPOV() == 270) {
            constants.setPoint(robot.limelight.getYAngle(), robot.hood.getPos(), robot.shooter.getVelocity());
            //robot.distanceVelo.addOption("name", object);
        }
        
        if (c.primary.getPOV() == 0) {
            robot.hood.setMax();
        } else if (c.primary.getPOV() == 180) {
            robot.hood.setMin();
        }
    }
    
}
