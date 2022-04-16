/*                                                     __________  __________  ___     ___      __________  __________  ___     ___
 +--^----------,--------,-----,--------^-,            /  ____   / /  _______/ /  /___ /  /     /  ____   / /  _______/ /  /___ /  /
 | |||||||||   `--------'     |          O           /  /___/  / /  /___     /  /   /   /     /  /___/  / /  /___     /  /   /   /
 `+---------------------------^----------|          /  _______/ /  ____/    /  /   /   /     /  _______/ /  ____/    /  /   /   /
   `\_,---------,---------,--------------'         /  /        /  /______  /   ___    /     /  /        /  /______  /   ___    /
     / XXXXXX /'|       /'                        /__/        /_________/ /___/  /___/     /__/        /_________/ /___/  /___/
    / XXXXXX /  `\    /'
   / XXXXXX /`-------'
  / XXXXXX /
 / XXXXXX /
(________(                
 `------'              
*/

package frc.robot.components;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.sensors.Limelight;

public class Shooter {
    // this will be madness and i will figure it out eventually - kai
    public TalonFX x, y;
    public CANSparkMax one, two, three, four;
    Limelight limelight;
    Hood hood;
    Indexer indexer;
    Drivetrain drivetrain;
    public double velocity;
    public ShooterConstants shooterConstants;
    Robot robot;
    Shoot shoot;

    double[] activeSetpoint;

    public boolean overrideTower, overrideShooter;

    public Shooter(Robot robot, int xID, int yID, int oneID, int twoID, int threeID, int fourID, Limelight limelight, Hood hood, Indexer indexer, Drivetrain drivetrain) {
        x = new TalonFX(xID);
        y = new TalonFX(yID);
        one = new CANSparkMax(oneID, MotorType.kBrushless);
        two = new CANSparkMax(twoID, MotorType.kBrushless);
        three = new CANSparkMax(threeID, MotorType.kBrushless);
        four = new CANSparkMax(fourID, MotorType.kBrushless);
        shooterConstants = new ShooterConstants(robot.limelight);
        
        shoot = new Shoot();

        overrideTower = false;
        overrideShooter = true;

        this.limelight = limelight;
        this.hood = hood;
        this.indexer = indexer;
        this.drivetrain = drivetrain;
        this.robot = robot;

        //y.set(ControlMode.Follower, x.getDeviceID());
        //y.setInverted(TalonFXInvertType.OpposeMaster);
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

        one.setIdleMode(IdleMode.kBrake);
        two.setIdleMode(IdleMode.kBrake);
        three.setIdleMode(IdleMode.kBrake);
        four.setIdleMode(IdleMode.kBrake);
    }

    public void setMotorVelo(double velo) {
        x.set(TalonFXControlMode.Velocity, velo);
        y.set(TalonFXControlMode.Velocity, velo);
    }

    public void setMotorPercentage(double velo) {
        x.set(TalonFXControlMode.PercentOutput, velo);
        y.set(TalonFXControlMode.PercentOutput, velo);
    }

    public double getMotorVelocity() {
        return x.getSelectedSensorVelocity();
    }

    public void overrideTower() {
        overrideTower = true;
    }

    public void overrideShooter() {
        overrideShooter = true;
    }

    public boolean getOverride() {
        return overrideTower;
    }

    public void setSetpoint() {
        double ty = limelight.getYAngle();
        
        if (overrideShooter) {
            activeSetpoint = shooterConstants.getNearestSetpoint(ty);
        } else {
            activeSetpoint = shooterConstants.getNearestSetpointRegression(ty);
        }
    }

    public double[] getSetpoint() {
        return activeSetpoint;
    }

    public boolean alignSetpoint() {
        limelight.setLights(3);

        //double ty = limelight.getYAngle();

        double[] setPoint = activeSetpoint;//shooterConstants.getNearestSetpoint(ty);
        //double[] setPoint = shooterConstants.getNearestSetpointRegression(ty);
        //double[] setPoint = shooterConstants.getSetpointWeirdRegression(ty);

        double targetYAngle = setPoint[0]; // as in, the angle we want to get to, not the limelight target
        double hoodAngle = setPoint[1];
        /*
         * 0 - yAngle
         * 1 - hoodAngle
         * 2 - velocity
         */

        // autoalign to that setpoint
        hood.set(hoodAngle);

        if (drivetrain.xAlign()) {
            drivetrain.yAlign(targetYAngle);
        }

        //are we aligned?
        return robot.drivetrain.isXAligned() && robot.drivetrain.isYAligned(targetYAngle);
    }

    public boolean shootVeloHoodAngle(double velo, double hoodAngle) {
        setMotorVelo(velo);
        hood.set(hoodAngle);

        long startTime = System.currentTimeMillis();
        while (startTime + (.15*1000) > System.currentTimeMillis()) {
            continue;
        }

        // pewpew

        if (Math.abs(x.getSelectedSensorVelocity() - velo) < 250) {
            one.set(.8);
            indexer.in();
            return true;
        } else
            return false;
    }


    private class Shoot extends Thread {

        Shoot thread;

        public void shoot() {
            thread = new Shoot();
            thread.start();
        }

        public void run() {
            robot.shooter.shootWithDelay();
        }
    }
//bruh 
    /**
     * pew the. the shoot. do the pew. the fricken. do the yeet
     * @return IS IT PEW O CLOCK??? HAS THE VELOCITY BEEN AN REACHED?????
     */
    public boolean shootWithDelay() {
        //BLIND THE ROOKIES
        limelight.setLights(3);

        //orion was here

        //get Values
        double ty = limelight.getYAngle();
        double[] setPoint = shooterConstants.getNearestSetpoint(ty);
        //double[] setPoint = shooterConstants.getNearestSetpointRegression(ty);
        //double[] setPoint = shooterConstants.getSetpointWeirdRegression(ty);
        double targetVelocity = setPoint[2];
        velocity = targetVelocity;

        /*
         * 0 - yAngle
         * 1 - hoodAngle
         * 2 - velocity
         */

        // set the velocity of the falcons
        if (robot.c.primary.getYButton()) {
            targetVelocity = SmartDashboard.getNumber("Target Velocity", 0);
        }

        setMotorVelo(targetVelocity);

        long startTime = System.currentTimeMillis();
        if (overrideTower) {
            while (startTime + (.15*1000) > System.currentTimeMillis()) {
                continue;
            }
        } else {
        while (Math.abs(x.getSelectedSensorVelocity() - targetVelocity) > 500) {
                if (System.currentTimeMillis() - startTime > 3000) break;
                setMotorVelo(targetVelocity);
                if (Math.abs(x.getSelectedSensorVelocity() - targetVelocity) > 500) {
                    indexer.stop();
                    one.set(0);
                }
            }
        }

        // pewpew

        if (Math.abs(x.getSelectedSensorVelocity() - targetVelocity) < 150) {
            one.set(.8);
            indexer.in();
            return true;
        } else
            return false;
    }

    public void shoot() {
        shoot.shoot();
    }

    public void stopEverything() {
        shoot.interrupt();
        interrupt();
    }

    /**
     * Stops everything
     */
    public void interrupt() {
        setMotorPercentage(0);
        one.set(0);
    }
}
