package frc.robot.autonomous;

import java.io.IOException;
import java.util.Hashtable;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.Filesystem;

public class Paths {

    Hashtable<String, Trajectory> trajectories;

    public Paths(){
        trajectories = new Hashtable<>();
    
    }

    public void init(){
        try{
            trajectories.put("tarmac-to-rightBb", TrajectoryUtil.fromPathweaverJson(Filesystem.getDeployDirectory().toPath().resolve("paths/output/tarmac-to-rightBb.wpilib.json")));
            trajectories.put("rightBb-to-tarmac", TrajectoryUtil.fromPathweaverJson(Filesystem.getDeployDirectory().toPath().resolve("paths/output/rightBb-to-tarmac.wpilib.json")));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public Hashtable<String, Trajectory> getTrajectories(){
        return trajectories;
    }
}
