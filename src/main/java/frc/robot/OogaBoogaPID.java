package frc.robot;

import edu.wpi.first.math.controller.PIDController;

public class OogaBoogaPID {
    
    PIDController pid;

    double maxSpeedValue;
    double maxSpeed;

    /**
     * @param kp Proportional
     * @param ki Integral
     * @param kd Derivatve
     * @param maxSpeedValue Calculated number that the pid can give the max output of 1 at (if above x return 1)
     */
    public OogaBoogaPID(double kp, double ki, double kd, double maxSpeedValue) {
        pid = new PIDController(kp, ki, kd);
        pid.setTolerance(.03);
        this.maxSpeedValue = maxSpeedValue;
        maxSpeed = 1;
    }

    /**
     * @param kp Proportional
     * @param ki Integral
     * @param kd Derivative
     * @param maxSpeedValue Calculated number that the pid can give the max output of 1 at (if above x return 1)
     * @param tolerance How much room of error allowed
     */
    public OogaBoogaPID(double kp, double ki, double kd, double maxSpeedValue, double tolerance) {
        pid = new PIDController(kp, ki, kd);
        pid.setTolerance(tolerance);
        this.maxSpeedValue = maxSpeedValue;
        maxSpeed = 1;
    }

    /**
     * @param kp Proportional
     * @param ki Integral
     * @param kd Derivative
     * @param maxSpeedValue Calculated number that the pid can give the max output of 1 at (if above x return 1)
     * @param tolerance How much room of error allowed
     * @param maxSpeed Max speed that the pid can output
     */
    public OogaBoogaPID(double kp, double ki, double kd, double maxSpeedValue, double tolerance, double maxSpeed) {
        pid = new PIDController(kp, ki, kd);
        pid.setTolerance(tolerance);
        this.maxSpeedValue = maxSpeedValue;
        this.maxSpeed = maxSpeed;
    }

    /**
     * @param whereIAm Current Location
     * @param whereIWantToBe Location robot should be at
     * @return Calculated value for pid
     */
    public double calculate(double whereIAm, double whereIWantToBe) {
        boolean reverse = whereIAm - whereIWantToBe < 0;
        double speed = pid.calculate(whereIAm, whereIWantToBe);
        reverse = speed < 0;
        if (reverse) {
            speed = speed < -maxSpeedValue ? -maxSpeed : speed/(maxSpeedValue/maxSpeed);
        } else {
            speed = speed > maxSpeedValue ? maxSpeed : speed/(maxSpeedValue/maxSpeed);
        }
        return speed;
    }


    /**
     * @return Returns wether pid is accomplished or not
     */
    public boolean atSetpoint() {
        return pid.atSetpoint();
    }

}
