import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class MyDrawPanel extends JPanel{
    public void paintFractal( Graphics g){
        Graphics2D g2d = (Graphics2D) g;
       
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        Color startColor = new Color(red, green, blue);

       
        red = random.nextInt(256);
        green = random.nextInt(256);
        blue = random.nextInt(256);
        Color endColor = new Color(red, green, blue);

        GradientPaint gradient = new GradientPaint(70,70, startColor, 150, 150, endColor);
        g2d.setPaint(gradient);
        g2d.fillRect(30, 30, 100, 100);


    }

}