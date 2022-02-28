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
     * @param position the degree which the hood is set to
     */
    public void set(double position){
        motor.setAngle(position);
    }

    public void setMax(){
        motor.set(1);
    }

    public void setMin(){
        motor.set(0);
    }
}