package frc.robot.components;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.sensors.Limelight;

public class Shooter {
    //this will be madness and i will figure it out eventually - kai

    //todo: find appropriate names
    public TalonFX x, y;
    public CANSparkMax one, two, three, four;
    Limelight limelight;
    Hood hood;
    Indexer indexer;
    Drivetrain drivetrain; 
    double velocity;
    public ShooterConstants shooterConstants;
    
    //4 cansparkmaxes????????? tbd
    public Shooter(int xID, int yID, int oneID, int twoID, int threeID, int fourID, Limelight limelight, Hood hood, Indexer indexer, Drivetrain drivetrain){
        x = new TalonFX(xID);
        y = new TalonFX(yID);
        one = new CANSparkMax(oneID, MotorType.kBrushless);
        two = new CANSparkMax(twoID, MotorType.kBrushless);
        three = new CANSparkMax(threeID, MotorType.kBrushless);
        four = new CANSparkMax(fourID, MotorType.kBrushless);
        shooterConstants = new ShooterConstants();
        
        this.limelight = limelight;
        this.hood = hood;
        this.indexer = indexer;
        this.drivetrain = drivetrain;

        y.setInverted(true);

        x.setInverted(false);
        x.config_kP(0, Constants.LAUNCHER_KP);
        x.config_kI(0, Constants.LAUNCHER_KI);
        x.config_kD(0, Constants.LAUNCHER_KD);
        x.config_kF(0, Constants.LAUNCHER_KF);

        y.setInverted(true);
        y.config_kP(0, Constants.LAUNCHER_KP);
        y.config_kI(0, Constants.LAUNCHER_KI);
        y.config_kD(0, Constants.LAUNCHER_KD);
        y.config_kF(0, Constants.LAUNCHER_KF);

        // 41 - Back Top
        one.setInverted(true);
        // 42 - Back Bottom
        two.follow(one, true);
        // 43 - Front Bottom
        three.follow(one, true);
        // 44 - Front Top
        four.follow(one, false);
    }

    /**
     * Gets target velocity
     * @return Returns target velocity
     */
    public double getVelocity(){
        return this.velocity;
    }

    /**
     * Sets target velocity
     * @param velocity Target velocity
     */
    public void setVelocity(double velocity){
        this.velocity = velocity;
    }

    public void setMotorVelo(double velo) {
        x.set(TalonFXControlMode.Velocity, velo);
        y.set(TalonFXControlMode.Velocity, velo);
    }

    public void setMotorPercentage(double velo) {
        x.set(TalonFXControlMode.PercentOutput, velo);
        y.set(TalonFXControlMode.PercentOutput, velo);
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
    /*public void shoot(){
        ///all of these values are in meters or m/s
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

        groundDistance= 2.0;
        //2) have falcons approaching launching velocity   
        //double targetVelocity = getLaunchVelocity(targetAngle, groundDistance, height);
        x.config_kP(0, Constants.LAUNCHER_KP);
        x.config_kI(0, Constants.LAUNCHER_KI);
        x.config_kD(0, Constants.LAUNCHER_KD);

            
        x.config_kF(0, Constants.LAUNCHER_KF);
        
        //x.set(TalonFXControlMode.Velocity, targetVelocity * Constants.LAUNCHER_ENCODER_UNITS_PER_ROTATION / (Constants.LAUNCHER_WHEEL_CIRCUMFERENCE * 10));
        x.set(TalonFXControlMode.Velocity, velocity);

        //3) *after* falcons are at that velocity, run indexer & internal cansparkmaxes
        //if(x.getSelectedSensorVelocity(0) * Constants.LAUNCHER_WHEEL_CIRCUMFERENCE  * 10 / Constants.LAUNCHER_ENCODER_UNITS_PER_ROTATION >= 0.9*targetVelocity){
        if (Math.abs(x.getSelectedSensorVelocity() - velocity) <= 500
        ) {

            one.set(0.5);
            indexer.in();
        }
        //}      
    }*/

    /**
     * setpoints version of shoot method
     */

    public void alignSetpoint(){
        double tx = limelight.getXAngle();
        if (Math.abs(tx)>2.0){
            drivetrain.autoAlign();
            //tx = limelight.getXAngle();
        }

        
        double ty = limelight.getYAngle();
        double[] setPoint = shooterConstants.getSetpoint(shooterConstants.getNearestSetpointID(ty));
        double targetYAngle = setPoint[0]; //as in, the angle we want to get to, not the limelight target
        double hoodAngle = setPoint[1];
        /*
        0 - yAngle
        1 - hoodAngle
        2 - velocity
        */

        //autoalign to that setpoint
        
        if(Math.abs(ty - targetYAngle) > 2.0){
            drivetrain.setPointAlign(targetYAngle);
            //drivetrain.autoAlign();
        }

        //change the hood angle
        //hood.setSpeed(1);
        hood.set(hoodAngle);
    }

    public void shootAuto(){
        limelight.setLights(3);
        setMotorVelo(SmartDashboard.getNumber("Shooter Velocity", 0));

        double ty = limelight.getYAngle();
        double[] setPoint = shooterConstants.getSetpoint(shooterConstants.getNearestSetpointID(ty));
        double hoodAngle = setPoint[1];
        hood.set(hoodAngle);

        if (Math.abs(x.getSelectedSensorVelocity() - SmartDashboard.getNumber("Shooter Velocity", 0)) < 300 && Math.abs(hood.getPos() - hoodAngle) < 2) {
            one.set(0.5);
            indexer.in();
        }
    }

    public void shoot(){
        limelight.setLights(3);
        //ok, so our inconveniently written algorithm is:

        //auto-align to tx
        //figure out the closest setpoint via yangle
        //auto-align to that ty
        //auto-align tx again?????? //TODO: see which timing of auto-aligning tx is better
        //change the hood angle
        //set velocity of falcons
        //PEW PEW


        //auto-align to tx
        double tx = limelight.getXAngle();
        if (Math.abs(tx)>2.0){
            drivetrain.autoAlign();
            //tx = limelight.getXAngle();
        }

        //figure out the closest setpoint via yAngle (will be the one at setPointID)
        
        double ty = limelight.getYAngle();
        double[] setPoint = shooterConstants.getSetpoint(shooterConstants.getNearestSetpointID(ty));
        double targetYAngle = setPoint[0]; //as in, the angle we want to get to, not the limelight target
        double hoodAngle = setPoint[1];
        double targetVelocity = setPoint[2];
        /*
        0 - yAngle
        1 - hoodAngle
        2 - velocity
        */

        //autoalign to that setpoint
        
        if(Math.abs(ty - targetYAngle) > 2.0){
            drivetrain.setPointAlign(targetYAngle);
            //drivetrain.autoAlign();
        }

        //change the hood angle
        //hood.setSpeed(1);
        hood.set(hoodAngle);

        //set the velocity of the falcons
        setMotorVelo(targetVelocity);

        //pewpew
        if (Math.abs(x.getSelectedSensorVelocity() - targetVelocity) < 500 && Math.abs(hood.getPos() - hoodAngle) < 1.0) {
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

    /**
     * Stops everything
     */
    public void stopEverything(){
        setMotorPercentage(0);
        one.set(0);
        //limelight.setLights(1);
    }

    /**
     * Run tower belts
     * @param x Speed that tower belts will run at
     */
    public void runBelts(double x){
        one.set(x);
    }



    /* Ben's Shoot thing idk
    public void shoot() {
        double velocity;
        h = new Hashtable();
        h.put(keys, values); // idk
        velocity = h.get(limelight.getGroundDistance(Constants.UPPER_HUB_HEIGHT));
        x.set(TalonFXControlMode.Velocity, velocity);
    }
    */

    



}

