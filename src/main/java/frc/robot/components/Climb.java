package frc.robot.components;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

public class Climb {
    public CANSparkMax left, right;
    Solenoid lock;
    public Climb(int leftID, int rightID, int solenoidID) {
        left = new CANSparkMax(leftID, MotorType.kBrushless);
        right = new CANSparkMax(rightID, MotorType.kBrushless);
        lock = new Solenoid(PneumaticsModuleType.CTREPCM, solenoidID);

        left.setIdleMode(IdleMode.kBrake);
        right.setIdleMode(IdleMode.kBrake);
    }

    /**
     * Sets climb motor speed
     * @param c How fast you want the motors to go
     */
    public void setLeft(double c) {
        left.set(c);
    }

    public void setRight(double c) {
        right.set(c);
    }

    /**
     * Locks climb solenoid
     */
    public void enable() {
        lock.set(true);
    }

    /**
     * Gets climb solenoid position
     * @return Returns solenoid position
     */
    public boolean getLock() {
        return lock.get();
    }

    /**
     * Unlocks lock solenoid
     */
    public void disable() {
        lock.set(false);
    }
}