
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.Color;



/**
 * Teilt man das Dreieck in vier zueinander kongruente und zum Ausgangsdreieck 
 * ähnliche Dreiecke, deren Eckpunkte die Seitenmittelpunkte des Ausgangsdreiecks 
 * sind, dann sind die Teilmengen des Fraktals in den drei äußeren Dreiecken
 *  skalierte Kopien des gesamten Fraktals, während das mittlere Teildreieck 
 * nicht zum Fraktal gehört.
 */


public class SierpinskiDreieck extends FractalBase {
  /*   private FractalParameterPanelHelper parameterHelper;

    public SierpinskiDreieck(){
        this.parameterHelper = new FractalParameterPanelHelper(this);
        this.tief = parameterHelper.getTiefSlider().getValue();
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
        
    } */




    private void drawSpierpinski ( Graphics2D g, int tief, int x1, int y1,  int x2, int y2,  int x3, int y3 ){
        if(tief <= 0 || Math.abs(x1 - x2) < 2) return;
        g.drawLine(x1, y1, x2, y2);
        g.drawLine(x2, y2, x3, y3);
        g.drawLine(x3, y3, x1, y1);
       
        // mittelPunkt von der Linien
        int mp1X = (x1 + x2) / 2;
        int mp1Y = (y1 + y2) / 2;
        int mp2X = (x2 + x3) / 2;
        int mp2Y = (y2 + y3) / 2;
        int mp3X = (x3 + x1) / 2;
        int mp3Y = (y3 + y1) / 2;
        drawSpierpinski(g, tief - 1, x1, y1, mp1X, mp1Y, mp3X, mp3Y);
        drawSpierpinski(g, tief -1, x2, y2, mp1X, mp1Y, mp2X, mp2Y);
        drawSpierpinski(g, tief - 1, x3,y3, mp2X, mp2Y, mp3X, mp3Y);
        

    }
    protected void setupTransform(Graphics2D g) {
        int width = getWidth();
        int height = getHeight();
    
        // Transformation: Zentrieren und skalieren
        g.translate(width / 2, height / 2); // Zentriert den Ursprung
        double scale = Math.min(width, height) / 400.0; // Einheitliche Skalierung
        g.scale(scale, scale);
    }
    

    public void paint(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        setupTransform(g2);
        g2.setColor(color);
        int x1 = -200, y1 = 200;   // Untere linke Ecke
        int x2 = 200, y2 = 200;    // Untere rechte Ecke
        int x3 = 0, y3 = -200;     // Spitze oben
        drawSpierpinski(g2, tief, x1, y1, x2, y2, x3,y3);
    }



}
