package frc.robot.controlInterfaces;

import frc.robot.Control;
import frc.robot.Robot;
import frc.robot.components.Indexer;

public class IndexerInterface extends Interface{
    Indexer indexer;
    
    public IndexerInterface(Robot robot, Control c) {
        super(robot, c);
        indexer = robot.indexer;
        //TODO Auto-generated constructor stub
    }

    /**
     * what the indexer will do every tick
     */
    public void tick(){
        //Commented out because I'm not sure what's going on here
        /*if (c.getButton() && c.getButton()) {
            indexer.stop();
        }*/
        if (c.getIndexerIn()) {
            indexer.in();
        }
        else if (c.getIndexerOut()) {
            indexer.out();
        }
        else {
            indexer.stop();
        }
    }
    
}
