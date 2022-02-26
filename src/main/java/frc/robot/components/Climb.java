package frc.robot.components;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

public class Climb {
    CANSparkMax left, right;
    Solenoid lock;
    public Climb(int leftID, int rightID, int solenoidID) {
        left = new CANSparkMax(leftID, MotorType.kBrushless);
        right = new CANSparkMax(rightID, MotorType.kBrushless);
        lock = new Solenoid(PneumaticsModuleType.CTREPCM, solenoidID);

        right.follow(left);
    }

    public void set(double c) {
        c = c/2.0;
        if(Math.abs(c) >= 0.30) {
            c *= 0.30/Math.abs(c);
        }
        left.set(c);
    }

    public void lock() {
        lock.set(false);
    }

    public void unlock() {
        lock.set(true);
    }
}