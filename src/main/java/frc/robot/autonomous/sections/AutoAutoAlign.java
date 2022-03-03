package frc.robot.autonomous.sections;

import frc.robot.Robot;

public class AutoAutoAlign extends AutoSection {

    Robot robot;

    public AutoAutoAlign(int length, Robot robot){
        super(length);
        this.robot = robot;
    }

    @Override
    public void init(){
        super.init();
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        robot.getDrivetrain().autoAlign();
        
    }

    @Override
    public void disabled() {
        // TODO Auto-generated method stub
        
    }
    
}
