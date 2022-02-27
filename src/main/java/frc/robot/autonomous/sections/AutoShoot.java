package frc.robot.autonomous.sections;

import frc.robot.components.Shooter;

public class AutoShoot extends AutoSection {
    
    Shooter shooter;

    public AutoShoot(double length){
        super(length);
        
    }

    @Override
    public void update() {
        shooter.shoot();
    }

    @Override
    public void disabled() {

    }
}
