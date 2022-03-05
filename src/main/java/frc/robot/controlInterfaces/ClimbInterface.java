package frc.robot.controlInterfaces;

import frc.robot.Control;
import frc.robot.Robot;
import frc.robot.components.Climb;

public class ClimbInterface extends Interface{
    Climb climb;
    Control safe;

    public ClimbInterface(Robot robot, Control c) {
        super(robot, c);
        climb = robot.climb;
    }

     /**
     * what the climb will do every tick
     */
    public void tick() {




        if (Math.abs(c.getLeftClimb()) > .2) {
            climb.lock();
            climb.setLeft(c.getLeftClimb());
        }
        if (Math.abs(c.getRightClimb()) > .2) {
            climb.lock();
            climb.setRight(c.getRightClimb());
        }



    }

}
