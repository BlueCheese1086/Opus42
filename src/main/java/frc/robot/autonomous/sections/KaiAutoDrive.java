package frc.robot.autonomous.sections;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;

public class KaiAutoDrive extends AutoSection{
    Robot robot;
    RelativeEncoder frontLeftEncoder, frontRightEncoder;
    PIDController lPID, rPID;
    double distance;
    double currentDistance;
    double targetDistance;

    /**
     * 
     * @param robot the robomb
     * @param distance distance in meters
     * @param length t i m e
     */
    public KaiAutoDrive(Robot robot, double distance, double length){
        super(length);
        frontLeftEncoder = robot.drivetrain.frontLeft.getEncoder();
        frontRightEncoder = robot.drivetrain.frontRight.getEncoder();
        this.robot = robot;
    
        lPID = new PIDController(0.5, 0, 0);
        rPID = new PIDController(0.5, 0, 0);

    }

    /**
     * 
     * @param robot le opie fortee toot
     * @param distance distance in meters, as opposed to stinky feet with fungus
     */
    public KaiAutoDrive(Robot robot, double distance){
        super();
    }

    @Override
    public void init(){
        super.init();
        frontLeftEncoder.setPosition(0);
        frontRightEncoder.setPosition(0);
        currentDistance = frontLeftEncoder.getPosition();
        frontLeftEncoder.setPositionConversionFactor(1/Constants.LAUNCHER_WHEEL_CIRCUMFERENCE * 42);
        frontRightEncoder.setPositionConversionFactor(1/Constants.LAUNCHER_WHEEL_CIRCUMFERENCE * 42);

        targetDistance = distance * frontLeftEncoder.getPositionConversionFactor() + currentDistance;        
    }

    @Override
    public void update() {
        //pid crap
        currentDistance = frontLeftEncoder.getPosition();
        double rSpeed = rPID.calculate(currentDistance, targetDistance);
        double lSpeed = lPID.calculate(currentDistance, targetDistance);
        
        SmartDashboard.putNumber("CURRENT DISTANCE", currentDistance);
        robot.drivetrain.set(lSpeed, rSpeed);
        SmartDashboard.putNumber("Drive PID - Left", lSpeed);
        SmartDashboard.putNumber("Drive PID - Right", rSpeed);
    }

    @Override
    public void disabled() {
        robot.drivetrain.set(0,0);
    }    
}
