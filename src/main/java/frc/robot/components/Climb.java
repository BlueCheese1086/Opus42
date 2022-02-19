package frc.robot.components;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Climb {
    CANSparkMax left, right;
    public Climb(int leftID, int rightID){
        left = new CANSparkMax(leftID, MotorType.kBrushless);
        right = new CANSparkMax(rightID, MotorType.kBrushless);
    }

    public void toggle(double c){
        c = c/2.0;
        if(Math.abs(c) >= 0.30) {
            c *= 0.30/Math.abs(c);
        }
        left.set(c);
        right.set(c);
    }

    /**
     * pulling robot up (pulling climb inward)
     */
    public void up(){

    }

    /**
     * lowering robot (pulling climb outward)
     */
    public void down(){

    }
}
