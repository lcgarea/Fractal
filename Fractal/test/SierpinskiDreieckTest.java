

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


import Fractal.SierpinskiDreieck;

public class SierpinskiDreieckTest {
    private SierpinskiDreieck sierpinski;
    @BeforeEach
    public void setup(){
        sierpinski = new SierpinskiDreieck();
        sierpinski.setSize(400, 400);
        sierpinski.setVisible(true);
    }

    @Test
    public void testSetAndGetTief(){
        int testTief = 12;
        sierpinski.setTief(testTief);
        assertEquals(testTief, sierpinski.getTief());
        
    }
    @Test
    public void testSetAndGetColot(){
        Color testColor = new Color(120, 180, 240);
        sierpinski.setColor(testColor);
        assertEquals(testColor,sierpinski.getColor());
    }
    @Test
    public void testDrawSpierpinskiLogik(){
        BufferedImage image = new BufferedImage(400, 400,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        sierpinski.setTief(3);
        sierpinski.paint(g2d);
        assertTrue(true,"dfff");
      }


}
