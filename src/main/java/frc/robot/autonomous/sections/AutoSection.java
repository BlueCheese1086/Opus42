
package frc.robot.autonomous.sections;

public abstract class AutoSection {

    double length;
    double startTime;
    double endTime;
    boolean started;

    public AutoSection(double length) {
        this.length = length * 1000;
        started = false;
    
    }

    public AutoSection(){
        started = false;
    
    }
    
    public void init() {
        this.startTime = System.currentTimeMillis();
        started = true;
    }

    public abstract void update();

    public abstract void disabled();

    public boolean disableCondition() {
        return length != 0 ? System.currentTimeMillis() - this.startTime >= length : false;
    }

    public boolean hasStarted() {
        return started;
    }

    public double getStartTime(){
        return this.startTime;
    }

    public double getEndTime(){
        return this.endTime;
    }
    
}

