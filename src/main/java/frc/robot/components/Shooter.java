package frc.robot.components;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import java.util.Collections;
import java.util.Hashtable;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.sensors.Limelight;

public class Shooter {
    // this will be madness and i will figure it out eventually - kai

    // todo: find appropriate names
    public TalonFX x, y;
    public CANSparkMax one, two, three, four;
    Limelight limelight;
    Hood hood;
    Indexer indexer;
    Drivetrain drivetrain;

    // Velocity Distance Stuff
    double velocity;
    Hashtable<Double, Double> h;

    Robot robot;


    // 4 cansparkmaxes????????? tbd
    public Shooter(Robot r, int xID, int yID, int oneID, int twoID, int threeID, int fourID, Limelight limelight,
            Hood hood, Indexer indexer, Drivetrain drivetrain) {
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

        robot = r;

        this.velocity = 0;
        h = new Hashtable<>();

        x.setInverted(false);
        y.setInverted(true);
        
        x.config_kP(0, Constants.LAUNCHER_KP);
        x.config_kI(0, Constants.LAUNCHER_KI);
        x.config_kD(0, Constants.LAUNCHER_KD);
        x.config_kF(0, Constants.LAUNCHER_KF);

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
     * 
     * @return Returns target velocity
     */
    public double getVelocity() {
        return this.velocity;
    }

    /**
     * Sets target velocity
     * @param velocity Target velocity
     */
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    /*
     * notes for future kai and emily-
     * | upper hub
     * |
     * | X
     * |
     * x |
     * opus42 ----------------
     * Y
     * X = 264 cm
     * x = camera mounting angle + ty from limelight(converted to degrees)
     * ^ use that to set the hood angle ^
     * 
     * Y = (X - cameraHeight)/tan(x) (this is what is in Limelight.java)
     * ^ use that to set launching velocity^
     * 1) set hood angle
     * 2) have falcons approach launching velocity
     * 3) *after* falcons are at that velocity, run indexer & internal cansparkmaxes
     * 4) PEW PEW
     */

    /*
     * public void shoot(){
     * //all of these values are in meters or m/s
     * double targetVelocity = Constants.LAUNCHER_DEFAULT_VELOCITY;
     * double groundDistance =
     * limelight.getGroundDistance(Constants.UPPER_HUB_HEIGHT);
     * double height = Constants.UPPER_HUB_HEIGHT + Constants.CARGO_DIAMETER -
     * Constants.CAMERA_HEIGHT + 2;
     * //0) autoalign
     * double tx = limelight.getXAngle();
     * while(Math.abs(tx)>1.0){
     * drivetrain.autoAlign();
     * tx = limelight.getXAngle();
     * }
     * //1) set hood angle
     * double targetAngle = getLaunchAngle(targetVelocity, groundDistance, height) +
     * 45;
     * hood.set(targetAngle);
     * //2) have falcons approaching launching velocity
     * x.config_kP(0, Constants.LAUNCHER_KP);
     * x.config_kI(0, Constants.LAUNCHER_KI);
     * x.config_kD(0, Constants.LAUNCHER_KD);
     * /*
     *
     * See documentation for calculation details.
     * If using velocity, motion magic, or motion profile,
     * use (1023 * duty-cycle / sensor-velocity-sensor-units-per-100ms).
     * 
     * 
     * 
     * x.config_kF(0, Constants.LAUNCHER_KF);
     * x.set(TalonFXControlMode.Velocity, targetVelocity /
     * (Constants.LAUNCHER_WHEEL_CIRCUMFERENCE *
     * Constants.LAUNCHER_ENCODER_UNITS_PER_ROTATION * 10));
     * //3) *after* falcons are at that velocity, run indexer & internal
     * cansparkmaxes
     * if(x.getSelectedSensorVelocity(0) * Constants.LAUNCHER_WHEEL_CIRCUMFERENCE *
     * Constants.LAUNCHER_ENCODER_UNITS_PER_ROTATION * 10 >= 0.9*targetVelocity){
     * one.set(0.5);
     * indexer.in();
     * }
     * }
     */

    /**
     * Hashtable stuff idk
     */
    public void putData(double distance, double velo) {
        robot.distanceVelo.addOption(String.valueOf(Math.floor(distance)) + " - " + String.valueOf(Math.floor(velo)), "");
        h.put(distance, velo);
    }

    /**
     * Hastable sorting
     */
    public double getClosestVelo(double distance) {
        double closestDistance = 0;
        for (double i : Collections.list(h.keys())) {
            if (Math.abs(distance - i) < closestDistance) {
                closestDistance = i;
            }
        }
        return closestDistance;
    }

    /**
     * pew pew
     */
    public void shoot() {
        /// all of these values are in meters or m/s
        // double targetVelocity = Constants.LAUNCHER_DEFAULT_VELOCITY;
        double groundDistance = limelight.getGroundDistance(Constants.UPPER_HUB_HEIGHT);
        double height = Constants.UPPER_HUB_HEIGHT + Constants.CARGO_DIAMETER - Constants.CAMERA_HEIGHT + 2;
        double targetAngle = Constants.LAUNCHER_MIN_ANGLE;

        // 0) autoalign
        double tx = limelight.getXAngle();
        while (Math.abs(tx) > 3.0) {
            drivetrain.autoAlign();
            tx = limelight.getXAngle();
        }

        // 1) set hood angle
        // double targetAngle = getLaunchAngle(targetVelocity, groundDistance, height) +
        // 45;
        // hood.set(targetAngle);
        //hood.setMax();

        groundDistance = 2.0;
        // 2) have falcons approaching launching velocity
        // double targetVelocity = getLaunchVelocity(targetAngle, groundDistance,
        // height);

        /*
         *
         * See documentation for calculation details.
         * If using velocity, motion magic, or motion profile,
         * use (1023 * duty-cycle / sensor-velocity-sensor-units-per-100ms).
         * 
         */


        // x.set(TalonFXControlMode.Velocity, targetVelocity *
        // Constants.LAUNCHER_ENCODER_UNITS_PER_ROTATION /
        // (Constants.LAUNCHER_WHEEL_CIRCUMFERENCE * 10));
        /*if (h.size() > 0) {
            velocity = h.get(this.getClosestVelo(limelight.getGroundDistance(Constants.UPPER_HUB_HEIGHT)));
        } else {
            velocity = SmartDashboard.getNumber("Shooter Velocity", 0);
        }*/
        velocity = SmartDashboard.getNumber("Shooter Velocity", 0);

        //x.set(TalonFXControlMode.Velocity, velocity);
        setMotorVelo(velocity);

        // 3) *after* falcons are at that velocity, run indexer & internal cansparkmaxes
        // if(x.getSelectedSensorVelocity(0) * Constants.LAUNCHER_WHEEL_CIRCUMFERENCE *
        // 10 / Constants.LAUNCHER_ENCODER_UNITS_PER_ROTATION >= 0.9*targetVelocity){
        if (Math.abs(x.getSelectedSensorVelocity() - velocity) <= 500) {
            one.set(0.5);
            indexer.in();
        }
        // }
    }

    public void setMotorVelo(double velo) {
        x.set(TalonFXControlMode.Velocity, velo);
        y.set(TalonFXControlMode.Velocity, velo);
    }

    public void setMotorPercentage(double percentage) {
        x.set(TalonFXControlMode.PercentOutput, percentage);
        y.set(TalonFXControlMode.PercentOutput, percentage);
    }

    /**
     * figuring out target velocity
     * 
     * @param targetVelocity the velocity the ball will travel at
     * @param groundDistance the distance between the robot and the upper hub
     * @param height         the height the ball needs to be at when it reaches
     */
    public double getLaunchAngle(double targetVelocity, double groundDistance, double height) {
        // this formula was obtained with help from lokesh pillai
        return Math.atan(((2 * Math.pow(targetVelocity, 2)) + Math.sqrt((4 * Math.pow(targetVelocity, 4) - (4
                * Constants.GRAVITY
                * ((2 * Math.pow(targetVelocity, 2) * height) + (Constants.GRAVITY * Math.pow(groundDistance, 2)))))))
                / (2 * Constants.GRAVITY * groundDistance));
    }

    /**
     * figuring out target velocity
     * 
     * @param targetAngle    the angle the hood is at
     * @param groundDistance the distance between the robot and the upper hub
     * @param height         the height the ball needs to be at when it reaches
     */
    public double getLaunchVelocity(double targetAngle, double groundDistance, double height) {
        return Math.sqrt((-1 * Constants.GRAVITY * Math.pow(groundDistance, 2) * Math.pow(sec(targetAngle), 2))
                / (2 * (height - (groundDistance * Math.tan(targetAngle)))));
    }

    /**
     * literally just 1/cos for sec bc i could not be bothered to be literate
     * 
     * @param a the angle
     */
    public double sec(double a) {
        return 1.0 / Math.cos(a);
    }

    /**
     * Stops everything
     */
    public void stopEverything() {
        setMotorPercentage(0);
        one.set(0);
    }

    /**
     * Run tower belts
     * 
     * @param x Speed that tower belts will run at
     */
    public void runBelts(double x) {
        one.set(x);
    }
}
