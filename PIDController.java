package frc.robot;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;

public class PIDController {

    String type;
    double variable;
    CANSparkMax controller;
    SparkMaxPIDController pid;

    public PIDController(String type, double variable){
            this.type = type;
            this.variable = variable;

    }

    public void initPID(double ff, double kp, double ki, double kd) {
        pid = controller.getPIDController();
        pid.setFF(ff);
        pid.setP(kp);
        pid.setI(ki);
        pid.setD(kd);
    }

    public void setAngle(double angle) {
        variable = angle;

    }

    public void setPosition(double position) {
        variable = position;
        pid.setReference(position, ControlType.kPosition);
    }
    
}
