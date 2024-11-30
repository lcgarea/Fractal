package Fractal;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Hashtable;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class PythagorasBaum extends KonstruktiveBase implements ConfigurableFractal { 

    private JSlider kSlider;
    private double k = 0.5;
    private float baseHue = 0.33f;
    private final int ABSTAND_OBEN = 10;

  

    public PythagorasBaum() {
        this.color = new Color(77,153,77);

        // Hashtable, um Dezimalwerte zu simulieren
         Hashtable<Integer, JLabel> zahlenSkal = new Hashtable<>();
        int scaleFactor = 10; 
        for (int i = 1; i <= scaleFactor; i +=10) {
            double value = i / (double) scaleFactor; // Dezimalwert berechnen
            zahlenSkal.put(i, new JLabel(String.format("%.1f", value))); // Beschriftung hinzufügen
        }
        
        kSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        kSlider.setMajorTickSpacing(25);
        kSlider.setMinorTickSpacing(5);
        kSlider.setPaintTicks(true);
        kSlider.setPaintLabels(true);
        //kSlider.setLabelTable(zahlenSkal);
        kSlider.addChangeListener(e -> {
            this.k = kSlider.getValue() / 100.0; // Aktualisiere k
            repaint(); // Neu zeichnen
        });
       
    }
    public void applyParameters() {
      
        this.k = kSlider.getValue() / 100.0; // k-Wert übernehmen
        repaint();
    } 
    @Override
    public JPanel getConfigPanel() {
        JPanel panel = new JPanel(); // Gemeinsame Logik für Level
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel kLabel = new JLabel("<html>Skalierung k (10<sup>-2</sup>):</html>");
        kLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(Box.createVerticalStrut(ABSTAND_OBEN));
        panel.add(kLabel);

        panel.add(Box.createVerticalStrut(ABSTAND_OBEN));
        panel.add(kSlider); 

        return panel;
    }

   

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Fraktal mit "tief" und "k" zeichnen
    }



    public void drawPythagorasBaum(Graphics2D g, int tief, double k, Point p1, Point p2 ){
        if ( tief >= 0){
            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();
            if(red == 255 && green == 255 && blue == 255){
                float saturation = 0.0f; // Sin saturación para blanco perfecto
                float brightness = 1.0f; // Máximo brillo
                float alpha = 1.0f;      // Total opacidad
                Color whiteColor = Color.getHSBColor(0, saturation, brightness);
                g.setColor(new Color(whiteColor.getRed(), whiteColor.getGreen(), whiteColor.getBlue()));

            }else{
                float[] baseHSB = Color.RGBtoHSB(red, green, blue, null);
                float adjustedHue = (baseHSB[0] + 0.02f * tief) % 1.0f;
                float adjustedSaturation = (tief == 0)
                    ? 0.6f // Saturación moderada para el tronco
                    : Math.max(0.8f, baseHSB[1] * (1.0f / (tief + 1.0f))); //Escalar la saturación
                    float adjustedBrightness = (tief == 0)
                    ? 0.4f // Tronco oscuro
                    : Math.min(1.0f, baseHSB[2] * (1.0f - (0.03f * tief))); // Reducir brillo según el nivel
                Color adjustedColor = Color.getHSBColor(adjustedHue, adjustedSaturation, adjustedBrightness);
                float alpha = Math.max(0.5f, 25.0f / (tief + 25.0f));  // Controlar la transparencia (alfa) según el nivel
            g.setColor(new Color(adjustedColor.getRed(), adjustedColor.getGreen(), adjustedColor.getBlue(), (int) (alpha * 255)));
            }
            

            /*  float saturation = 0.9f / (tief + 1.0f);
            float brightness = 0.9f;
            float alpha = 20.0f / (tief + 25.0f);
            Color color = Color.getHSBColor(baseHue, saturation, brightness); */

        //g.setColor(new Color (color.getRed(), color.getGreen(), color.getBlue(), (int) (alpha * 255)));
        tief --;

        int dx = p2.x - p1.x;
        int dy = p1.y - p2.y;

        
        Point p3 = new Point(p2.x - dy, p2.y - dx);
        Point p4 = new Point(p1.x -dy, p1.y - dx );

        // polygon zeichnen

        Polygon quadrat = new Polygon();
        quadrat.addPoint(p1.x, p1.y);
        quadrat.addPoint(p2.x, p2.y);
        quadrat.addPoint(p3.x, p3.y);
        quadrat.addPoint(p4.x, p4.y);
        g.fillPolygon(quadrat);


        //Spitze der dreieck
        
        int xN = (int) (k * p1.x + (1 -k) * p2.x 
                        - (1 + Math.sqrt(k * ( 1 - k))) * dy);
        int yN = (int) (k* p1.y + (1 - k) * p2.y
                        - (1 + Math.sqrt(k * ( 1 - k ))) * dx);
        
        Point pN = new Point(xN, yN);

        // Dreieck zeichnen
        
        drawPythagorasBaum(g, tief, k, pN, p3);
        drawPythagorasBaum(g, tief, k, p4, pN);
        }

    }

        protected void setupTransform(Graphics2D g) {
        int width = getWidth();
        int height = getHeight();
    
        // Transformation: Zentrieren und skalieren
        g.translate(width / 2, height / 2 + 100); // Zentriert den Ursprung
        double scale = Math.min(width, height) / 800.0; // Einheitliche Skalierung
        g.scale(scale, scale);
    }
     public void paint( Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        setupTransform(g2);
        Point p1 = new Point(-50, 50);
        Point p2 = new Point(50,  50);
        
        drawPythagorasBaum(g2, tief, k, p1, p2);

     }

    
}
