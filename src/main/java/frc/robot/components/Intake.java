package frc.robot.components;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

public class Intake {
    CANSparkMax motor;
    Solenoid solenoid;
    
    public Intake(int motorID, int solenoidID) {
        motor = new CANSparkMax(motorID, MotorType.kBrushless);
        solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, solenoidID);
    }

     /**
     * runs intake inward to intake balls into robot
     */
    public void in() {
        motor.set(-1);
    }

     /**
     * runs intake outward for dejamming
     */
    public void out() {
        motor.set(1);
    }
    
    /**
     * makes intake do nothing to prevent overheating
     */
    public void neutral() {
        motor.set(0);
    }

    /**
     * puts intake in up position
     */
    public void up() {
        if (solenoid.get()) {
            solenoid.set(false);
        }
    }

    /**
     * Toggles intake position
     */
    public void toggle() {
        solenoid.set(!solenoid.get());
    }

    /**
     * Gets current intake position
     * @return Returns current intake position
     */
    public boolean getPos() {
        return solenoid.get();
    }

    /**
     * puts intake in down position
     */
    public void down() {
        if (!solenoid.get()) {
            solenoid.set(true);
        }
    }

}