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

        /*if (climb.getLock()) {
            robot.lights.setLightsTo(137, 174, 0, 0, 0);
            robot.lights.setLightsTo(137, 137 + (int) (Math.abs(climb.right.getEncoder().getPosition()) % 250)/(174-137), 0, 255, 0);
            robot.lights.setLightsTo(113, 135, 0, 0, 0);
            robot.lights.setLightsTo(113, 113 + (int) (Math.abs(climb.left.getEncoder().getPosition()) % 250)/(135-113), 0, 255, 0);
        }*/

        if (Math.abs(c.getRightClimb()) < .2 && Math.abs(c.getLeftClimb()) < .2) {
            climb.disable();
            climb.setLeft(0);
            climb.setRight(0);
        }

    }

}
