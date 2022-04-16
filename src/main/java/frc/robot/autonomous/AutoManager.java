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
    AutoMode twoBall, oneBall, threeBall, intercept, twoBallAlign;
    public AutoMode test;
    AutoMode auto4;

    Robot robot;

    public AutoManager(Robot robot) {
        robot.limelight.setLights(3);
        System.out.println("AutoManager stuff...");

        autoChooser = robot.autoMode;
        this.robot = robot;

        twoBall = new AutoMode("2ball");
        oneBall = new AutoMode("1ball");
        test = new AutoMode("test");
        intercept = new AutoMode("Intercept");
        threeBall = new AutoMode("3ball");
        twoBallAlign = new AutoMode("2 ball - UNTESTED");

        //Test auto code
        //test.addSection(new AutoAutoAlign(-1, robot), 0);
        test.addSection(new AutoSetpoint(10, -2, robot), 0);
        //test.addSection(new AutoTurn(-45, robot), 0);

        //Shoots and taxis
        oneBall.addSection(new AutoShoot(3, robot), 0);
        oneBall.addSection(new AutoDrive(2, robot, false), 0);

        //Taxis intakes and shoots both balls
        twoBall.addSection(new AutoDrive(1.5, robot, false), 0);
        twoBall.addSection(new AutoIntake(2.2, robot), 1);
        twoBall.addSection(new AutoDrive(1.5, robot, true), 0);
        twoBall.addSection(new AutoSetpoint(10, robot), 0);
        twoBall.addSection(new AutoShoot(4, robot), 0);

        //Two ball but setpoint align
        twoBallAlign.addSection(new AutoDrive(1.5, robot, false), 0);
        twoBallAlign.addSection(new AutoIntake(2.2, robot), 1);
        twoBallAlign.addSection(new AutoDrive(1.5, robot, true), 0);
        twoBallAlign.addSection(new AutoSetpoint(10, 0, robot), 0);
        twoBallAlign.addSection(new AutoShoot(10, robot), 0);

        //Shoots taxis back, turns, taxis to 3rd ball, aligns to target, shoots both balls
        //threeBall.addSection(new AutoAutoAlign(10, robot), 0);
        threeBall.addSection(new AutoHood(0, robot), 0);
        threeBall.addSection(new AutoWait(4.7), 1);
        threeBall.addSection(new AutoShoot(7150, 0, 3, robot), 0);
        threeBall.addSection(new AutoDrive(1, robot, false), 0);
        threeBall.addSection(new AutoIntake(2, robot), 1);
        threeBall.addSection(new AutoHood(.5, robot), 1);
        //threeBall.addSection(new AutoWait(1), 1);
        threeBall.addSection(new AutoTurn(102, robot), 0);
        threeBall.addSection(new AutoDrive(2.5, robot, false), 0);
        threeBall.addSection(new AutoIntake(3, robot), 1);
        threeBall.addSection(new AutoTurn(-82, robot), 0);
        threeBall.addSection(new AutoSetpoint(10, robot), 0);
        threeBall.addSection(new AutoShoot(8150, .5, 5, robot), 0);


        //Taxis back to hit ball, comes back and shoots ball
        intercept.addSection(new AutoDrive(2, robot, false), 0);
        intercept.addSection(new AutoWait(1), 0);
        intercept.addSection(new AutoDrive(2, robot, true), 0);
        intercept.addSection(new AutoAutoAlign(10, robot), 0);
        intercept.addSection(new AutoShoot(3, robot), 0);

        autoChooser.setDefaultOption(twoBall.getName(), twoBall);
        autoChooser.addOption(oneBall.getName(), oneBall);
        autoChooser.addOption(test.getName(), test);
        autoChooser.addOption(intercept.getName(), intercept);
        autoChooser.addOption(threeBall.getName(), threeBall);

        //SmartDashboard.putData("Auto Mode Selector", autoChooser);

    }

    public AutoMode getAuto() {
        return (AutoMode)autoChooser.getSelected();
        
    }

    public void update() {
        SmartDashboard.putString("Current Auto", autoChooser.getSelected().name);
        //autoChooser.getSelected().update();
        //oneBall.update();
        autoChooser.getSelected().update();
    }

}