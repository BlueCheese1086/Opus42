package frc.robot.components;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;

public class Intake {
    CANSparkMax motor;
    Solenoid solenoid;
    
    public Intake(int motorID){
        motor = new CANSparkMax(motorID, MotorType.kBrushless);
    }

     /**
     * runs intake inward to intake balls into robot
     */
    public void in(){

    }

     /**
     * runs intake outward for dejamming
     */
    public void out(){

    }

    /**
     * puts intake in up position
     */
    public void up(){

    }

    /**
     * puts intake in down position
     */
    public void down(){

    }

    
}
