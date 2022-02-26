package frc.robot;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.math.controller.PIDController;

public class PIDController {

    String type;
    double value;
    CANSparkMax controller;
    SparkMaxPIDController pid;

    public PIDController(String type, CANSparkMax controller, double value){
            this.type = type;
            this.value = value;
            this.controller = controller;
            this.id = id;

    }

    public void initPID(double ff, double kp, double ki, double kd) {
        pid = controller.getPIDController();
        pid.setFF(ff);
        pid.setP(kp);
        pid.setI(ki);
        pid.setD(kd);
    }

    /** turns the robot to the given angle
      * angle = degrees
     */
    public void rotateToAngle(double angle) {
        value = angle;
        pid.setReference(angle, ControlType.kPosition, 0);
    }

    /** move the robot the given distance
      * distance = meters
     */
    public void driveDistance(double distance) {
        value = distance;
        pid.setReference(distance, ControlType.kPosition, 0);
    }
    
}
