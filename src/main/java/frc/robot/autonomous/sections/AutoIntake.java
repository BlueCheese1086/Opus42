
package frc.robot.autonomous.sections;

import frc.robot.components.Drivetrain;
import frc.robot.components.Intake;
import frc.robot.components.Indexer;

import frc.robot.Robot;

public class AutoIntake extends AutoSection{

    Intake intake;
    Drivetrain drivetrain;
    Indexer indexer;
    
    public AutoIntake(double length, Robot robot){
        super(length);
        this.intake = robot.intake;
        //this.drivetrain = robot.drivetrain;
        this.indexer = robot.indexer;
        
    }

    @Override
    public void init(){
        super.startTime = System.currentTimeMillis();
        super.started = true;
        intake.down();

    }

    @Override
    public void update() {
        intake.in();
        indexer.in();

    }

    @Override
    public void disabled() {
        intake.up();
        intake.neutral();
        indexer.stop();

    }
}

