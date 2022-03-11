/**
 *    █████
 *  █░ ░░░███
 *  █████████
 *  █████████ AMOGUS
 * ███ ██████
 */
package frc.robot.autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

import frc.robot.autonomous.sections.*;

public class AutoManager {

    SendableChooser<AutoMode> autoChooser;
    AutoMode auto1, auto2, auto3, auto4, auto5, twoBallAuto;

    Robot robot;

    public AutoManager(Robot robot) {

        autoChooser = robot.autoMode;
        this.robot = robot;

        auto1 = new AutoMode("test");
        auto2 = new AutoMode("1ball");
        twoBallAuto = new AutoMode("2Ball - USE ME");
        auto3 = new AutoMode("2ball official");
        auto4 = new AutoMode("3ball");
        auto5 = new AutoMode("taxi");

        auto5.addSection(new AutoDrive(1.2, robot, false), 0);
        auto5.addSection(new AutoIntake(1.8, robot), 1);

        //auto4.addSection(new AutoTurn(1, true, SmartDashboard.getNumber("3Ball Turn Speed", 0), robot), 0);
/** 

        //auto4.addSection(new AutoAutoAlign(1, robot), 0);
        //auto4.addSection(new AutoShoot(2, robot), 0);
        //auto4.addSection(new AutoTurn(1, true, 0.05, robot), 0);
        auto4.addSection(new AutoDrive(3, robot, false), 0);
        auto4.addSection(new AutoTurn(1, false, 0.05, robot), 0);
        auto4.addSection(new AutoShoot(2, robot), 0);
        //auto4.addSection(new AutoTurn(1, true, 0.05, robot), 0);
        //auto4.addSection(new AutoDrive(1, robot, true), 0);
        auto4.addSection(new AutoTurn(1, true, SmartDashboard.getNumber("3Ball Turn Speed", 0), robot), 0);
        auto4.addSection(new AutoDrive(2, robot, false), 0);
        auto4.addSection(new AutoTurn(1, false, SmartDashboard.getNumber("3Ball Turn Speed", 0), robot), 0);
        auto4.addSection(new AutoAutoAlign(1, robot), 0);
        auto4.addSection(new AutoShoot(3, robot), 0);

        //auto4.addSection(new AutoWait(3), 1);
        auto4.addSection(new AutoIntake(3, robot), 1);
        auto4.addSection(new AutoWait(6), 1);
        auto4.addSection(new AutoIntake(2, robot), 1);

        
*/



        
        auto3.addSection(new AutoDrive(1.15, robot, false), 0);
        auto3.addSection(new AutoDrive(1, robot, true), 0);
        //auto3.addSection(new AutoAutoAlign(1, robot), 0);
        auto3.addSection(new AutoShoot(3, robot), 0);

        //auto3.addSection(new AutoWait(1), 1);
        auto3.addSection(new AutoIntake(1.2, robot), 1);

        auto1.addSection(new AutoDrive(2, robot, false), 0);
        auto1.addSection(new AutoIntake(2.2, robot), 1);
        auto1.addSection(new AutoDrive(2, robot, true), 0);
        auto1.addSection(new AutoShoot(4, robot), 0);

        /**
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

        */

        auto2.addSection(new AutoWait(3), 0);
        auto2.addSection(new AutoShoot(3, robot), 0);
        auto2.addSection(new AutoDrive(1, robot, false), 0);

        auto2.addSection(new AutoWait(6), 1);
        auto2.addSection(new AutoIntake(1.4, robot), 1);

        twoBallAuto.addSection(new AutoWait(2), 0);
        twoBallAuto.addSection(new AutoShoot(3, robot), 0);
        twoBallAuto.addSection(new AutoDrive(1.05, robot, false), 0);
        twoBallAuto.addSection(new AutoWait(5), 1);
        twoBallAuto.addSection(new AutoIntake(2.5, robot), 1);
        twoBallAuto.addSection(new AutoWait(1.5), 0);
        twoBallAuto.addSection(new AutoDrive(1, robot, true), 0);
        twoBallAuto.addSection(new AutoShoot(3, robot), 0);

        /*
        auto2.addSection(new AutoDrive(1.15, robot, false), 0);
        auto2.addSection(new AutoShoot(3, robot), 0);

        auto2.addSection(new AutoIntake(1.2, robot), 1);
        
        */

        autoChooser.addOption(auto1.getName(), auto1);
        autoChooser.addOption(auto2.getName(), auto2);
        autoChooser.addOption(auto3.getName(), auto3);
        autoChooser.addOption(auto4.getName(), auto4);
        autoChooser.addOption(auto5.getName(), auto5);
        autoChooser.setDefaultOption(twoBallAuto.getName(), twoBallAuto);

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