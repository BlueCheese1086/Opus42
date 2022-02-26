package frc.robot.controlInterfaces;

import frc.robot.Control;
import frc.robot.Robot;
import frc.robot.components.Intake;

public class IntakeInterface extends Interface {
    Intake intake;

    public IntakeInterface(Robot robot, Control c) {
        super(robot, c);
        intake = robot.intake;
        //TODO Auto-generated constructor stub
    }

    /**
     * what the intake will do every tick
     */
    public void tick() {
        //Check for loading in
        if(xbox.getButton()) {
            intake.in();
        }
        //Check for jamming
        else if (xbox.getButton()) {
            intake.out();
        }
        //stop loading
        else {
            intake.neutral();
        }
        //Check for obtain ready
        if(xbox.getButton()) {
            intake.down();
        }
        else {
            intake.up();
        }
    }
    
}
