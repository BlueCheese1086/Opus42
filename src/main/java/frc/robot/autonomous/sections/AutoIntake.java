package frc.robot.autonomous.sections;

import frc.robot.components.Drivetrain;
import frc.robot.components.Intake;

import frc.robot.Robot;

public class AutoIntake extends AutoSection{

    Intake intake;
    Drivetrain drivetrain;
    
    public AutoIntake(double length){
        super(length);
        this.intake = Robot.intake;
        this.drivetrain = Robot.drivetrain;
        
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
        drivetrain.drive(0,0);

    }
}
