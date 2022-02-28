/*
░░███╗░░░█████╗░░█████╗░░█████╗░  ██████╗░██╗░░░░░███████╗░██╗░░░░░░░██╗  ░█████╗░██╗░░██╗███████╗███████╗
░████║░░██╔══██╗██╔═══╝░██╔══██╗  ██╔══██╗██║░░░░░██╔════╝░██║░░██╗░░██║  ██╔══██╗██║░░██║██╔════╝╚════██║
██╔██║░░██║░░██║██████╗░╚█████╔╝  ██████╦╝██║░░░░░█████╗░░░╚██╗████╗██╔╝  ██║░░╚═╝███████║█████╗░░░░███╔═╝
╚═╝██║░░██║░░██║██╔══██╗██╔══██╗  ██╔══██╗██║░░░░░██╔══╝░░░░████╔═████║░  ██║░░██╗██╔══██║██╔══╝░░██╔══╝░░
███████╗╚█████╔╝╚█████╔╝╚█████╔╝  ██████╦╝███████╗███████╗░░╚██╔╝░╚██╔╝░  ╚█████╔╝██║░░██║███████╗███████╗
╚══════╝░╚════╝░░╚════╝░░╚════╝░  ╚═════╝░╚══════╝╚══════╝░░░╚═╝░░░╚═╝░░  ░╚════╝░╚═╝░░╚═╝╚══════╝╚══════╝

█▄─██─▄█▄─█▀▀▀█─▄█▄─██─▄███▄─▀█▀─▄█▄─█─▄███▄─▀█▄─▄█▄─██─▄█─▄─▄─█░▄▄░▄█
██─██─███─█─█─█─███─██─█████─█▄█─███▄─▄█████─█▄▀─███─██─████─████▀▄█▀█
▀▀▄▄▄▄▀▀▀▄▄▄▀▄▄▄▀▀▀▄▄▄▄▀▀▀▀▄▄▄▀▄▄▄▀▀▄▄▄▀▀▀▀▄▄▄▀▀▄▄▀▀▄▄▄▄▀▀▀▄▄▄▀▀▄▄▄▄▄▀


*/

package frc.robot;

import java.util.ArrayList;
import java.util.Arrays;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Control.Primary;
import frc.robot.Control.Secondary;
import frc.robot.autonomous.AutoManager;
import frc.robot.autonomous.AutoMode;
import frc.robot.autonomous.sections.AutoSection;
import frc.robot.components.Climb;
import frc.robot.components.Drivetrain;
import frc.robot.components.Hood;
import frc.robot.components.Indexer;
import frc.robot.components.Intake;
import frc.robot.components.Shooter;
import frc.robot.controlInterfaces.ClimbInterface;
import frc.robot.controlInterfaces.DrivetrainInterface;
import frc.robot.controlInterfaces.IndexerInterface;
import frc.robot.controlInterfaces.IntakeInterface;
import frc.robot.controlInterfaces.Interface;
import frc.robot.controlInterfaces.ShooterInterface;
import frc.robot.sensors.Limelight;

import com.ctre.phoenix.music.Orchestra;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  public Drivetrain drivetrain;
  public Climb climb;
  public Indexer indexer;
  public Intake intake;
  public static Shooter shooter;
  public Limelight limelight;
  public Hood hood;
  public AutoManager m;
  Control c;
  ArrayList<Interface> interfaces;
  //ArrayList<AutoSection> autoSections;
  Orchestra o;

  SendableChooser<Primary> primaryDrivers;
  SendableChooser<Secondary> secondaryDrivers;

  public SendableChooser<AutoMode> autoMode;

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {

    c = new Control();

    c.setPrimary(Primary.RyanBox);
    c.setSecondary(Secondary.Toshi);

    limelight = new Limelight();
    hood = new Hood(RobotMap.HOOD_SERVO_ID);
    drivetrain = new Drivetrain(RobotMap.FRONT_LEFT_ID, RobotMap.FRONT_RIGHT_ID, RobotMap.BACK_LEFT_ID,
        RobotMap.BACK_RIGHT_ID, limelight);
    climb = new Climb(RobotMap.CLIMB_LEFT_ID, RobotMap.CLIMB_RIGHT_ID, RobotMap.CLIMB_SOLENOID_ID);
    indexer = new Indexer(RobotMap.INDEXER_LEFT_ID, RobotMap.INDEXER_RIGHT_ID);
    intake = new Intake(RobotMap.INTAKE_MOTOR_ID, RobotMap.INTAKE_SOLENOID_ID);
    shooter = new Shooter(RobotMap.LAUNCHER_X_ID, RobotMap.LAUNCHER_Y_ID, RobotMap.LAUNCHER_ONE_ID,
        RobotMap.LAUNCHER_TWO_ID, RobotMap.LAUNCHER_THREE_ID, RobotMap.LAUNCHER_FOUR_ID, limelight, hood, indexer,
        drivetrain);

    interfaces = new ArrayList<>();
    interfaces.addAll(Arrays.asList(new DrivetrainInterface(this, c), new ClimbInterface(this, c), new IndexerInterface(this, c), new IntakeInterface(this, c), new ShooterInterface(this, c)));

    primaryDrivers = new SendableChooser<>();
    secondaryDrivers = new SendableChooser<>();

    autoMode = new SendableChooser<>();
    m = new AutoManager(this);

    for (Primary p : Primary.values()) {
      primaryDrivers.addOption("Primary - " + p.name(), p);
    }
    for (Secondary s : Secondary.values()) {
      secondaryDrivers.addOption("Secondary - " + s.name(), s);
    }
    primaryDrivers.setDefaultOption("Primary - " + Primary.values()[0].name(), Primary.values()[0]);
    secondaryDrivers.setDefaultOption("Secondary - " + Secondary.values()[0].name(), Secondary.values()[0]);

    o = new Orchestra();

    o.addInstrument(shooter.x);
    o.addInstrument(shooter.y);

    o.loadMusic("toto.chrp");

  }

  public static Shooter getShooter() {
    return shooter;
  }

  @Override
  public void robotPeriodic() {
    /********************
     * TELEMETRY WOOOOO *
     ********************/

    // Battery Voltage
    SmartDashboard.putNumber("Battery Voltage", RobotController.getBatteryVoltage());

    // Driver Selection Shit
    SmartDashboard.putData("Primary Driver", primaryDrivers);
    SmartDashboard.putData("Secondary Driver", secondaryDrivers);

    // Auto Selection
    SmartDashboard.putData("Auto Mode Selector", autoMode);

    // Brake Mode
    SmartDashboard.putBoolean("Brake Mode", drivetrain.getMode());

    // Motor Temps
    SmartDashboard.putNumber("Front Left Temp", drivetrain.getTemps()[0]);
    SmartDashboard.putNumber("Back Left Temp", drivetrain.getTemps()[1]);
    SmartDashboard.putNumber("Front Right Temp", drivetrain.getTemps()[2]);
    SmartDashboard.putNumber("Back Right Temp", drivetrain.getTemps()[3]);

    // Shoota
    SmartDashboard.putBoolean("Shooter Button", c.getLauncherShoot());

    //Solenoids
    SmartDashboard.putBoolean("Intake Solenoid", intake.getPos());
    SmartDashboard.putBoolean("Climb Solenoid", climb.getLock());

    // Input
    SmartDashboard.putNumber("Shooter Velocity", SmartDashboard.getNumber("Shooter Velocity", 0));
    SmartDashboard.getNumber("Shooter Velocity", 0);

    // Falcons :)
    SmartDashboard.putNumber("Shooter 1 Velo", shooter.x.getSelectedSensorVelocity());
  }

  @Override
  public void autonomousInit() {
    m.getAuto();

  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    m.update();
    
    //shooter.shoot();
    //o.play();

  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {

    c.setPrimary(primaryDrivers.getSelected());
    c.setSecondary(secondaryDrivers.getSelected());

  }
  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    interfaces.forEach(Interface::tick);

    //shooter.runBelts(0.5);
    /*if (c.primary.getAButton()) {
      shooter.one.set(.2);
    } else {
      shooter.one.set(0);
    }*/
    //shooter.shoot();
    /*if (c.secondary.getYButton()) {
      hood.setMax();
    }
    if (c.secondary.getAButton()) {
      hood.setMin();
    }
    if (c.getLauncherShoot()){
      //shooter.shoot();
      shooter1.set(ControlMode.PercentOutput, 1);
      shooter2.set(ControlMode.PercentOutput, 1);
    } else {
      shooter1.set(ControlMode.PercentOutput, 0);
      shooter2.set(ControlMode.PercentOutput, 0);
    }*/
    //interfaces.get(0).tick();
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
  }

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {
  }

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {
  }

  public static void testShoot(){
    shooter.shoot();
  }
}
