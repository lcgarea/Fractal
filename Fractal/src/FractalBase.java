import javax.swing.JPanel;
import java.awt.Color;

/**
 * Base Klasse für alle Fractale:
 * Atttibutte:
 * - tief
 * - zoomFactor
 * - color
 */

public class FractalBase extends FraktalBaseV0{
    protected int tief = 1 ;
    protected double zoomFactor = 1.0;
   // protected Color color;

     public void setTief(int tief){
        this.tief = tief;
    }
    public double getZoomFactor() {
        return zoomFactor;
    }
    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }
    public int getTief(){
        return this.tief;
    }
   /*  public void setColor(Color color){
        this.color = color;
        //System.out.println("Color set to: " + color);
        //this.repaint();
    } */



}
