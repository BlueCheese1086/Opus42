
package frc.robot.autonomous.sections;

import frc.robot.Robot;

//test
import frc.robot.components.Indexer;
import frc.robot.components.Intake;

//import frc.robot.components.Shooter;

public class AutoShoot extends AutoSection {
    
    //Shooter shooter;
    Robot robot;

    //test
    Intake intake;
    Indexer indexer;

    public AutoShoot(double length, Robot robot){
        super(length);
        this.robot = robot;
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
        //robot.limelight.setLights(3);
        //robot.shooter.setVelocity(SmartDashboard.getNumber("Shooter Velocity", 0));
        //robot.shooter.setVelocity(13000);
        //shooter.shoot();
        //if (robot.shooter.alignSetpoint()) {
        robot.drivetrain.set(0, 0);
        robot.shooter.autonomousShoot(8500.0);
        //}
        //robot.shooter.shoot();
        //sc.setPoint(robot.limelight.getYAngle(), robot.hood.getPos(), SmartDashboard.getNumber("Shooter Velocity", 0));

        //test
        /**intake.in();
        indexer.in();*/
    }

    @Override
    public void disabled() {
        //robot.limelight.setLights(1);
        robot.shooter.stopEverything();
        robot.indexer.stop();

        //test
        /**intake.up();
        intake.neutral();
        indexer.stop();*/
    }
}

