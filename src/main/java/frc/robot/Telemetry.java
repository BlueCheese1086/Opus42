package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Telemetry {
    NetworkTableInstance a;
    NetworkTable table;

    public Telemetry(String name) {
        a = NetworkTableInstance.getDefault();
        table = a.getTable(name);
    }

    public void addEntry(String name, double value) {
        table.getEntry(name);
    }

}
