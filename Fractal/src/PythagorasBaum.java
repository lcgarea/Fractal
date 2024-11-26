import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class PythagorasBaum extends FractalBase implements ConfigurableFractal{ 
    private FractalParameterPanelHelper parameterHelper;
    private JSlider kSlider;
    private double k;

    public PythagorasBaum() {
        parameterHelper = new FractalParameterPanelHelper(this);
        this.tief = parameterHelper.getTiefSlider().getValue();

        // Initialisiere Slider für "k"
        kSlider = new JSlider(JSlider.VERTICAL, 0, 100, 50);
        kSlider.setMajorTickSpacing(25);
        kSlider.setMinorTickSpacing(5);
        kSlider.setPaintTicks(true);
        kSlider.setPaintLabels(true);
        kSlider.addChangeListener(e -> this.applyParameters());
    }

    @Override
    public JPanel getConfigPanel() {
        JPanel panel = parameterHelper.createBaseConfigPanel(); // Gemeinsame Logik für Level
        JLabel kLabel = new JLabel("Skalierung k:");
        kLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(kLabel);
        panel.add(kSlider); // Füge k-Slider hinzu
        return panel;
    }

    @Override
    public void applyParameters() {
        this.tief = parameterHelper.getTiefSlider().getValue();
        this.k = kSlider.getValue() / 100.0; // k-Wert übernehmen
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Fraktal mit "tief" und "k" zeichnen
    }



    public void drawPythagorasBaum(Graphics2D g, int tief, double k, Point p1, Point p2 ){
        if ( tief >= 0){

        g.setColor(new Color(0.0f, 1.0f/(tief + 1.0f), 0.0f, 25.0f/(tief +25.0f)));
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
/*      public static void main(String[] args) {
        JFrame frame = new JFrame();
        PythagorasBaum baum = new PythagorasBaum();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        
        //aum.setColor(Color.GREEN);
        frame.add(baum);
        frame.setVisible(true);
      }*/

}
