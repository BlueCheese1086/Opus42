package frc.robot.components;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Drivetrain {
    CANSparkMax frontLeft, frontRight, backLeft, backRight;
    //import IDs in the constructor and leave them as variables. don't hard-code them in.
    public Drivetrain(int frontLeftID, int frontRightID, int backLeftID, int backRightID){
        frontLeft = new CANSparkMax(frontLeftID, MotorType.kBrushless);
        frontRight = new CANSparkMax(frontRightID, MotorType.kBrushless);
        backLeft = new CANSparkMax(backLeftID, MotorType.kBrushless);
        frontLeft = new CANSparkMax(frontLeftID, MotorType.kBrushless);
        backLeft.follow(frontLeft);
        backRight.follow(frontRight);
    }

    /**
     * @param forward controller input declaring robot's speed forward/backward
     * @param turn controller input declaring how robot turns
     */
    public void drive(double forward, double turn){
        double totalForward = forward * Math.abs(forward);
        double totalTurn = turn * Math.abs(turn);
        frontLeft.set(totalForward + totalTurn);
        frontRight.set(totalForward - totalTurn);
    }

    /**
     * will be used to autoalign before launching. don't worry about writing this method if you're not working on the launcher.
     */
    public void autoAlign(){

    }
}
