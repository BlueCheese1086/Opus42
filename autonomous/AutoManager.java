package frc.robot.autonomous;

import java.util.ArrayList;
import java.util.Arrays;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Robot;
import frc.robot.autonomous.sections.*;

public class AutoManager {

    SendableChooser autoChooser;
    AutoMode auto1, auto2, auto3;
    
    public AutoManager() {

        autoChooser = new SendableChooser<AutoMode>();

        auto1 = new AutoMode("2ball");
    

        autoChooser.setDefaultOption(auto1.getName(), auto1);
        //autoChooser.addOption(auto2.getName(), auto2);
        //autoChooser.addOption(auto3.getName(), auto3);

        SmartDashboard.putData("Auto Mode Selector", autoChooser);

    }

    public AutoMode getAuto() {
        return (AutoMode)autoChooser.getSelected();
    }
}