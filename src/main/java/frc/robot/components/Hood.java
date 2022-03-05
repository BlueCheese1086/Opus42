package frc.robot.components;

import edu.wpi.first.wpilibj.Servo;

public class Hood {
    Servo motor;

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

    public void setRaw(int a) {
        motor.setRaw(a);
    }

    /**
     * Sets hood to max position
     */
    public void setMax(){
        motor.set(1);
    }

    public int getRaw() {
        return motor.getRaw();
    }

    /**
     * Sets hood to minimum position
     */
    public void setMin(){
        motor.set(0);
    }
}