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
        if (Math.abs(c.getClimb()) > .2) {
            climb.lock();
            climb.set(c.getClimb());
        } else {
            climb.set(0);
            climb.unlock();
        }
    }

}
