package frc.robot.components;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.sensors.Limelight;

public class Drivetrain {
    CANSparkMax frontLeft;
    CANSparkMax frontRight;
    CANSparkMax backLeft;
    CANSparkMax backRight;
    Limelight limelight;
    //import IDs in the constructor and leave them as variables. don't hard-code them in.
    public Drivetrain(int frontLeftID, int frontRightID, int backLeftID, int backRightID, Limelight limelight){
        frontLeft = new CANSparkMax(frontLeftID, MotorType.kBrushless);
        frontRight = new CANSparkMax(frontRightID, MotorType.kBrushless);
        backLeft = new CANSparkMax(backLeftID, MotorType.kBrushless);
        backRight = new CANSparkMax(backRightID, MotorType.kBrushless);
        this.limelight = limelight;
        
        backLeft.follow(frontLeft);
        backRight.follow(frontRight);
    }

    public CANSparkMax getFrontLeft(){
        return frontLeft;
    }

    public CANSparkMax getFrontRight(){
        return frontRight;
    }

    /**
     * @param forward controller input declaring robot's speed forward/backward
     * @param turn controller input declaring how robot turns
     */
    public void drive(double forward, double turn){
        double totalForward = forward * Math.abs(forward);
        if(Math.abs(totalForward)<0.15) totalForward=0;
        double totalTurn = turn * Math.abs(turn);
        if(Math.abs(totalTurn)<0.15) totalTurn=0;

        frontLeft.set(totalForward + totalTurn);
        frontRight.set(totalForward - totalTurn);
    }

    /**
     * @param left what the left side is set to
     * @param right what the right side is set to
     */
    public void set(double left, double right){
        frontLeft.set(left);
        frontRight.set(right);
    }

    /**
     * will be used to autoalign before launching. don't worry about writing this method if you're not working on the launcher.
     */
    public void autoAlign(){
        double Kp = -0.01;
        double min_command = 0.001;
        double tx = limelight.getXAngle();

        double steering_adjust = (tx) * Kp;
        if(tx > 1.0) steering_adjust -=min_command;
        else if(tx<1.0) steering_adjust+=min_command;
          
        this.set(-1 * steering_adjust, steering_adjust);
    }
}