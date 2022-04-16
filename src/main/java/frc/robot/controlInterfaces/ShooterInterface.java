package frc.robot.controlInterfaces;

import frc.robot.Control;
import frc.robot.Robot;
import frc.robot.components.Shooter;
import frc.robot.components.ShooterConstants;
import frc.robot.controlInterfaces.LightsInterface.ControlMode;

public class ShooterInterface extends Interface {
    Shooter shooter;
    ShooterConstants constants;
   // Shoot shoot;

    /*private class Shoot extends Thread {

        public void shoot() {
            Shoot thread = new Shoot();
            thread.start();
        }

        public void run() {
            robot.shooter.shoot();
        }
    }*/

    public ShooterInterface(Robot robot, Control c) {
        super(robot, c);
        // shooter = Robot.shooter;
        constants = new ShooterConstants(robot.limelight);
        //shoot = new Shoot();
    }

    /**
     * what the pewpew will do every tick
     */
    public void tick() {

        if (c.getLauncherAlign()) {
            if (c.primary.getAButtonPressed()) {
                robot.shooter.setSetpoint();
            }
            if (robot.shooter.alignSetpoint()) {
                robot.lightsInter.setControlMode(ControlMode.StaticColor);
                robot.lights.setLights(0, 255, 0);
            } else {
                robot.lightsInter.setControlMode(ControlMode.Alliance);
            }
        } else {
            robot.lightsInter.setControlMode(ControlMode.Alliance);
        }
        if (c.getLauncherShoot()) {
            //shoot.shoot();
            robot.shooter.shoot();
        }
        if (c.primary.getLeftStickButtonPressed()) {
            robot.shooter.overrideTower();
        }
        if (c.primary.getStartButtonPressed()) {
            robot.shooter.overrideShooter();
        }
        if (c.primary.getRightStickButton()) {
            robot.drivetrain.xAlign();
        }
        if (!c.getLauncherShoot() == !c.getLauncherAlign()) {
            robot.shooter.interrupt();
            //robot.shooter.stopEverything();
        }

        /*if (c.getLauncherShoot()) {
            robot.lights.rainbow();
            shoot.shoot();
        } else if (c.getLauncherAlign()) {
            robot.shooter.setMotorVelo(7000);
            if (robot.shooter.alignSetpoint()) {
                robot.lights.setLights(0, 255, 0);
            }
        } else if (c.primary.getRightStickButton()) {
            robot.drivetrain.yAlign(0);
        } else {
            robot.lights.setLights(0, 0, 255);
            robot.shooter.stopEverything();
        }*/
        
        if (c.primary.getPOV() == 0) {
            robot.hood.set(1.0);
        } else if (c.primary.getPOV() == 90) {
            robot.hood.set(0.5);
        } else if (c.primary.getPOV() == 180) {
            robot.hood.set(0.25);
        } else if (c.primary.getPOV() == 270) {
            robot.hood.set(0.0);
        }


        if (c.primary.getStartButtonPressed()) {
            robot.limelight.setLights(3);
            constants.setPoint(robot.limelight.getYAngle(), robot.hood.getPos(), robot.shooter.velocity);
            robot.limelight.setLights(1);
        }

    }

}
