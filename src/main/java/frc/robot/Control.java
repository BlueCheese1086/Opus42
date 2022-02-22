package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

public class Controls {

    public enum Primary {
        YBox,
        RyanBox,
        Joystick;

        private Primary() {

        }

        public static Primary getByString(String s) {
            for (Primary a : Primary.values()) {
                if (s.equalsIgnoreCase(a.name())) {
                    return a;
                }
            }
            return YBox;
        }
    }

    public enum Secondary {
        Harrison;
    }

    static Primary primDriver;
    static Secondary suckondeeznutzDriver;

    XboxController primary = new XboxController(0);
    XboxController secondary = new XboxController(1);

    Joystick left = new Joystick(2);
    Joystick right = new Joystick(3);

    /**
     * @param p Primary driver to set control scheme
     */
    public void setPrimary(Primary p) {
        primDriver = p;
    }

    /**
     * @param sux Secondary driver to set control scheme
     */
    public void setSecondary(Secondary sux) {
        suckondeeznutzDriver = sux;
    }

    /**
     * @return Returns acceleration value based on driver profile
     */
    public double getDriveForward() {
        switch (primDriver) {
            case YBox:
                return Meth.doMagik(-primary.getLeftY());
            case Joystick:
                return (Meth.doMagik(-left.getRawAxis(1)));
            case RyanBox:
                return (Meth.doMagik(primary.getRightTriggerAxis()) - Meth.doMagik(primary.getLeftTriggerAxis()));
            default:
                return 0.0;
        }
    }

    /**
     * @return Returns turn axis value based on driver profile
     */
    public double getDriveTurn() {
        switch (primDriver) {
            case YBox:
                return (Meth.doTurnMagik(primary.getRightX()));
            case Joystick:
                return Meth.doTurnMagik(right.getRawAxis(0));
            case RyanBox:
                /*if (getDriveForward() < 0) {
                    return Meth.doTurnMagik(-primary.getLeftX());
                }*/
                return Meth.doTurnMagik(primary.getLeftX());
            default:
                return 0.0;
        }
    }

    /**
     * @return Returns true because false cringe
     */
    public boolean getSafety() {
        switch (primDriver) {
            case Joystick:
                left.getTrigger();
            default:
                return true;
        }
    }

    /**
     * @return Returns t/f for intake input
     */
    public boolean getIntakeIn() {
        switch (suckondeeznutzDriver) {
            case Harrison:
                return secondary.getPOV() == 0;
            default:
                return false;
        }
    }

    /**
     * @return Returns t/f for intake output
     */
    public boolean getIntakeOut() {
        switch (suckondeeznutzDriver) {
            case Harrison:
                return secondary.getPOV() == 180;
            default:
                return false;
        }
    }

    /**
     * @return Returns t/f for intake toggle button pressed
     */
    public boolean getIntakeToggle() {
        switch (suckondeeznutzDriver) {
            case Harrison:
                return secondary.getBButtonPressed();
            default:
                return false;
        }
    }

    /**
     * @return Returns t/f for indexer in button
     */
    public boolean getIndexerIn() {
        switch (suckondeeznutzDriver) {
            case Harrison:
                return secondary.getYButton();
            default:
                return false;
        }
    }

    /**
     * @return Returns t/f for indexer out button
     */
    public boolean getIndexerOut() {
        switch (suckondeeznutzDriver) {
            case Harrison:
                return secondary.getAButton();
            default:
                return false;
        }
    }

    /**
     * @return Returns t/f for secondary shoot button
     */
    public boolean getLauncherShoot() {
        switch (suckondeeznutzDriver) {
            case Harrison:
                return secondary.getXButton();
            default:
                return false;
        }
    }

    /**
     * @return Raw double value of climb axis (deadzone .03)
     */
    public double getClimb() {
        switch (suckondeeznutzDriver) {
            case Harrison:
                return Meth.deadzone(secondary.getLeftY(), .03);
            default:
                return 0.0;
        }
    }

    public boolean getBrake() {
        switch (primDriver) {
            case RyanBox:
                return primary.getPOV() == 0;
            default:
                return false;
        }
    }

    // not sure what controllers we're using for drive right now, but necessary
    // methods include:
    /*
     * --getDriveForward (returns a double)--
     * --getDriveTurn (returns a double)--
     * --getSafety--
     * --getIntakeIn--
     * --getIntakeOut--
     * --getIntakeToggle-- //returns a booleans
     * --getIndexerIn--
     * --getIndexerOut--
     * --getLauncherShoot--
     * --getClimbUp--
     * --getClimbDown--
     */
}
