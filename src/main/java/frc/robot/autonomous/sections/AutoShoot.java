package frc.robot.autonomous.sections;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
//import frc.robot.components.Shooter;

public class AutoShoot extends AutoSection {
    
    //Shooter shooter;

    public AutoShoot(double length){
        super(length);
        //this.shooter = Robot.shooter;
    }

    @Override
    public void update() {
        Robot.shooter.setVelocity(SmartDashboard.getNumber("Shooter Velocity", 0));
        //shooter.shoot();
        Robot.testShoot();
    }

    @Override
    public void disabled() {
        Robot.shooter.stopEverything();
    }
}
