package frc.robot.components;

import java.nio.channels.Selector;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class Lights {
    AddressableLEDBuffer buffer;
    AddressableLED leds;

    int m_rainbowFirstPixelHue;
    

    int hue = 0;

    public Lights(int length) {
        buffer = new AddressableLEDBuffer(length);
        leds = new AddressableLED(1);
        leds.setLength(buffer.getLength());
        setLights(0, 0, 255);
        leds.start();
        m_rainbowFirstPixelHue = 0;
    }

    public void setLights(int r, int g, int b) {
        for (int i = 0; i < buffer.getLength(); i++) {
            buffer.setRGB(i, r, g, b);
        }
    }

    public void rainbow() {
        // For every pixel
    for (var i = 0; i < buffer.getLength(); i++) {
        // Calculate the hue - hue is easier for rainbows because the color
        // shape is a circle so only one value needs to precess
        final var hue = (m_rainbowFirstPixelHue + (i * 180 / buffer.getLength())) % 180;
        // Set the value
        buffer.setHSV(i, hue, 255, 128);
      }
      // Increase by to make the rainbow "move"
      m_rainbowFirstPixelHue += 3;
      // Check bounds
      m_rainbowFirstPixelHue %= 180;
    }

    public void update() {
        leds.setData(buffer);
    }
    
}
