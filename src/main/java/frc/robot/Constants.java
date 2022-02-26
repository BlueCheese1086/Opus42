package frc.robot;

public class Constants {
    public static final double LAUNCHER_DEFAULT_VELOCITY = 9.0; //meters/second


    public static final double CAMERA_HEIGHT = 0.762; //meters, TODO: determine camera height
    public static final double CAMERA_ANGLE = 45; //degrees
    public static final double LAUNCHER_WHEEL_DIAMETER = 0.1016; //m e t er s??
    public static final double LAUNCHER_WHEEL_CIRCUMFERENCE = 2 * Math.PI * (LAUNCHER_WHEEL_DIAMETER / 2);
    public static final double LAUNCHER_ENCODER_UNITS_PER_ROTATION = 2048;
    public static final double LAUNCHER_MAX_VELOCITY = 6380; //rpm

    public static final double LAUNCHER_KP = 0.05;
    public static final double LAUNCHER_KI = 0.0;
    public static final double LAUNCHER_KD = 0.0;
    public static final double LAUNCHER_KF = 1023 / LAUNCHER_MAX_VELOCITY;

    public static final double UPPER_HUB_HEIGHT = 2.64; //meters
    public static final double GRAVITY = 9.8; //meters/second
    public static final double CARGO_DIAMETER = 0.24; //meters


}
