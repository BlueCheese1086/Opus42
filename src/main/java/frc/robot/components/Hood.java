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
        motor.set(position);
    }

    public void setRaw(int a) {
        motor.setRaw(a);
    }

    public double getPos() {
        return motor.get();
    }

    public void setSpeed(double speed) {
        motor.setSpeed(speed);
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