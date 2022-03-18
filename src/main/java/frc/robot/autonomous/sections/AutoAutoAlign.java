package frc.robot.autonomous.sections;

import frc.robot.Robot;

public class AutoAutoAlign extends AutoSection {

    public AutoAutoAlign(int length, Robot robot){
        super(length, robot);
    }

    @Override
    public void init(){
        super.init();
    }

    @Override
    public void update() {
        robot.drivetrain.autoAlign();
    }

    @Override
    public void disabled() {        
    }

    @Override
    public boolean disableCondition() {
        return robot.shooter.alignSetpoint();
    }
    
}
