package frc.robot.components;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Climb {
    CANSparkMax left, right;
    public Climb(int leftID, int rightID){
        left = new CANSparkMax(leftID, MotorType.kBrushless);
        right = new CANSparkMax(rightID, MotorType.kBrushless);
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
