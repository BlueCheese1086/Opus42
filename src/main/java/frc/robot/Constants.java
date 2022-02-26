package frc.robot;



public class Constants {
    public static final double LAUNCHER_DEFAULT_VELOCITY = 9.0; //meters/second
   

    public static final double CAMERA_HEIGHT = 0.0; //meters, TODO: determine camera height
    public static final double CAMERA_ANGLE = 45; //degrees

    public static final double UPPER_HUB_HEIGHT = 2.64; //meters
    public static final double GRAVITY = 9.8; //meters/second
    public static final double CARGO_DIAMETER = 0.24;

    public static final double MP_DRIVE_FF = 1.0 / 5676;
    public static final double MP_DRIVE_KP = 1.0 / 1000;
    public static final double MP_DRIVE_KI = 0;
    public static final double MP_DRIVE_KD = 0;

    public static double DRIVETRAIN_POSITION_SCALE = (8.98) / (CARGO_DIAMETER * Math.PI); // m -> rotation
    public static double WHEEL_TO_WHEEL_RADIUS = Math.sqrt(Math.pow(74.72) + Math.pow(52.75)); // cm

    public static double TURN_ERROR = 0.005 * DRIVETRAIN_POSITION_SCALE; // rotations
    public static double DRIVE_ERROR = 0.003 * DRIVETRAIN_POSITION_SCALE; // rotaions
}
