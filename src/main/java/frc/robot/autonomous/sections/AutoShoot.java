
package frc.robot.autonomous.sections;

import frc.robot.Robot;
import frc.robot.components.ShooterConstants;

//test
import frc.robot.components.Indexer;
import frc.robot.components.Intake;

//import frc.robot.components.Shooter;

public class AutoShoot extends AutoSection {
    
    //Shooter shooter;
    Robot robot;
    ShooterConstants sc;

    double velo;
    double hoodAngle;

    //test
    Intake intake;
    Indexer indexer;

    public AutoShoot(double length, Robot robot){
        super(length);
        this.robot = robot;
        //this.shooter = Robot.shooter;
        //sc = new ShooterConstants();

        //test

        this.velo = -1;
        this.hoodAngle = -1;

        this.intake = robot.intake;
        this.indexer = robot.indexer;
    }

    public AutoShoot(double velo, double hoodAngle, double length, Robot robot){
        super(length);
        this.robot = robot;
        //this.shooter = Robot.shooter;
        //sc = new ShooterConstants();

        //test
        this.intake = robot.intake;
        this.indexer = robot.indexer;

        this.hoodAngle = hoodAngle;
        this.velo = velo;
    }

    @Override
    public void init(){
        super.init();
        robot.limelight.setLights(3);
        //test
        //intake.down();
    }

    @Override
    public void update() {
        //robot.limelight.setLights(3);
        //robot.shooter.setVelocity(SmartDashboard.getNumber("Shooter Velocity", 0));
        //robot.shooter.setVelocity(13000);
        //shooter.shoot();
        //robot.shooter.shoot();
        if (velo != -1) {
            robot.shooter.shootVeloHoodAngle(velo, hoodAngle);
        } else {
            robot.shooter.shoot();
        }

        //sc.setPoint(robot.limelight.getYAngle(), robot.hood.getPos(), SmartDashboard.getNumber("Shooter Velocity", 0));

        //test
        /**intake.in();
        indexer.in();*/


    }

    @Override
    public void disabled() {
        //robot.limelight.setLights(1);
        robot.shooter.stopEverything();

        //test
        /**intake.up();
        intake.neutral();
        indexer.stop();*/
    }
}

