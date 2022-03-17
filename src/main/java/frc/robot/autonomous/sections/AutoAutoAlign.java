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
        // TODO Auto-generated method stub
        //robot.drivetrain.autoAlign();
        robot.drivetrain.autoAlign();
    }

    @Override
    public void disabled() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean disableCondition() {
        return robot.shooter.alignSetpoint();
    }
    
}
