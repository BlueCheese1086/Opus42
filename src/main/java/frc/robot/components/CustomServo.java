package frc.robot.components;

import edu.wpi.first.wpilibj.PWM;

public class CustomServo {
    PWM servo;
    
    public CustomServo(int channel) {
        servo = new PWM(channel);
        servo.setBounds(2.5, 1.5, 1.5, 1.5, 0.5);
    }

    public double getPosition() {
        return servo.getPosition();
    }

    public void setPosition(double pos) {
        servo.setPosition(pos);
    }

    public double getSpeed() {
        return servo.getSpeed();
    }

    public void setSpeed(double speed) {
        servo.setSpeed(speed);
    }
}