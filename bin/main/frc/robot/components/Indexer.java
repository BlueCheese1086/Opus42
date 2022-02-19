package frc.robot.components;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Indexer {
    CANSparkMax left, right;
    public Indexer(int leftID, int rightID){
        left = new CANSparkMax(leftID, MotorType.kBrushless);
        right = new CANSparkMax(rightID, MotorType.kBrushless);
        right.setInverted(true);
    }

    /**
     * makes indexer intake balls into launcher/run inward
     */
    public void in(){
        left.set(0.5);
        right.set(0.5);
    }

     /**
     * makes indexer outtake balls/run outward at a slower speed for dejamming
     */
    public void out(){
        left.set(-0.25);
        right.set(-0.25);
    }

    /**
     * sets indexer motors to zero if no buttons are pushed
     */
    public void stop() {
        left.set(0.0);
        right.set(0.0);
    }
}