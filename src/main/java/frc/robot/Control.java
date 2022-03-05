package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class Control {

    public enum Primary {
        RyanBox,
        YBox,
        Joystick;
    }

    public enum Secondary {
        Toshi;
    }

    static Primary primDriver;
    static Secondary suckondeeznutzDriver;

    public XboxController primary = new XboxController(0);
    public XboxController secondary = new XboxController(1);

    
    /*Joystick left = new Joystick(2);
    Joystick right = new Joystick(3);*/

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

    /**
     * @return Returns acceleration value based on driver profile
     */
    public double getDriveForward() {
        switch (primDriver) {
            case YBox:
                return Meth.doMagik(-primary.getLeftY());
            case RyanBox:
                return (Meth.doMagik(primary.getRightTriggerAxis()) - Meth.doMagik(primary.getLeftTriggerAxis()));
            default:
                return 0.0;
        }
    }

    /**
     * @return Returns B button pressed for braking toggle
     */
    public boolean getBrakeToggle() {
        switch (primDriver) {
            case RyanBox:
                return primary.getBButtonPressed();
            default:
                return false;
        }
    }

    /**
     * @return Returns turn axis value based on driver profile
     */
    public double getDriveTurn() {
        switch (primDriver) {
            case YBox:
                return (Meth.doTurnMagik(primary.getRightX()));
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
            default:
                return true;
        }
    }

    /**
     * @return Returns t/f for intake input
     */
    public boolean getIntakeIn() {
        switch (suckondeeznutzDriver) {
            case Toshi:
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
            case Toshi:
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
            case Toshi:
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
            case Toshi:
                return secondary.getRightBumper();
            default:
                return false;
        }
    }

    /**
     * @return Returns t/f for indexer out button
     */
    public boolean getIndexerOut() {
        switch (suckondeeznutzDriver) {
            case Toshi:
                return secondary.getLeftBumper();
            default:
                return false;
        }
    }

    /**
     * @return Returns t/f for secondary shoot button
     */
    public boolean getLauncherShoot() {
        switch (primDriver) {
            case RyanBox:
                return primary.getXButton();
            default:
                return false;
        }
    }

    /**
     * @return Raw double value of climb axis (deadzone .03)
     */
    public double getClimb() {
        switch (suckondeeznutzDriver) {
            case Toshi:
                return Meth.deadzone(secondary.getLeftY(), .03);
            default:
                return 0.0;
        }
    }
}
