package frc.robot;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.Arrays;

import com.ctre.phoenix.music.Orchestra;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Control.Primary;
import frc.robot.Control.Secondary;
import frc.robot.autonomous.AutoManager;
import frc.robot.autonomous.AutoMode;
import frc.robot.autonomous.Paths;
import frc.robot.components.Climb;
import frc.robot.components.Drivetrain;
import frc.robot.components.Lights;
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
import frc.robot.controlInterfaces.LightsInterface.ControlMode;
import frc.robot.controlInterfaces.LightsInterface;
import frc.robot.sensors.Limelight;

public class Robot extends TimedRobot {

  public Drivetrain drivetrain;
  public Climb climb;
  public Indexer indexer;
  public Intake intake;
  public Shooter shooter;
  public Limelight limelight;
  public Hood hood;
  public AutoManager m;
  public Control c;
  public ArrayList<Interface> interfaces;
  public Orchestra o;
  public Lights lights;
  public LightsInterface lightsInter;
  public AHRS gyro;
  public Paths trajectories;

  public SendableChooser<Primary> primaryDrivers;
  public SendableChooser<Secondary> secondaryDrivers;
  public SendableChooser<String> distanceVelo;
  public SendableChooser<AutoMode> autoMode;

  // Robot Initiate
  @Override
  public void robotInit() {

    // Init control
    // Setting default drivers
    c = new Control();
    c.setPrimary(Primary.RyanBox);
    c.setSecondary(Secondary.Toshi);

    //LIGHTS
    lights = new Lights(300);
    lightsInter = new LightsInterface(this, c);
    lightsInter.setControlMode(LightsInterface.ControlMode.Rainbow);
    lights.setAlliance();

    // Initializing components
    limelight = new Limelight();
    gyro = new AHRS(); 
    hood = new Hood(RobotMap.HOOD_SERVO_ID);
    drivetrain = new Drivetrain(RobotMap.FRONT_LEFT_ID, RobotMap.FRONT_RIGHT_ID, RobotMap.BACK_LEFT_ID, RobotMap.BACK_RIGHT_ID, limelight, gyro, this);
    climb = new Climb(RobotMap.CLIMB_LEFT_ID, RobotMap.CLIMB_RIGHT_ID, RobotMap.CLIMB_SOLENOID_ID);
    indexer = new Indexer(RobotMap.INDEXER_LEFT_ID, RobotMap.INDEXER_RIGHT_ID);
    intake = new Intake(RobotMap.INTAKE_MOTOR_ID, RobotMap.INTAKE_SOLENOID_ID);
    shooter = new Shooter(this, RobotMap.LAUNCHER_X_ID, RobotMap.LAUNCHER_Y_ID, RobotMap.LAUNCHER_ONE_ID, RobotMap.LAUNCHER_TWO_ID, RobotMap.LAUNCHER_THREE_ID, RobotMap.LAUNCHER_FOUR_ID, limelight, hood, indexer, drivetrain);

    // Initializing interfaces
    interfaces = new ArrayList<>();
    interfaces.addAll(Arrays.asList(new DrivetrainInterface(this, c), new ClimbInterface(this, c), new IndexerInterface(this, c), new IntakeInterface(this, c), new ShooterInterface(this, c)));

    // Sendable Choosers
    distanceVelo = new SendableChooser<>();
    primaryDrivers = new SendableChooser<>();
    secondaryDrivers = new SendableChooser<>();
    autoMode = new SendableChooser<>();

    // Auto Manager
    m = new AutoManager(this);

    // Driver selection
    for (Primary p : Primary.values()) {
      primaryDrivers.addOption("Primary - " + p.name(), p);
    }
    for (Secondary s : Secondary.values()) {
      secondaryDrivers.addOption("Secondary - " + s.name(), s);
    }
    primaryDrivers.setDefaultOption("Primary - " + Primary.values()[0].name(), Primary.values()[0]);
    secondaryDrivers.setDefaultOption("Secondary - " + Secondary.values()[0].name(), Secondary.values()[0]);

    // Singing Falcon FX
    //toshi moved this lol

    gyro.calibrate();

    //CameraServer.startAutomaticCapture();

  }

  @Override
  public void robotPeriodic() {
    /********************
     * TELEMETRY WOOOOO *
     ********************/

    //lights.rainbow();
    lightsInter.tick();
    
    // Driver Selection
    SmartDashboard.putData("Primary Driver", primaryDrivers);
    SmartDashboard.putData("Secondary Driver", secondaryDrivers);

    // Auto Selection
    SmartDashboard.putData("Auto Mode Selector", autoMode);

    //Override
    SmartDashboard.putBoolean("Tower Override", shooter.getOverride());
    SmartDashboard.putBoolean("Shooter Override", shooter.overrideShooter);

    // Brake Mode
    SmartDashboard.putBoolean("Brake Mode", drivetrain.getMode());

    // Motor Temps
    SmartDashboard.putNumber("Front Left Temp", drivetrain.getTemps()[0]);
    SmartDashboard.putNumber("Back Left Temp", drivetrain.getTemps()[1]);
    SmartDashboard.putNumber("Front Right Temp", drivetrain.getTemps()[2]);
    SmartDashboard.putNumber("Back Right Temp", drivetrain.getTemps()[3]);

    SmartDashboard.putNumber("Shooter 1 Temp", shooter.x.getTemperature());
    SmartDashboard.putNumber("Shooter 2 Temp", shooter.y.getTemperature());

    //Shooter Velocity
    SmartDashboard.putNumber("Shooter Velocity", shooter.getMotorVelocity());

    // Input
    SmartDashboard.putNumber("Target Velocity", SmartDashboard.getNumber("Target Velocity", 0));
    SmartDashboard.getNumber("Target Velocity", 0);

    //Limelight
    SmartDashboard.putNumber("Y Angle", limelight.getYAngle());
    SmartDashboard.putNumber("X Angle", limelight.getXAngle());
  }

  @Override
  public void autonomousInit() {
    m = null;
    m = new AutoManager(this);
    //m.getAuto();
    hood.setMax();
    hood.setMin();
    drivetrain.setMode(IdleMode.kBrake);
    lightsInter.setControlMode(ControlMode.Alliance);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    m.update();
    //o.play();
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {

    c.setPrimary(primaryDrivers.getSelected());
    c.setSecondary(secondaryDrivers.getSelected());

    drivetrain.setMode(IdleMode.kBrake);

  }

  @Override
  public void teleopPeriodic() {
    // Ticks each interface
    interfaces.forEach(Interface::tick);
    if (DriverStation.getMatchTime() <= 15.0) {
      lights.setLights(255, 255, 0);
      lightsInter.setControlMode(ControlMode.StaticColor);
    }
    /*if(c.primary.getAButton()){
      drivetrain.autoAlign();
    }
    else{
      drivetrain.set(0, 0);
    }*/
  }

  long timeOff;
  @Override
  public void disabledInit() {
    drivetrain.setMode(IdleMode.kCoast);
    // Sets up counter for disabled time
    timeOff = 0;
    timeOff = System.currentTimeMillis();
    lightsInter.setControlMode(ControlMode.Alliance);
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
    //lights.setLights(255, 0, 0);
    // Updates time disabled counter
    SmartDashboard.putNumber("Time Disabled", (System.currentTimeMillis() - timeOff)/1000.0);
  }

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
    //initiate the sus
    o = new Orchestra();
    o.addInstrument(shooter.x);
    o.addInstrument(shooter.y);
    o.loadMusic("sus.chrp");
  }
  

  /** This function is called periodically during test mode. */
  int lightId = 0;
  @Override
  public void testPeriodic() {
    /*SmartDashboard.putNumber("Light ID", lightId);
    if (c.primary.getRightBumperPressed()) {
      lightId++;
    }
    if (c.primary.getLeftBumperPressed()) {
      lightId--;
    }
    lights.setLights(0, 0, 0);
    lights.setLightsTo(0, lightId, 0, 0, 255);*/
    o.play();
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