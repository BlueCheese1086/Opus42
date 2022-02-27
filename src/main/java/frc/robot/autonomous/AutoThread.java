package frc.robot.autonomous;

import java.util.ArrayList;

import frc.robot.autonomous.sections.AutoSection;

public class AutoThread {
    
    ArrayList<AutoSection> sections;
    int section;

    public AutoThread() {
        
        section = 0;
        sections = new ArrayList<AutoSection>();

    }

    public void addSection(AutoSection s) {
        sections.add(s);
    }

    public void update() {
        
        if (section < sections.size()) {

            if (!sections.get(section).hasStarted()) {

                sections.get(section).init();
            
            } else {
                sections.get(section).update();
            }

            if (sections.get(section).disableCondition()) {
                sections.get(section).disabled();
                section++;
            }

        }
    }
}