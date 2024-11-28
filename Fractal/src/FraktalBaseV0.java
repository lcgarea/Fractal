import java.awt.Color;

import javax.swing.JPanel;

public class FraktalBaseV0 extends JPanel {
    protected Color color;

    public void setColor(Color color){
        this.color = color;
        repaint();
    }

    public Color getColor(){
        return this.color;
    }



}
