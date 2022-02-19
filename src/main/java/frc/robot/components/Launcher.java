package frc.robot.components;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Launcher {
    //this will be madness and i will figure it out eventually - kai

    //todo: find appropriate names
    TalonFX x, y;
    CANSparkMax one, two, three, four, five;
    //5 cansparkmaxes????????? tbd
    public Launcher(int xID, int yID, int oneID, int twoID, int threeID, int fourID, int fiveID){
        x = new TalonFX(xID);
        y = new TalonFX(yID);
        one = new CANSparkMax(oneID, MotorType.kBrushless);
        two = new CANSparkMax(twoID, MotorType.kBrushless);
        three = new CANSparkMax(threeID, MotorType.kBrushless);
        four = new CANSparkMax(fourID, MotorType.kBrushless);
        five = new CANSparkMax(fiveID, MotorType.kBrushless);
    }
}
