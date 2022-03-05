package frc.robot.controlInterfaces;

import frc.robot.Control;
import frc.robot.Robot;
import frc.robot.components.Drivetrain;

public class DrivetrainInterface extends Interface{
    Drivetrain drivetrain;

    //Constructor
    public DrivetrainInterface(Robot robot, Control c){
        super(robot, c);
        drivetrain = robot.getDrivetrain();
    }

    /**
     * what the Twin Turbo V8 2.4L 800BHP absolute clapped out honda civic will do every tick
     */
    public void tick() {
        if (true) {
            drivetrain.drive(c.getDriveForward(), c.getDriveTurn());
        }

        if (c.getBrakeToggle()) {
            drivetrain.toggleMode();
        }
    }

}
