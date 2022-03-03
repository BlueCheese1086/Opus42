
package frc.robot.autonomous.sections;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
//import frc.robot.components.Shooter;

public class AutoShoot extends AutoSection {
    
    //Shooter shooter;
    Robot robot;

    public AutoShoot(double length, Robot robot){
        super(length);
        this.robot = robot;
        //this.shooter = Robot.shooter;
    }

    @Override
    public void update() {
        robot.getShooter().setVelocity(SmartDashboard.getNumber("Shooter Velocity", 0));
        //shooter.shoot();
        robot.getShooter().shoot();
    }

    @Override
    public void disabled() {
        robot.getShooter().stopEverything();
    }
}

