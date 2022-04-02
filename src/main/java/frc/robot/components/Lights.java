package frc.robot.components;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;

public class Lights {
    AddressableLEDBuffer underglowBuffer;
    AddressableLED underglow;

    int m_rainbowFirstPixelHue;

    int hue = 0;

    public Lights(int length) {

        /**
         * 0-112 - Underglow
         * 113-135 - Left Climb
         * 137-174 - Right Climb
         */

        underglow = new AddressableLED(1);
        underglowBuffer = new AddressableLEDBuffer(length);
        underglow.setLength(underglowBuffer.getLength());
        underglow.start();

        m_rainbowFirstPixelHue = 0;

        setLights(0, 0, 255);
        //rainbow();
    }

    public void setLights(int r, int g, int b) {
        for (int i = 0; i < underglowBuffer.getLength(); i++) {
            underglowBuffer.setRGB(i, r, g, b);
        }
        /*for (int i = 0; i < rightBuffer.getLength(); i++) {
            rightBuffer.setRGB(i, r, g, b);
        }*/
    }

    public void setAlliance() {
        if (DriverStation.getAlliance().name().startsWith("B")) {
            setLights(0, 0, 255);
        } else {
            setLights(255, 0, 0);
        }
    }

    public void rainbow() {
        // For every pixel
    for (var i = 0; i < underglowBuffer.getLength(); i++) {
        // Calculate the hue - hue is easier for rainbows because the color
        // shape is a circle so only one value needs to precess
        final var hue = (m_rainbowFirstPixelHue + (i * 180 / underglowBuffer.getLength())) % 180;
        // Set the value
        underglowBuffer.setHSV(i, hue, 255, 128);
      }
      // Increase by to make the rainbow "move"
      m_rainbowFirstPixelHue += 3;
      // Check bounds
      m_rainbowFirstPixelHue %= 180;
    }

    public void update() {
        underglow.setData(underglowBuffer);
        /*rightClimb.setData(rightBuffer);
        leftClimb.setData(leftBuffer);*/
    }

    public void setLightsTo(int min, int max, int r, int g, int b) {
        for (int i = min; i <= max; i++) {
            underglowBuffer.setRGB(i, r, g, b);
        }
    }
    
}
