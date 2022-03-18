
package frc.robot.autonomous.sections;

import frc.robot.Robot;

//test
import frc.robot.components.Indexer;
import frc.robot.components.Intake;

//import frc.robot.components.Shooter;

public class AutoShoot extends AutoSection {
    
    //Shooter shooter;

    //test
    Intake intake;
    Indexer indexer;

    public AutoShoot(double length, Robot robot){
        super(length, robot);
        //this.shooter = Robot.shooter;
        //sc = new ShooterConstants();

        //test
        this.intake = robot.intake;
        this.indexer = robot.indexer;
    }

    @Override
    public void init(){
        super.init();
        
        //test
        //intake.down();
    }

    @Override
    public void update() {
        robot.drivetrain.set(0, 0);
        robot.shooter.autonomousShoot(8500.0);
    }

    @Override
    public void disabled() {
        //robot.limelight.setLights(1);
        robot.shooter.stopEverything();
        robot.indexer.stop();
    }
}

