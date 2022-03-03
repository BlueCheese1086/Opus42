package frc.robot.components;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Servo;
import frc.robot.RobotMap;

public class NewShooter {
    
    CANSparkMax frontTop, frontBottom, backTop, backBottom;
    TalonFX shooter1, shooter2;
    Servo hood;

    public NewShooter() {
        frontTop = new CANSparkMax(RobotMap.LAUNCHER_FOUR_ID, MotorType.kBrushless);
        frontBottom = new CANSparkMax(RobotMap.LAUNCHER_THREE_ID, MotorType.kBrushless);
        backTop = new CANSparkMax(RobotMap.LAUNCHER_ONE_ID, MotorType.kBrushless);
        backBottom = new CANSparkMax(RobotMap.LAUNCHER_TWO_ID, MotorType.kBrushless);

        shooter1 = new TalonFX(RobotMap.LAUNCHER_X_ID);
        shooter2 = new TalonFX(RobotMap.LAUNCHER_Y_ID);
        shooter2.setInverted(true);
        shooter2.follow(shooter1);

        hood = new Servo(RobotMap.HOOD_SERVO_ID);
    }


    //S
    public void shoot() {

    }

}
