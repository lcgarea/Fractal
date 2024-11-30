package Fractal;
import java.awt.Color;

import javax.swing.JPanel;

public class FraktalBase extends JPanel {
    protected Color color;
     public FraktalBase (){
        this.color = new Color(77, 153, 77);
     }

    
    public void setColor(Color color){
        this.color = color;
        repaint();
    }

    public Color getColor(){
        return this.color;
    }



}
