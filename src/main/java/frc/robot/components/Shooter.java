package frc.robot.components;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import java.lang.Math.*;
import frc.robot.Constants;
import frc.robot.sensors.Limelight;

public class Shooter {
    //this will be madness and i will figure it out eventually - kai

    //todo: find appropriate names
    TalonFX x, y;
    CANSparkMax one, two, three, four, five;
    Limelight limelight;
    Hood hood;
    Indexer indexer;
    //5 cansparkmaxes????????? tbd
    public Shooter(int xID, int yID, int oneID, int twoID, int threeID, int fourID, int fiveID, Limelight limelight, Hood hood, Indexer indexer){
        x = new TalonFX(xID);
        y = new TalonFX(yID);
        one = new CANSparkMax(oneID, MotorType.kBrushless);
        two = new CANSparkMax(twoID, MotorType.kBrushless);
        three = new CANSparkMax(threeID, MotorType.kBrushless);
        four = new CANSparkMax(fourID, MotorType.kBrushless);
        five = new CANSparkMax(fiveID, MotorType.kBrushless);
        this.limelight = limelight;
        this.hood = hood;
        this.indexer = indexer;

        y.follow(x);
        x.config_kP(0, 0.05);
        x.config_kI(0, 0);
        x.config_kD(0, 0);

        two.follow(one);
        three.follow(one);
        four.follow(one);
        five.follow(one);
    }

    /*notes for future kai and emily-

                            | upper hub
                            |
                            | X
                            |
            x               |
    opus42 ----------------
                   Y

    X = 264 cm
    x = camera mounting angle  +  ty from limelight(converted to degrees) 
        ^ use that to set the hood angle ^
        
    Y = (X - cameraHeight)/tan(x) (this is what is in Limelight.java)
        ^ use that to set launching velocity^

    1) set hood angle
    2) have falcons approach launching velocity
    3) *after* falcons are at that velocity, run indexer & internal cansparkmaxes
    4) PEW PEW
    */

    public void shoot(){  
        double targetVelocity = Constants.LAUNCHER_DEFAULT_VELOCITY;
        double groundDistance = limelight.getGroundDistance(Constants.UPPER_HUB_HEIGHT);
        double height = Constants.UPPER_HUB_HEIGHT + Constants.CARGO_DIAMETER + 2;


        //1) set hood angle 
        //this formula was obtained with help from lokesh pillai
        double targetAngle = Math.atan( ((2 * Math.pow(targetVelocity, 2)) + Math.sqrt( (4 * Math.pow(targetVelocity, 4) - (4 * Constants.GRAVITY * ((2 * Math.pow(targetVelocity, 2) * height) + (Constants.GRAVITY * Math.pow(groundDistance, 2))))))) / (2 * Constants.GRAVITY * groundDistance));

        hood.set(targetAngle);

        //2) have falcons approaching launching velocity       
        x.set(TalonFXControlMode.Velocity, targetVelocity);

        //3) *after* falcons are at that velocity, run indexer & internal cansparkmaxes
        if(x.getSelectedSensorVelocity(0) >= 0.9*targetVelocity){
            one.set(0.5);
            indexer.in();
        }
        
    }
}
