package frc.robot.components;

import edu.wpi.first.wpilibj.Servo;

public class Hood {
    Servo motor;
    public Hood(int motorID){
        motor = new Servo(motorID);
    }

    /**
     * @param position the degree which the hood is set to
     */
    public void set(double position){
        motor.setAngle(position);
    }

}
