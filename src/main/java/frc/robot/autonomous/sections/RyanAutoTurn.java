package frc.robot.autonomous.sections;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class RyanAutoTurn extends AutoSection {

    double targetAngle;
    double currentAngle;

    double angle;

    double motorSpeed;

    PIDController pid;

    int ticks = 0;
    double[] pastAverages = {10, 10, 10, 10, 10};
    double averageAngle;

    Robot robot;

    boolean reverse;

    public RyanAutoTurn(double angle, Robot robot) {
        super();
        this.robot = robot;
        
        reverse = angle < 0 ? true : false;

        //pid = new ProfiledPIDController(1, 0, .2);
        pid = new PIDController(1, .003, .1);
        this.angle = angle;
    }

    @Override
    public void init() {
        super.init();
        targetAngle = robot.gyro.getAngle() + angle;
        currentAngle = robot.gyro.getAngle();
    }


    public double averageArray(double[] arr) {
        double sum = 0;
        for (double a : arr) {
            sum += a;
        }
        return sum/arr.length;
    }

    @Override
    public void update() {

        

        currentAngle = robot.gyro.getAngle();

        double speed = pid.calculate(currentAngle, targetAngle);
        if (reverse) {
            speed = speed < -90 ? -1 : speed/100;
        } else {
            speed = speed > 90 ? 1 : speed/100;
        }

        robot.drivetrain.set(speed, -speed);

        SmartDashboard.putNumber("Turn PID", pid.calculate(currentAngle, targetAngle));

        pastAverages[ticks%pastAverages.length] = pid.calculate(currentAngle, targetAngle);
        ticks++;

    }

    @Override
    public void disabled() {
        robot.drivetrain.set(0, 0);
    }

    @Override
    public boolean disableCondition() {
        return Math.abs(averageArray(pastAverages)) < 2.5;
        //return false;
    }
    
}
