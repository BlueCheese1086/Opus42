package frc.robot.autonomous.sections;

import frc.robot.Intake;
import frc.robot.Robot;

public class AutoIntake extends AutoSection{

    Intake intake;
    Drivetrain drivetrain;
    
    public AutoIntake(double length){
        super(length);
        this.intake = Robot.intake;
        this.drivetrain = Robot.drivetrian;
        
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

    }

    @Override
    public void disabled() {
        intake.up();
        drivetrain.drive(0,0)

    }
}
