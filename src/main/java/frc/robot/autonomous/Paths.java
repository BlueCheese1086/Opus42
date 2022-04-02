package frc.robot.autonomous;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Hashtable;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.Filesystem;

public class Paths {

    Hashtable<String, Trajectory> trajectories;
    Path p1, p2;
    Trajectory t1, t2;


    public Paths(){
        trajectories = new Hashtable<String, Trajectory>();
    
    }

    public void init(){
        /*System.out.println("\nPaths INIT started...");
        System.out.println("making paths...");
        p1 = Filesystem.getDeployDirectory().toPath().resolve("paths/tarmac-to-rightBb.wpilib.json");
        p2 = Filesystem.getDeployDirectory().toPath().resolve("paths/rightBb-to-tarmac.wpilib.json");
        try{
            System.out.println("making t1/t2...");
            t1 = TrajectoryUtil.fromPathweaverJson(p1);
            t2 = TrajectoryUtil.fromPathweaverJson(p2);
        } catch (IOException e) {
            System.out.println("t1/t2 build failed");
            e.printStackTrace();
        }*/

        try{
            System.out.println("building hashTable...");
            trajectories.put("tarmac-to-rightBb", TrajectoryUtil.fromPathweaverJson(Filesystem.getDeployDirectory().toPath().resolve("paths/tarmac-to-rightBb.wpilib.json")));
            //System.out.println("tarmac-to-rightBb :D");
            trajectories.put("rightBb-to-tarmac", TrajectoryUtil.fromPathweaverJson(Filesystem.getDeployDirectory().toPath().resolve("paths/rightBb-to-tarmac.wpilib.json")));
            trajectories.put("test", TrajectoryUtil.fromPathweaverJson(Filesystem.getDeployDirectory().toPath().resolve("paths/test.wpilib.json")));
        } catch (IOException e){
            System.out.println("hashTable build failed");
            e.printStackTrace();
        }
    }

    public Hashtable<String, Trajectory> getTrajectories(){
        return trajectories;
    }

    public void test(){
        System.out.println("test test test");
    }

    /*
    public static void main(String[] args){
        System.out.println("\nmain method running...\n");

        //new code
        Path path = Filesystem.getDeployDirectory().toPath().resolve("paths/tarmac-to-rightBb.wpilib.json");
        System.out.println("path made\n");

        Paths p = new Paths();
        System.out.println("p created");
        p.init();
        System.out.println("p init");
        System.out.println(p.getTrajectories().get("tarmac-to-rightBb"));
    }
    */
    
}