
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 * Die Koch-Scheeflocke entssteht, indem eine Linier Rekursive in vier Teile
 * geteil wird:
 * <ul> 
 * <li>Der mittlere Teil der Linier wir durch ein gleicseitiges Dreieck ersetzt.</li>
 * <li> Der ursprüngliche mittlere Teil wird entfernt. </li>
 * <li>  Dies wird wiederholt, bis die gewünschte Rekursionstiefe erreicht ist </li>
 * </ul>
 */
public class SchneeFlocke  extends FractalBase implements ConfigurableFractal{
    private static final int MARGIN = 20;  // Abstand vom Rand
    
    private FractalParameterPanelHelper parameterHelper;

    public SchneeFlocke(){
        this.parameterHelper = new FractalParameterPanelHelper(this);
        
    }

    @Override
    public JPanel getConfigPanel() {
        return parameterHelper.createBaseConfigPanel(); // Gemeinsames Panel
    }

    @Override
    public void applyParameters() {
        this.tief = parameterHelper.getTiefSlider().getValue(); // Slider-Wert übernehmen
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Hier die Fraktal-Logik basierend auf "tief" zeichnen
    }

    
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

    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
       
        
        int width = getWidth();
        int height = getHeight();
        int margin = MARGIN; // Abstand vom Rand
        
        // Größe des Dreiecks basierend auf Panelgröße
        int triWidth = (int) ((width - 2 * margin)*zoomFactor);
        int triHeight = (int) (triWidth * Math.sqrt(3) / 2); // Höhe eines gleichseitigen Dreiecks
        
        // Basis des Dreiecks (unten zentriert)
        int centerY = height / 2; // Vertikale Mitte
        int x1 = (width - triWidth) / 2; // Unten links
        int y1 = centerY + triHeight / 2; // Basislinie
        int x2 = (width + triWidth) / 2; // Unten rechts
        int y2 = y1;
        int x3 = width / 2; // Spitze (oben zentriert)
        int y3 = centerY - triHeight / 2;
        
        
        // Zeichne die Schneeflocke
        g2.setColor(color);
        System.out.println("Painting with color: " + color);
        drawKoch(g2, tief, x1, y1, x2, y2);
        drawKoch(g2, tief, x2, y2, x3, y3);
        drawKoch(g2, tief, x3, y3, x1, y1);
        
    
    
        
    }

    




}
