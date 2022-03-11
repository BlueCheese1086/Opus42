package frc.robot.components;

import java.util.ArrayList;

import frc.robot.sensors.Limelight;

public class ShooterConstants {
    ArrayList<double[]> setPoints;
    Limelight limelight;

    public ShooterConstants(Limelight limelight){
        setPoints = new ArrayList<double[]>();
        //this.setPoint(-1.2, 0, 8500);
        //this.setPoint(-1, 0.9, 13000);
        //this.setPoint(-12, 1.0, 14000);
        setPoints.add(new double[]{0, 0, 8500});
        setPoints.add(new double[]{-16.7, 1, 11111});
        //this.setPoint(limelight.getYAngle(), 0, 8500);
    }

    public ArrayList<double[]> getSetPoints(){
        return setPoints;
    }
    
    /**
     * //TODO: determine what units these should be in
     * @param yAngle limelight.getYAngle() 
     * @param hoodAngle the angle the hood servo is set to(?????)
     * @param velocity the velocity we learn from testing (encod7---]n///////////n/=*9+er units?)
     */
    public void setPoint(double yAngle, double hoodAngle, double velocity){
        double[] entry = {yAngle, hoodAngle, velocity};
        setPoints.add(entry);
        //setPoints.set(0, entry);
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

    public double[] getNearestSetpoint(double yAngle) {
        double difference = Double.MAX_VALUE;
        int setPointID = 0;
        for(int i=0; i<setPoints.size(); i++){
            double[] entry = setPoints.get(i);
            if(Math.abs(entry[0] - yAngle) < difference){
                difference = Math.abs(entry[0] - yAngle);
                setPointID = i;
            }
        }

        return setPoints.get(setPointID);
    }

    public double[] getSetpoint(int ID){
        return setPoints.get(ID);
    }
}
