
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import javax.swing.JPanel;

/**
 * Die Koch-Scheeflocke entssteht, indem eine Linier Rekursive in vier Teile
 * geteil wird:
 * <ul> 
 * <li>Der mittlere Teil der Linier wir durch ein gleicseitiges Dreieck ersetzt.</li>
 * <li> Der ursprüngliche mittlere Teil wird entfernt. </li>
 * <li>  Dies wird wiederholt, bis die gewünschte Rekursionstiefe erreicht ist </li>
 * </ul>
 */
public class SchneeFlocke  extends FractalBase {
    
    
    private FractalParameterPanelHelper parameterHelper;

     /*public SchneeFlocke(){
        this.parameterHelper = new FractalParameterPanelHelper(this);
        
    }

    @Override
    public JPanel getConfigPanel() {
        return parameterHelper.createBaseConfigPanel(); // Gemeinsames Panel
    }

    @Override
    public void applyParameters() {
        this.tief = parameterHelper.getTiefSlider().getValue(); // Slider-Wert übernehmen
        float hue = parameterHelper.getColoSlider().getValue()/255.0f;
        this.color =Color.getHSBColor(hue, 1.0f, 1.0f);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Hier die Fraktal-Logik basierend auf "tief" zeichnen
    }
 */
    
    private void drawKoch(Graphics g, int tief, int p1X, int p1Y, int p2X, int p2Y ){
        double linienLaenge = Math.sqrt(Math.pow(p2X - p1X, 2) + Math.pow(p2Y - p1Y, 2));
        if (tief == 0 || linienLaenge < 2) { 
            g.drawLine(p1X, p1Y, p2X, p2Y);
            return; 
        }
        
        
        else{
            int deltaX = p2X -p1X;
            int deltaY = p2Y - p1Y;

            //teile die Linie
            int pAX = p1X + deltaX/3;
            int pAY = p1Y + deltaY/3;

            int pBX = p2X - deltaX/3;
            int pBY = p2Y - deltaY/3;

            // 
            double winkel = Math.PI / 3;
            int xPeak = (int) (pAX + Math.cos(winkel) * (pBX - pAX) - Math.sin(winkel) * (pBY - pAY));
            int yPeak = (int) (pAY + Math.sin(winkel) * (pBX - pAX) + Math.cos(winkel) * (pBY - pAY));

            //rekursive Aufrufe für die vier neuen Segmmente

            drawKoch(g, tief -1, p1X, p1Y, pAX, pAY);
            drawKoch(g, tief - 1, pAX, pAY, xPeak, yPeak);
            drawKoch(g, tief - 1, xPeak, yPeak, pBX, pBY);
            drawKoch(g, tief - 1, pBX, pBY, p2X, p2Y);
        }
    }
    protected void setupTransform(Graphics2D g) {
        int width = getWidth();
        int height = getHeight();
    
        // Transformation: Zentrieren und skalieren
        g.translate(width / 2, height / 2); // Zentriert den Ursprung
        double scale = Math.min(width, height) / 600.0; // Einheitliche Skalierung
        g.scale(scale, scale);
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
       
        setupTransform(g2);
        int x1 = -200, y1 = 115;   // Untere linke Ecke
        int x2 = 200, y2 = 115;    // Untere rechte Ecke
        int x3 = 0, y3 = -230;     // Spitze oben
    
        
        // Zeichne die Schneeflocke
        g2.setColor(color);
        System.out.println("Painting with color: " + color);
        drawKoch(g2, tief, x1, y1, x2, y2);
        drawKoch(g2, tief, x2, y2, x3, y3);
        drawKoch(g2, tief, x3, y3, x1, y1);
        
    
    
        
    }

    




}
