package frc.robot.components;

import java.util.ArrayList;

import frc.robot.sensors.Limelight;

public class ShooterConstants {
    ArrayList<double[]> setPoints;
    Limelight limelight;

    public ShooterConstants(Limelight limelight){
        setPoints = new ArrayList<double[]>();

        /*setPoints.add(new double[]{1, 0, 7400});
        setPoints.add(new double[]{0, 0, 7600});
        setPoints.add(new double[]{-18, .5, 9300});
        setPoints.add(new double[]{-11, .25, 8550});*/
        setPoints.add(new double[]{0, 0, 0});
        //this.setPoint(limelight.getYAngle(), 0, 8500);

        this.limelight = limelight;
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

    /**
     * 
     * @param yAngle current yangle
     * @return the id of the setpoint in the arraylist
     */
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
  
         /**
          * ID 0 - target yAngle
          ID 1 - target hoodAngle
          ID 2 - target velocity
          * @param yAngle current yAngle
          * @return the double[] of the nearest setpoint
        */
    public double[] getNearestSetpoint(double yAngle) {
        double difference = Double.MAX_VALUE;
        int setPointID = 0;
        if(!(limelight.getXAngle() == 0.0 && yAngle == 0)){
            for(int i=0; i<setPoints.size(); i++){
                double[] entry = setPoints.get(i);
                if(Math.abs(entry[0] - yAngle) < difference){
                   difference = Math.abs(entry[0] - yAngle);
                    setPointID = i;
                }
            }
        }

        return setPoints.get(setPointID);
    }

    /**
     * get the. get the point. the setted point
     * @param ID the ID in the arraylist
     * @return THE SETTED POINTd
     */
    public double[] getSetpoint(int ID){
        return setPoints.get(ID);
    }
}
