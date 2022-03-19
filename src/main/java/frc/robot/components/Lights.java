package frc.robot.components;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class Lights {
    AddressableLEDBuffer buffer;
    AddressableLED leds;

    public Lights(int length) {
        buffer = new AddressableLEDBuffer(length);
        leds = new AddressableLED(buffer.getLength());
        leds.setData(buffer);
        leds.start();
    }

    public void setLights(int r, int g, int b) {
        for (int i = 0; i < buffer.getLength(); i++) {
            buffer.setRGB(i, r, g, b);
        }
    }
    
}
