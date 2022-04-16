package frc.robot.components;

import frc.robot.OogaBoogaPID;
import frc.robot.PIDControl;
import frc.robot.Robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;

import com.kauailabs.navx.frc.AHRS;

import frc.robot.sensors.Limelight;


public class Drivetrain {
    public CANSparkMax frontLeft, frontRight, backLeft, backRight;
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
    Robot robot;

    OogaBoogaPID xAlignPID, yAlignPID;

    //import IDs in the constructor and leave them as variables. don't hard-code them in.
    public Drivetrain(int frontLeftID, int frontRightID, int backLeftID, int backRightID, Limelight limelight, AHRS gyro, Robot robot){
        frontLeft = new CANSparkMax(frontLeftID, MotorType.kBrushless);
        frontRight = new CANSparkMax(frontRightID, MotorType.kBrushless);
        backLeft = new CANSparkMax(backLeftID, MotorType.kBrushless);
        backRight = new CANSparkMax(backRightID, MotorType.kBrushless);

        this.robot = robot;

        leftPID = new PIDControl("velocity", frontLeft);
        rightPID = new PIDControl("velocity", frontRight);

        kinematics = new DifferentialDriveKinematics(0.52705);
        
        this.limelight = limelight;
        this.gyro = gyro;


        backLeft.follow(frontLeft);
        backRight.follow(frontRight);

        frontRight.setInverted(false);
        frontLeft.setInverted(true);

        setMode(IdleMode.kBrake);

        frontRight.setOpenLoopRampRate(0);
        backRight.setOpenLoopRampRate(0);
        frontLeft.setOpenLoopRampRate(0);
        backLeft.setOpenLoopRampRate(0);

        xAlignPID = new OogaBoogaPID(0.3, 28.0, 0.01, 15, 3, .5);
        yAlignPID = new OogaBoogaPID(1.5, 23, .05, 30, 1, 1);

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

    private void setRawMode(IdleMode mode) {
        frontRight.setIdleMode(mode);
        backRight.setIdleMode(mode);
        frontLeft.setIdleMode(mode);
        backLeft.setIdleMode(mode);
    }

    /**
     * Toggle Control Mode (Brake, Coast)
     */
    public void toggleMode() {
        brake = !brake;
        if (brake) {
            setRawMode(IdleMode.kBrake);
        } else {
            setRawMode(IdleMode.kCoast);
        }
    }

    public void setMode(IdleMode mode) {
        if (!(getMode() == (mode == IdleMode.kBrake ? true : false))) {
            toggleMode();
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
    public boolean xAlign(){
        double tx = limelight.getXAngle();
        double speed = xAlignPID.calculate(tx, 0);
        if (xAlignPID.atSetpoint()) {
           speed = 0;
        }
        this.set(-speed, speed);
        return xAlignPID.atSetpoint();
    }


    //not sure how to work this yet, so i've just made it the same as auto align for now - kai
    public boolean yAlign(double targetYAngle){
        double ty = limelight.getYAngle();

        double speed = yAlignPID.calculate(ty, targetYAngle);

        if (yAlignPID.atSetpoint()) {
            speed = 0;
        }

        this.set(-speed, -speed);
        return yAlignPID.atSetpoint();
    }


    public double power = 0.0;

    public void initPID(){
        this.pid = new PIDController(1.0/90.0, 0, 0);
        this.pid.setTolerance(5);

    }

    public boolean isXAligned() {
        return Math.abs(limelight.getXAngle()) < 1.5;
    }

    public boolean isYAligned(double y) {
        return Math.abs(limelight.getYAngle() - y) < 1;
    }

    //maybe angle pid gyro based
    public void anglePID(double setpoint){
        //this.set(0.3, -0.3);
        
        this.setpoint = setpoint;

        power = pid.calculate(gyro.getAngle(), setpoint);

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
