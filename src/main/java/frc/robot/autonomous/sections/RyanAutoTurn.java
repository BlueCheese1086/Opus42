package frc.robot.autonomous.sections;

import java.util.Arrays;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OogaBoogaPID;
import frc.robot.Robot;

public class RyanAutoTurn extends AutoSection {

    double targetAngle;
    double currentAngle;

    double angle;

    double motorSpeed;

    PIDController pid;

    OogaBoogaPID ooga;

    int ticks = 0;
    double[] pastAverages = new double[10];
    double averageAngle;

    Robot robot;

    boolean reverse;

    public RyanAutoTurn(double angle, Robot robot) {
        super();
        this.robot = robot;

        Arrays.fill(pastAverages, 10);
        
        reverse = angle < 0 ? true : false;

        //pid = new ProfiledPIDController(1, 0, .2);
        pid = new PIDController(1, .003, .1);
        ooga = new OogaBoogaPID(1.5, .15, .2, 90);
        this.angle = angle;
    }

    @Override
    public void init() {
        super.init();
        targetAngle = robot.gyro.getAngle() + angle;
        currentAngle = robot.gyro.getAngle();
    }


    private double averageArray(double[] arr) {
        double sum = 0;
        for (double a : arr) {
            sum += a;
        }
        return sum/arr.length;
    }

    @Override
    public void update() {

        currentAngle = robot.gyro.getAngle();

        /*double speed = pid.calculate(currentAngle, targetAngle);
        if (reverse) {
            speed = speed < -90 ? -1 : speed/100;
        } else {
            speed = speed > 90 ? 1 : speed/100;
        }*/

        double speed = ooga.calculate(currentAngle, targetAngle);

        robot.drivetrain.set(speed, -speed);

        SmartDashboard.putNumber("Turn PID", ooga.calculate(currentAngle, targetAngle));
        SmartDashboard.putNumber("Past PID Average", averageArray(pastAverages));

        pastAverages[ticks%pastAverages.length] = ooga.calculate(currentAngle, targetAngle);
        ticks++;

    }

    @Override
    public void disabled() {
        robot.drivetrain.set(0, 0);
    }

    @Override
    public boolean disableCondition() {
        //return ooga.atSetpoint();
        return Math.abs(averageArray(pastAverages)) < .03;
        //return false;
    }
    
}
