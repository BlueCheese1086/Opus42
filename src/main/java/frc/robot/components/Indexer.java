package frc.robot.components;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Indexer {
    CANSparkMax left, right;
    public Indexer(int leftID, int rightID){
        left = new CANSparkMax(leftID, MotorType.kBrushless);
        right = new CANSparkMax(rightID, MotorType.kBrushless);
    }

    /**
     * makes indexer intake balls into launcher/run inward
     */
    public void in(){

    }

     /**
     * makes indexer outtake balls/run outward at a slower speed for dejamming
     */
    public void out(){

    }

}
