package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;

public class PIDController {

    String type;
    double value;
    CANSparkMax controller;
    SparkMaxPIDController pid;

    public PIDController(String type, CANSparkMax controller, double value) {
            this.type = type;
            this.value = value;
            this.controller = controller;
    }

    public void initPID(double ff, double kp, double ki, double kd) {
        pid = controller.getPIDController();
        pid.setFF(ff);
        pid.setP(kp);
        pid.setI(ki);
        pid.setD(kd);
        pid.setOutputRange(-1, 1);
    }

    /** 
     * turns the robot to the given angle
     * @param angle = degrees
     */
    public void rotateToAngle(double angle) {
        value = angle;
        pid.setReference(angle, CANSparkMax.ControlType.kPosition, 0);
    }

    /** 
     * move the robot the given distance
     * @param distance = meters
     */
    public void driveDistance(double distance) {
        value = distance;
        pid.setReference(distance, CANSparkMax.ControlType.kPosition, 0);
    }
    
}

