package frc.robot.controlInterfaces;

import frc.robot.Control;
import frc.robot.Robot;
import frc.robot.components.Intake;

public class IntakeInterface extends Interface {
    Intake intake;

    public IntakeInterface(Robot robot, Control c) {
        super(robot, c);
        intake = robot.intake;
        intake.up();
    }

    /**
     * what the zuc will do every tick
     */
    public void tick() {
        if (c.getIntakeIn()) {
            robot.indexer.in();
            intake.in();
        }
        // Check for jamming
        else if (c.getIntakeOut()) {
            robot.indexer.out();
            intake.out();
        }
        // stop loading
        else {
            intake.neutral();
        }
        if (c.getIntakeToggle())

        {
            intake.toggle();
        }
    }
}
