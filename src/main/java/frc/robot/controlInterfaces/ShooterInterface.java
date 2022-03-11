package frc.robot.controlInterfaces;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import frc.robot.Control;
import frc.robot.Robot;
import frc.robot.components.Shooter;
import frc.robot.components.ShooterConstants;

public class ShooterInterface extends Interface {
    Shooter shooter;
    ShooterConstants constants;
    boolean shotYet;

    public ShooterInterface(Robot robot, Control c) {
        super(robot, c);
        shotYet = false;
        // shooter = Robot.shooter;
        constants = new ShooterConstants(robot.limelight);
    }

    /**
     * what the pewpew will do every tick
     */
    public void tick() {
        if (c.getLauncherShoot()) {
            robot.limelight.setLights(3);
            // robot.shooter.setVelocity(SmartDashboard.getNumber("Shooter Velocity", 0));
            // shooter.shoot();
            robot.shooter.shoot();
            shotYet = true;
        } else if (c.getLauncherAlign()) {
            robot.shooter.setMotorVelo(7000);
            robot.limelight.setLights(3);
            if (robot.shooter.alignSetpoint()) {
                c.primary.setRumble(RumbleType.kRightRumble, 1);
                c.primary.setRumble(RumbleType.kLeftRumble, 1);
            }
        } else {
            // robot.limelight.setLights(1);
            c.primary.setRumble(RumbleType.kRightRumble, 0);
            c.primary.setRumble(RumbleType.kLeftRumble, 0);
            robot.shooter.stopEverything();
            shotYet = false;
        }

        /*
         * if (c.primary.getPOV() == 270) {
         * constants.setPoint(robot.limelight.getYAngle(), robot.hood.getPos(),
         * robot.shooter.getVelocity());
         * //robot.distanceVelo.addOption("name", object);
         * }
         */

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
            constants.setPoint(robot.limelight.getYAngle(), robot.hood.getPos(), robot.shooter.getVelocity());
            robot.limelight.setLights(1);
        }

    }

}
