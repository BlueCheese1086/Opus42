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
            climb.enable();
            climb.setLeft(c.getLeftClimb());
        } else {
            climb.setLeft(0);
        }

        if (Math.abs(c.getRightClimb()) > .2) {
            climb.enable();
            climb.setRight(c.getRightClimb());
        } else {
            climb.setRight(0);
        }

        if (Math.abs(c.getRightClimb()) < .2 && Math.abs(c.getLeftClimb()) < .2) {
            climb.disable();
            climb.setLeft(0);
            climb.setRight(0);
        }

    }

}
