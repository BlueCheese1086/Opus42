
package frc.robot.autonomous.sections;

import frc.robot.Robot;

public class AutoWait extends AutoSection {

    public AutoWait(double length, Robot robot) {
        super(length, robot);
    }

    @Override
    public void update() {
        //Jank solution for waiting on hood
    }

    @Override
    public void disabled() {

    }
    

}