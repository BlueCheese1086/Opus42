
package frc.robot.autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

import frc.robot.autonomous.sections.*;

public class AutoManager {

    SendableChooser<AutoMode> autoChooser;
    AutoMode auto1, auto2;

    Robot robot;

    public AutoManager(Robot robot) {

        autoChooser = robot.autoMode;
        this.robot = robot;

        auto1 = new AutoMode("2ball");
        auto2 = new AutoMode("1ball");

        //AutoTurn autoTurn1 = new AutoTurn(-29, robot);
        //auto1.addSection(autoTurn1, 0);
        //double waitTime = (autoTurn1.getEndTime() - autoTurn1.getStartTime())/1000;
        AutoDrive autoDrive1 = new AutoDrive(116, robot);
        auto1.addSection(autoDrive1, 0);
        double intakeTime = (autoDrive1.getEndTime() - autoDrive1.getStartTime())/1000;
        auto1.addSection(new AutoWait(intakeTime * 0.3), 0);
        //auto1.addSection(new AutoTurn(-119), 0);
        auto1.addSection(new AutoShoot(5), 0);

        //auto1.addSection(new AutoWait(waitTime), 1);
        auto1.addSection(new AutoIntake(intakeTime * 1.3, robot), 1);

        auto2.addSection(new AutoShoot(3), 0);
        auto2.addSection(new AutoDrive(2, robot, false), 0);

        autoChooser.setDefaultOption(auto1.getName(), auto1);
        autoChooser.addOption(auto2.getName(), auto2);
        //autoChooser.addOption(auto3.getName(), auto3);

        //SmartDashboard.putData("Auto Mode Selector", autoChooser);

    }

    public AutoMode getAuto() {
        return (AutoMode)autoChooser.getSelected();
    }

    public void update() {
        SmartDashboard.putString("Current Auto", autoChooser.getSelected().name);
        //autoChooser.getSelected().update();
        //auto2.update();
        autoChooser.getSelected().update();
    }

}