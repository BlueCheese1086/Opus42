package frc.robot.components;

import java.util.ArrayList;
import java.util.Collections;

import frc.robot.sensors.Limelight;

public class ShooterConstants {
    ArrayList<double[]> setPoints;
    Limelight limelight;

    public ShooterConstants(Limelight limelight){
        setPoints = new ArrayList<double[]>();

        setPoints.add(new double[]{1, 0, 7400});
        setPoints.add(new double[]{0, 0, 7600});
        setPoints.add(new double[]{-11, .25, 8550});
        setPoints.add(new double[]{-18, .5, 9300});
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

    public double[] getNearestSetpointRegression(double yAngle){
        //check if the angle is close enough to a setpoint to just align
        boolean useSetpoint = false;
        for(int i=0; i<setPoints.size(); i++){
            double[] currentSetpoint = setPoints.get(0);
            if(Math.abs(currentSetpoint[0] - yAngle) < 2) useSetpoint = true;
        }

        if(useSetpoint) return getNearestSetpoint(yAngle);
        else return getBestFit(yAngle);
    }

    /**
     * Finds values on the equations of best fit
     * @param yAngle the current y-angle of the robot
     * @return a double[] containing 1) velocity and 2) hood angle
     */
    public double[] getBestFit(double yAngle){
        //hard-coded quadratic equations of best fit found using a graphing calculator
        double velocity = (0.6342067527*yAngle*yAngle) + (-86.15121888*yAngle) + 7538.727415;
        double hoodAngle = (0.00077890757 * yAngle*yAngle) + (-0.0134128536 * yAngle) + 0.0067618303;
        double[] oogabooga = {yAngle, velocity, hoodAngle};
        return oogabooga;
    }

    /*
    so this is like a connect-the-dots kinda linear regression, where if you're within the setpoints it'll find the line between the two closest setpoints

    don't you just love basic algebra? logic behind this explained below:

    1) figure out what 2 points ur using
        - sort the list in ascending order by angle
        - get id of setpoint and the one after it

    in order to find the line between two points, you
    - find the slope
    - find the y intercept

    slope (ez) - (y2-y1)/(x2-x1)
    y-intercept -
    y = mx + b
    y - mx = b
    fill in eq with one of the two setpoints. "y" is either hoodangle || v, x is ty. both will do this alg once

    then, use that eq to return values based on yAngle :)
    */

    public double[] getSetpointWeirdRegression(double yAngle){
        //check if we're close enough to an existing setpoint to just use that
        boolean useSetpoint = false;
        for(int i=0; i<setPoints.size(); i++){
            double[] currentSetpoint = setPoints.get(0);
            if(Math.abs(currentSetpoint[0] - yAngle) < 2) useSetpoint = true;
        }
        if(useSetpoint) return getNearestSetpoint(yAngle);

        sortSetpoints(this.setPoints.size());

        //find the second of 2 setpoints
        int nextPointId = -1;
        for(int i=0; i<setPoints.size(); i++){
            double[] current = this.setPoints.get(i);
            if(current[0] > yAngle){
                nextPointId = i;
                break;
            }
        }

        //if we aren't between our measured setpoints, just use the eq
        if(nextPointId < 1) return getNearestSetpointRegression(yAngle);

        //if we are, let's get spicy
        double[] firstPoint = this.setPoints.get(nextPointId - 1);
        double[] secondPoint = this.setPoints.get(nextPointId);

        double velocity, hoodAngle;
        double velocitySlope= (secondPoint[2] - firstPoint[2]) / (secondPoint[0] - firstPoint[0]);
        double velocityIntercept = firstPoint[2] - (velocitySlope * firstPoint[0]);
        velocity = (velocitySlope * yAngle) + velocityIntercept;

        double hoodAngleSlope = (secondPoint[1] - firstPoint[1]) / (secondPoint[0] - firstPoint[0]);
        double hoodAngleIntercept = firstPoint[1] - (velocitySlope * firstPoint[0]);
        hoodAngle = (hoodAngleSlope * yAngle) + hoodAngleIntercept;

        double[] beans = {yAngle, hoodAngle, velocity};
        return beans;
    }

    /**
     * uses bubble sort to sort the setpoints in ascending order (by yAngle)
     */
    public void sortSetpoints(int n){
        if(n==1) return;
        for(int i=0; i<n-1; i++){
            double[] current = this.setPoints.get(i);
            double[] next = this.setPoints.get(i+1);
            if(current[0] > next[0]) Collections.swap(this.setPoints, i, i+1);
        }
        sortSetpoints(n-1);
    }
}