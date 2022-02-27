package frc.robot.components;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants;
import frc.robot.sensors.Limelight;

public class Shooter {
    //this will be madness and i will figure it out eventually - kai

    //todo: find appropriate names
    TalonFX x, y;
    CANSparkMax one, two, three, four;
    Limelight limelight;
    Hood hood;
    Indexer indexer;
    Drivetrain drivetrain; 

    //5 cansparkmaxes????????? tbd
    public Shooter(int xID, int yID, int oneID, int twoID, int threeID, int fourID, Limelight limelight, Hood hood, Indexer indexer, Drivetrain drivetrain){
        x = new TalonFX(xID);
        y = new TalonFX(yID);
        one = new CANSparkMax(oneID, MotorType.kBrushless);
        two = new CANSparkMax(twoID, MotorType.kBrushless);
        three = new CANSparkMax(threeID, MotorType.kBrushless);
        four = new CANSparkMax(fourID, MotorType.kBrushless);
        this.limelight = limelight;
        this.hood = hood;
        this.indexer = indexer;
        this.drivetrain = drivetrain;

        y.follow(x);
        x.config_kP(0, 0.05);
        x.config_kI(0, 0);
        x.config_kD(0, 0);

        two.follow(one);
        three.follow(one);
        four.follow(one);
    }

    /*notes for future kai and emily-
                            | upper hub
                            |
                            | X
                            |
            x               |
    opus42 ----------------
                   Y
    X = 264 cm
    x = camera mounting angle  +  ty from limelight(converted to degrees) 
        ^ use that to set the hood angle ^
        
    Y = (X - cameraHeight)/tan(x) (this is what is in Limelight.java)
        ^ use that to set launching velocity^
    1) set hood angle
    2) have falcons approach launching velocity
    3) *after* falcons are at that velocity, run indexer & internal cansparkmaxes
    4) PEW PEW
    */

    /*public void shoot(){  
        //all of these values are in meters or m/s
        double targetVelocity = Constants.LAUNCHER_DEFAULT_VELOCITY;
        double groundDistance = limelight.getGroundDistance(Constants.UPPER_HUB_HEIGHT);
        double height = Constants.UPPER_HUB_HEIGHT + Constants.CARGO_DIAMETER - Constants.CAMERA_HEIGHT + 2;
        //0) autoalign
        double tx = limelight.getXAngle();
        while(Math.abs(tx)>1.0){
            drivetrain.autoAlign();
            tx = limelight.getXAngle();
        }
        //1) set hood angle 
        double targetAngle = getLaunchAngle(targetVelocity, groundDistance, height) + 45;
        hood.set(targetAngle); 
        //2) have falcons approaching launching velocity   
        x.config_kP(0, Constants.LAUNCHER_KP);
        x.config_kI(0, Constants.LAUNCHER_KI);
        x.config_kD(0, Constants.LAUNCHER_KD);
        /*
         *
	    * See documentation for calculation details.
	    * If using velocity, motion magic, or motion profile,
	    * use (1023 * duty-cycle / sensor-velocity-sensor-units-per-100ms).
        * 
        
            
        x.config_kF(0, Constants.LAUNCHER_KF);
        x.set(TalonFXControlMode.Velocity, targetVelocity / (Constants.LAUNCHER_WHEEL_CIRCUMFERENCE * Constants.LAUNCHER_ENCODER_UNITS_PER_ROTATION * 10));
        //3) *after* falcons are at that velocity, run indexer & internal cansparkmaxes
        if(x.getSelectedSensorVelocity(0) * Constants.LAUNCHER_WHEEL_CIRCUMFERENCE * Constants.LAUNCHER_ENCODER_UNITS_PER_ROTATION * 10 >= 0.9*targetVelocity){
            one.set(0.5);
            indexer.in();
        }        
    }*/

    /**
     * pew pew
     */
    public void shoot(){
        //all of these values are in meters or m/s
        //double targetVelocity = Constants.LAUNCHER_DEFAULT_VELOCITY;
        double groundDistance = limelight.getGroundDistance(Constants.UPPER_HUB_HEIGHT);
        double height = Constants.UPPER_HUB_HEIGHT + Constants.CARGO_DIAMETER - Constants.CAMERA_HEIGHT + 2;
        double targetAngle = Constants.LAUNCHER_MIN_ANGLE;

        //0) autoalign
        double tx = limelight.getXAngle();
        while(Math.abs(tx)>1.0){
            drivetrain.autoAlign();
            tx = limelight.getXAngle();
        }


        //1) set hood angle 
        //double targetAngle = getLaunchAngle(targetVelocity, groundDistance, height) + 45;
        //hood.set(targetAngle); 
        hood.setMax();

        //2) have falcons approaching launching velocity   
        double targetVelocity = getLaunchVelocity(targetAngle, groundDistance, height);
        x.config_kP(0, Constants.LAUNCHER_KP);
        x.config_kI(0, Constants.LAUNCHER_KI);
        x.config_kD(0, Constants.LAUNCHER_KD);

        /*
         *
	    * See documentation for calculation details.
	    * If using velocity, motion magic, or motion profile,
	    * use (1023 * duty-cycle / sensor-velocity-sensor-units-per-100ms).
        * 
        */
            
        x.config_kF(0, Constants.LAUNCHER_KF);

        x.set(TalonFXControlMode.Velocity, targetVelocity / (Constants.LAUNCHER_WHEEL_CIRCUMFERENCE * Constants.LAUNCHER_ENCODER_UNITS_PER_ROTATION * 10));


        //3) *after* falcons are at that velocity, run indexer & internal cansparkmaxes
        if(x.getSelectedSensorVelocity(0) * Constants.LAUNCHER_WHEEL_CIRCUMFERENCE * Constants.LAUNCHER_ENCODER_UNITS_PER_ROTATION * 10 >= 0.9*targetVelocity){
            one.set(0.5);
            indexer.in();
        }        
    }

    /**
     * figuring out target velocity
     * @param targetVelocity the velocity the ball will travel at
     * @param groundDistance the distance between the robot and the upper hub
     * @param height the height the ball needs to be at when it reaches 
     */
    public double getLaunchAngle(double targetVelocity, double groundDistance, double height){
        //this formula was obtained with help from lokesh pillai
        return Math.atan( ((2 * Math.pow(targetVelocity, 2)) + Math.sqrt( (4 * Math.pow(targetVelocity, 4) - (4 * Constants.GRAVITY * ((2 * Math.pow(targetVelocity, 2) * height) + (Constants.GRAVITY * Math.pow(groundDistance, 2))))))) / (2 * Constants.GRAVITY * groundDistance));
    }

    /**
     * figuring out target velocity
     * @param targetAngle the angle the hood is at
     * @param groundDistance the distance between the robot and the upper hub
     * @param height the height the ball needs to be at when it reaches 
     */
    public double getLaunchVelocity(double targetAngle, double groundDistance, double height){
        return Math.sqrt( (-1 * Constants.GRAVITY * Math.pow(groundDistance, 2) * Math.pow(sec(targetAngle), 2)) / (2 * (height - (groundDistance * Math.tan(targetAngle)))) );
    }

    /**
     * literally just 1/cos for sec bc i could not be bothered to be literate
     * @param a the angle
     */
    public double sec(double a){
        return 1.0 / Math.cos(a);
    }
}