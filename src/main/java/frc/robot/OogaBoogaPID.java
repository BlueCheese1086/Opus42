package frc.robot;

import edu.wpi.first.math.controller.PIDController;

public class OogaBoogaPID {
    
    PIDController pid;

    double maxSpeedValue;
    double maxSpeed;

    public OogaBoogaPID(double kp, double ki, double kd, double maxSpeedValue) {
        pid = new PIDController(kp, ki, kd);
        pid.setTolerance(.03);
        this.maxSpeedValue = maxSpeedValue;
        maxSpeed = 1;
    }

    public OogaBoogaPID(double kp, double ki, double kd, double maxSpeedValue, double tolerance) {
        pid = new PIDController(kp, ki, kd);
        pid.setTolerance(tolerance);
        this.maxSpeedValue = maxSpeedValue;
        maxSpeed = 1;
    }

    public OogaBoogaPID(double kp, double ki, double kd, double maxSpeedValue, double tolerance, double maxSpeed) {
        pid = new PIDController(kp, ki, kd);
        pid.setTolerance(tolerance);
        this.maxSpeedValue = maxSpeedValue;
        maxSpeedValue = 1;
    }

    public double calculate(double whereIAm, double whereIWantToBe) {
        boolean reverse = whereIAm - whereIWantToBe < 0;
        double speed = pid.calculate(whereIAm, whereIWantToBe);
        if (reverse) {
            speed = speed < -maxSpeedValue ? -maxSpeed : speed/100;
        } else {
            speed = speed > maxSpeedValue ? maxSpeed : speed/100;
        }
        return speed;
    }

    public boolean atSetpoint() {
        return pid.atSetpoint();
    }

}
