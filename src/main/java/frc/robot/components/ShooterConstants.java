package frc.robot.components;

import java.util.ArrayList;

public class ShooterConstants {
    ArrayList<double[]> setPoints;

    public ShooterConstants(){
        setPoints = new ArrayList<double[]>();
        this.setPoint(7, .75, 8400);
        this.setPoint(-12, 1.0, 10000);
    }

    public ArrayList<double[]> getSetPoints(){
        return setPoints;
    }
    /**
     * //TODO: determine what units these should be in
     * @param yAngle limelight.getYAngle() 
     * @param hoodAngle the angle the hood servo is set to(?????)
     * @param velocity the velocity we learn from testing (encoder units?)
     */
    public void setPoint(double yAngle, double hoodAngle, double velocity){
        double[] entry = {yAngle, hoodAngle, velocity};
        setPoints.add(entry);
    }

    public int getNearestSetpointID(double yAngle){
        double difference = Double.MAX_VALUE;
        int setPointID = 0;
        for(int i=0; i<setPoints.size(); i++){
            double[] entry = setPoints.get(i);
            if(Math.abs(entry[0] - yAngle) < difference){
                difference = Math.abs(entry[0] - yAngle);
                setPointID = i;
            }
        }

        return setPointID;
    }

    public double[] getSetpoint(int ID){
        return setPoints.get(ID);
    }
}
