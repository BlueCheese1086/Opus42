package frc.robot.components;

import frc.robot.PIDControl;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.kauailabs.navx.frc.AHRS;

import frc.robot.sensors.Limelight;


public class Drivetrain {
    CANSparkMax frontLeft, frontRight, backLeft, backRight;
    Limelight limelight;
    public AHRS gyro;
    public DifferentialDriveKinematics kinematics;
    public PIDControl leftPID;
    public PIDControl rightPID;

    boolean brake = true;

    double P, I, D = 1.0;
    double integral, previous_error, setpoint = 0.0;
    double turn_adjust, kp, min_command, error, derivative;
    public PIDController pid;

    //import IDs in the constructor and leave them as variables. don't hard-code them in.
    public Drivetrain(int frontLeftID, int frontRightID, int backLeftID, int backRightID, Limelight limelight, AHRS gyro){
        frontLeft = new CANSparkMax(frontLeftID, MotorType.kBrushless);
        frontRight = new CANSparkMax(frontRightID, MotorType.kBrushless);
        backLeft = new CANSparkMax(backLeftID, MotorType.kBrushless);
        backRight = new CANSparkMax(backRightID, MotorType.kBrushless);

        leftPID = new PIDControl("velocity", frontLeft);
        rightPID = new PIDControl("velocity", frontRight);

        kinematics = new DifferentialDriveKinematics(0.52705);
        
        this.limelight = limelight;
        this.gyro = gyro;


        backLeft.follow(frontLeft);
        backRight.follow(frontRight);

        frontRight.setInverted(false);
        frontLeft.setInverted(true);

        frontLeft.setIdleMode(IdleMode.kBrake);
        frontRight.setIdleMode(IdleMode.kBrake);

        frontRight.setOpenLoopRampRate(0);
        backRight.setOpenLoopRampRate(0);
        frontLeft.setOpenLoopRampRate(0);
        backLeft.setOpenLoopRampRate(0);
    }

    /**
     * Gets the front left CANSparkMax object
     * @return Returns front left CANSparkMax object
     */
    public CANSparkMax getFrontLeft() {
        return frontLeft;
    }

    /**
     * Gets the front right CANSparkMax object
     * @return Returns fron right CANSparkMax object
     */
    public CANSparkMax getFrontRight() {
        return frontRight;
    }

    /**
     * Get motor temps array
     * @return Returns motor temps double array (FrontLeft, BackLeft, FrontRight, BackRight)
     */
    public double[] getTemps() {
        return new double[]{frontLeft.getMotorTemperature(), backLeft.getMotorTemperature(), frontRight.getMotorTemperature(), backRight.getMotorTemperature()};
    }

    /**
     * Drives robot (duh)
     * @param forward controller input declaring robot's speed forward/backward
     * @param turn controller input declaring how robot turns
     */
    public void drive(double forward, double turn){
        frontLeft.set(forward + turn);
        frontRight.set(forward - turn);
    }

    /**
     * Toggle Control Mode (Brake, Coast)
     */
    public void toggleMode() {
        brake = !brake;
        if (brake) {
            frontLeft.setIdleMode(IdleMode.kBrake);
            frontRight.setIdleMode(IdleMode.kBrake);
        } else {
            frontLeft.setIdleMode(IdleMode.kCoast);
            frontRight.setIdleMode(IdleMode.kCoast);
        }
    }

    /**
     * Get current mode
     * @return Returns if brake mode is active or not
     */
    public boolean getMode() {
        return brake;
    }

    /**
     * Control left and right side individually
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
        limelight.setLights(3);
        double Kp = -0.01;
        double min_command = 0.001;
        double tx = limelight.getXAngle();

        double steering_adjust = (tx) * Kp;
        if(tx > 1.0) steering_adjust -=min_command;
        else if(tx<1.0) steering_adjust+=min_command;
          
        this.set(-1 * steering_adjust, steering_adjust);
    }


    //not sure how to work this yet, so i've just made it the same as auto align for now - kai
    public void setPointAlign(double targetYAngle){
        double Kp = 0.05;
        double min_command = 0.01;
        double ty = limelight.getYAngle();

        double steering_adjust = (ty - targetYAngle) * Kp * -1;
        if( Math.abs(ty - targetYAngle) > 1.0) steering_adjust -= min_command;
        else if(Math.abs(ty-targetYAngle) < 1.0) steering_adjust+=min_command;
        steering_adjust *= -1;
        this.set(steering_adjust, steering_adjust);
    }


    //maybe angle pid gyro based
    public void anglePID(double setpoint){
        this.setpoint = setpoint;
        
        pid = new PIDController(1.0/90.0, 0,0);

        double power = pid.calculate(gyro.getAngle(), setpoint);

        this.set(power, -power);

        /*sad unnessecarily complicated code:((((
            why jameeeeeee! whyyyyyyy *crying emote*

        error = setpoint - gyro.getAngle(); // Error = Target - Actual

        this.integral += (error*.02); // Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
        derivative = (error - this.previous_error) / .02;
        //this.rcw = P*error + I*this.integral + D*derivative;

        kp = 0.05;
        min_command = 0.01;

        turn_adjust = P*error + I*this.integral + D*derivative;
        if( Math.abs(error) > 1.0) turn_adjust -= min_command;
        else if(error < 1.0) turn_adjust+=min_command;
        int steering_adjust = -1;
        this.set(0, 0);

        this.frontLeft.set(0.25);
        this.frontRight.set(-0.25);*/
    }

    public boolean getAnglePIDStatus(){
        return this.pid.atSetpoint();
        // return setpoint - gyro.getAngle() < 1 && setpoint - gyro.getAngle() > -1;
    }

    public void setSpeed(DifferentialDriveWheelSpeeds speeds){

    }
}
