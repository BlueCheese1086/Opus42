package frc.robot.components;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class Launcher {
    //this will be madness and i will figure it out eventually - kai

    //todo: find appropriate names
    TalonFX x, y;
    //5 cansparkmaxes????????? tbd
    public Launcher(int xID, int yID){
        x = new TalonFX(xID);
        y = new TalonFX(yID);
    }
}
