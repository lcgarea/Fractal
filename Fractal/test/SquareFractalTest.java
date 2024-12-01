import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import java.awt.Color;

import Fractal.*;
public class SquareFractalTest {

    private SquareFractal squareFractal;
    
    @BeforeEach
    void setup(){
        squareFractal = new SquareFractal();
        squareFractal.setSize(400, 400);
        squareFractal.setVisible(true);

    }

    @Test
    public void testSetAndGetTief(){
        int testTief = 5;
        squareFractal.setTief(testTief);
        assertEquals(testTief, squareFractal.getTief(), "Soll korrket sein");
    }

    @Test
    public void testSetAndGetColot(){
        Color testColor = new Color(110, 150, 200);
        squareFractal.setColor(testColor);
        assertSame(testColor, squareFractal.getColor());
    }

    @Test
    public void testDefaultValue(){
        assertEquals(1, squareFractal.getTief());
        assertNotNull(squareFractal.getColor());
    }

}
