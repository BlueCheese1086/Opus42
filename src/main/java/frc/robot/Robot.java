// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

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

  public static Drivetrain drivetrain;
  public Climb climb;
  public static Indexer indexer;
  public static Intake intake;
  public Shooter shooter;
  public Limelight limelight;
  public Hood hood;
  AutoManager m;
  Control c;
  ArrayList<Interface> interfaces;

  SendableChooser<Primary> primaryDrivers;
  SendableChooser<Secondary> secondaryDrivers;

  SendableChooser<AutoMode> autoMode;

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {

    c = new Control();

    c.setPrimary(Primary.RyanBox);

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

    for (Primary p : Primary.values()) {
      primaryDrivers.addOption("Primary - " + p.name(), p);
    }
    for (Secondary s : Secondary.values()) {
      secondaryDrivers.addOption("Secondary - " + s.name(), s);
    }
    primaryDrivers.setDefaultOption("Primary - " + Primary.values()[0].name(), Primary.values()[0]);
    secondaryDrivers.setDefaultOption("Secondary - " + Secondary.values()[0].name(), Secondary.values()[0]);
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

    //Brake Mode
    SmartDashboard.putBoolean("Brake Mode", drivetrain.getMode());
  }

  @Override
  public void autonomousInit() {
    m = new AutoManager();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    m.update();
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
}
