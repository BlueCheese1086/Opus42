package frc.robot.components;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;

public class Hood {
    Servo motor;

    PWMMotorController pwm;

    public Hood(int motorID){
        motor = new Servo(motorID);
        // = new PWMMotorController();
            
        
    }

    /**
     * Sets hood position
     * @param position the degree which the hood is set to
     */
    public void set(double position){
        motor.setAngle(position);
    }

    /**
     * Sets hood to max position
     */
    public void setMax(){
        motor.set(1);
    }

    /**
     * Sets hood to minimum position
     */
    public void setMin(){
        motor.set(0);
    }
}