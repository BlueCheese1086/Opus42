package frc.robot.components;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
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
    double velocity;
    public ShooterConstants shooterConstants;
    Robot robot;

    // 4 cansparkmaxes????????? tbd
    public Shooter(Robot robot, int xID, int yID, int oneID, int twoID, int threeID, int fourID, Limelight limelight,
            Hood hood, Indexer indexer, Drivetrain drivetrain) {
        x = new TalonFX(xID);
        y = new TalonFX(yID);
        one = new CANSparkMax(oneID, MotorType.kBrushless);
        two = new CANSparkMax(twoID, MotorType.kBrushless);
        three = new CANSparkMax(threeID, MotorType.kBrushless);
        four = new CANSparkMax(fourID, MotorType.kBrushless);
        shooterConstants = new ShooterConstants(robot.limelight);

        this.limelight = limelight;
        this.hood = hood;
        this.indexer = indexer;
        this.drivetrain = drivetrain;
        this.robot = robot;

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
     * 
     * @return Returns target velocity
     */
    public double getVelocity() {
        return this.velocity;
    }

    /**
     * Sets target velocity
     * 
     * @param velocity Target velocity
     */
    public void setVelocity(double velocity) {
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



    public boolean alignSetpoint() {
        limelight.setLights(3);
        double tx = limelight.getXAngle();
        if (Math.abs(tx) > 2.0) {
            drivetrain.autoAlign();
            // tx = limelight.getXAngle();
        }

        double ty = limelight.getYAngle();
        // double ty = 1.0;
        double[] setPoint = shooterConstants.getNearestSetpoint(ty);//shooterConstants.getSetpoint(shooterConstants.getNearestSetpointID(ty));
        double targetYAngle = setPoint[0]; // as in, the angle we want to get to, not the limelight target
        double hoodAngle = setPoint[1];
        /*
         * 0 - yAngle
         * 1 - hoodAngle
         * 2 - velocity
         */

        // autoalign to that setpoint
        hood.set(hoodAngle);
        if (targetYAngle != -15.5) {
            if (Math.abs(ty - targetYAngle) > 2.0) {
                drivetrain.setPointAlign(targetYAngle);
                // drivetrain.autoAlign();
            }
        } else {
            targetYAngle = ty;
        }

        if (Math.abs(ty - targetYAngle) < 2.0 && Math.abs(tx) < 1.5) {
            return true;
        } else
            return false;

        // change the hood angle
        // hood.setSpeed(1);
    }

    public void autoAlign() {
        limelight.setLights(3);
        drivetrain.autoAlign();
    }

    public void shootAuto() {
        limelight.setLights(3);
        double tx = limelight.getXAngle();
        if (Math.abs(tx) > 2.0) {
            drivetrain.autoAlign();
            // tx = limelight.getXAngle();
        }
        setMotorVelo(SmartDashboard.getNumber("Shooter Velocity", 0));
//lol
        double ty = limelight.getYAngle();
        double[] setPoint = shooterConstants.getSetpoint(shooterConstants.getNearestSetpointID(ty));
        double hoodAngle = setPoint[1];
        hood.set(hoodAngle);

        if (Math.abs(x.getSelectedSensorVelocity() - SmartDashboard.getNumber("Shooter Velocity", 0)) < 300
                && Math.abs(hood.getPos() - hoodAngle) < 2) {
            one.set(0.5);
            indexer.in();
        }
    }

    public void autonomousShoot(double targetVelocity) {
        limelight.setLights(3);
        setMotorVelo(targetVelocity);
        double tx = limelight.getXAngle();
        if (Math.abs(tx) > 2.0) {
            drivetrain.autoAlign();
            // tx = limelight.getXAngle();
        }
        hood.set(0);
        if (Math.abs(x.getSelectedSensorVelocity() - targetVelocity) < 200) {
            one.set(0.35);
            indexer.in();
        }
    }

    public boolean shoot() {
        limelight.setLights(3);

        double ty = limelight.getYAngle();
        double[] setPoint = shooterConstants.getSetpoint(shooterConstants.getNearestSetpointID(ty));
        double targetYAngle = setPoint[0]; // as in, the angle we want to get to, not the limelight target
        double hoodAngle = setPoint[1];
        double targetVelocity = setPoint[2];
        velocity = targetVelocity;
        /*
         * 0 - yAngle
         * 1 - hoodAngle
         * 2 - velocity
         */
        //hood.set(hoodAngle);


        // set the velocity of the falcons
        if (robot.c.primary.getYButton()) {
            targetVelocity = SmartDashboard.getNumber("Shooter Velocity", 0);
        }
        setMotorVelo(targetVelocity);

        long startTime = System.currentTimeMillis();
        while (startTime + (.3*1000) > System.currentTimeMillis()) {
            continue;
        }

        // pewpew
        if (Math.abs(x.getSelectedSensorVelocity() - targetVelocity) < 100) {
            one.set(.75);
            indexer.in();
            return true;
        } else
            return false;
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
        limelight.setLights(1);
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
