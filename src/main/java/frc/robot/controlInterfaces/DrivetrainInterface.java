package frc.robot.controlInterfaces;

import frc.robot.Control;
import frc.robot.Robot;
import frc.robot.components.Drivetrain;
import frc.robot.sensors.Limelight;

public class DrivetrainInterface extends Interface{
    Drivetrain drivetrain;

    //Constructor
    public DrivetrainInterface(Robot robot, Control c){
        super(robot, c);
        drivetrain = robot.drivetrain;
    }

    /**
     * what the Twin Turbo V8 2.4L 800BHP absolute clapped out honda civic (Opus42) will do every tick
     */
    public void tick() {
        if (true) {
            drivetrain.drive(c.getDriveForward(), c.getDriveTurn());
        }

        /*if (c.primary.getRightBumperPressed()) {
            drivetrain.driveDistance(24);
        }*/

        if (c.getBrakeToggle()) {
            drivetrain.toggleMode();
        }
    }

}
