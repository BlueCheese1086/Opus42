package frc.robot.autonomous;

import java.util.ArrayList;
import java.util.Arrays;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Robot;
import frc.robot.autonomous.sections.*;

public class AutoManager {

    SendableChooser autoChooser;
    AutoMode auto1;
    
    
    public AutoManager() {

        autoChooser = new SendableChooser<AutoMode>();

        auto1 = new AutoMode("2ball");

        auto1.addSection(new AutoTurn(119), 0);
        int waitTime = (AutoTurn.super.getEndTime() - AutoTurn.super.getStartTime())/1000;
        auto1.addSection(new AutoDrive(116), 0);
        int intakeTime = (AutoDrive.super.getEndTime() - AutoDrive.super.getStartTime())/1000;
        auto1.addSection(new AutoWait(intakeTime * 0.3), 0)
        auto1.addSection(new AutoTurn(-119), 0);
        auto1.addSection(new AutoShoot(length), 0);

        auto1.addSection(new AutoWait(waitTime), 1);
        auto1.addSection(new AutoIntake(intakeTime * 1.3), 1);

    

        autoChooser.setDefaultOption(auto1.getName(), auto1);
        //autoChooser.addOption(auto2.getName(), auto2);
        //autoChooser.addOption(auto3.getName(), auto3);

        SmartDashboard.putData("Auto Mode Selector", autoChooser);

    }

    public AutoMode getAuto() {
        return (AutoMode)autoChooser.getSelected();
    }
}