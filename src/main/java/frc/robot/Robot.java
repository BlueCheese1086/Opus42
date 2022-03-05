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
//import frc.robot.autonomous.sections.AutoAutoAlign;
import frc.robot.components.Climb;
import frc.robot.components.CustomServo;
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
//import frc.robot.autonomous.sections.AutoAutoAlign;
//import frc.robot.autonomous.sections.AutoShoot;

import com.ctre.phoenix.music.Orchestra;

public class Robot extends TimedRobot {

  public static Drivetrain drivetrain;
  public Climb climb;
  public Indexer indexer;

  public CustomServo servo;

  public Intake intake;
  public Shooter shooter;
  public Limelight limelight;
  public Hood hood;
  public AutoManager m;
  Control c;
  ArrayList<Interface> interfaces;
  Orchestra o;

  SendableChooser<Primary> primaryDrivers;
  SendableChooser<Secondary> secondaryDrivers;

  public SendableChooser<String> distanceVelo;

  public SendableChooser<AutoMode> autoMode;

  public Drivetrain getDrivetrain(){
      return drivetrain;
  }

  public Shooter getShooter(){
    return shooter;
  }

  public Indexer getIndexer(){
    return indexer;
  }

  // Robot Initiate
  @Override
  public void robotInit() {

    c = new Control();

    c.setPrimary(Primary.RyanBox);
    c.setSecondary(Secondary.Toshi);

    //servo = new CustomServo(RobotMap.HOOD_SERVO_ID);

    limelight = new Limelight();
    hood = new Hood(RobotMap.HOOD_SERVO_ID);
    drivetrain = new Drivetrain(RobotMap.FRONT_LEFT_ID, RobotMap.FRONT_RIGHT_ID, RobotMap.BACK_LEFT_ID, RobotMap.BACK_RIGHT_ID, limelight);
    climb = new Climb(RobotMap.CLIMB_LEFT_ID, RobotMap.CLIMB_RIGHT_ID, RobotMap.CLIMB_SOLENOID_ID);
    indexer = new Indexer(RobotMap.INDEXER_LEFT_ID, RobotMap.INDEXER_RIGHT_ID);
    intake = new Intake(RobotMap.INTAKE_MOTOR_ID, RobotMap.INTAKE_SOLENOID_ID);
    shooter = new Shooter(this, RobotMap.LAUNCHER_X_ID, RobotMap.LAUNCHER_Y_ID, RobotMap.LAUNCHER_ONE_ID, RobotMap.LAUNCHER_TWO_ID, RobotMap.LAUNCHER_THREE_ID, RobotMap.LAUNCHER_FOUR_ID, limelight, hood, indexer, drivetrain);

    interfaces = new ArrayList<>();
    interfaces.addAll(Arrays.asList(new DrivetrainInterface(this, c), new ClimbInterface(this, c), new IndexerInterface(this, c), new IntakeInterface(this, c), new ShooterInterface(this, c)));


    distanceVelo = new SendableChooser<>();

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

    o.loadMusic("somethingjustlikethis.chrp");

  }

  @Override
  public void robotPeriodic() {
    /********************
     * TELEMETRY WOOOOO *
     ********************/

    // Hood
    SmartDashboard.putNumber("Hood Raw", Double.valueOf(hood.getRaw()));

    // Battery Voltage
    SmartDashboard.putNumber("Battery Voltage", RobotController.getBatteryVoltage());

    // Driver Selection
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

    SmartDashboard.putNumber("Shooter 1 Temp", shooter.x.getTemperature());
    SmartDashboard.putNumber("Shooter 2 Temp", shooter.y.getTemperature());

    // Shooter
    SmartDashboard.putBoolean("Shooter Button", c.getLauncherShoot());
    SmartDashboard.putNumber("Shooter 1 Velo", shooter.x.getSelectedSensorVelocity());

    // Shooter Currents
    SmartDashboard.putNumber("Shooter 1 Current", shooter.x.getStatorCurrent());
    SmartDashboard.putNumber("Shooter 2 Current", shooter.y.getStatorCurrent());

    // Solenoids
    SmartDashboard.putBoolean("Intake Solenoid", intake.getPos());
    SmartDashboard.putBoolean("Climb Solenoid", climb.getLock());

    // Input
    SmartDashboard.putNumber("Shooter Velocity", SmartDashboard.getNumber("Shooter Velocity", 0));
    SmartDashboard.getNumber("Shooter Velocity", 0);

    // Currents
    SmartDashboard.putNumber("Front Right Current", drivetrain.getFrontRight().getOutputCurrent());
    SmartDashboard.putNumber("Front Left Current", drivetrain.getFrontLeft().getOutputCurrent());

    // SPEEEEED
    SmartDashboard.putNumber("Speed", (drivetrain.getFrontRight().get() + drivetrain.getFrontLeft().get())/2.0);

    // Limelight
    SmartDashboard.putNumber("Ground Number", limelight.getGroundDistance(Constants.UPPER_HUB_HEIGHT - Constants.CAMERA_HEIGHT + Constants.CARGO_DIAMETER));
    SmartDashboard.putNumber("Y Angle", limelight.getYAngle());
    SmartDashboard.putNumber("X Angle", limelight.getXAngle());

    // Distance -> Velo Data
    SmartDashboard.putData(distanceVelo);

  }

  @Override
  public void autonomousInit() {
    m.getAuto();

  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    m.update();
    //o.play();
  }

  //AutoAutoAlign a;

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {

    //a = new AutoAutoAlign(3);

    c.setPrimary(primaryDrivers.getSelected());
    c.setSecondary(secondaryDrivers.getSelected());

  }
  /** This function is called periodically during operator control. */

  //AutoAutoAlign a = new AutoAutoAlign(3);

  @Override
  public void teleopPeriodic() {
    interfaces.forEach(Interface::tick);

    /*if (c.primary.getPOV() == 0) {
      hood.setRaw(hood.getRaw() + 1);
    } else if (c.primary.getPOV() == 180) {
      hood.setRaw(hood.getRaw() - 1);
    }*/

    /*if (c.primary.getPOV() == 0) {
      servo.setSpeed(1);
    } else if (c.primary.getPOV() == 180) {
      servo.setSpeed(-1);
    } else {
      servo.setSpeed(0);
    }*/
    
    
  }

  long timeOff;

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
    timeOff = 0;
    timeOff = System.currentTimeMillis();
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
    SmartDashboard.putNumber("Time Disabled", (System.currentTimeMillis() - timeOff)/1000.0);
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

  /**public static void testShoot(){
    shooter.shoot();
  }*/
}