import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class FractalGui {
    private JFrame frame;
    private JPanel fractalPanel;
    private JPanel paremeterPanel;
    private CardLayout parameterCardLayout;
    private CardLayout cardLayout;
    private JComboBox<FractalType> fractalChooser;
    JPanel rightPanel;
    static final int ABSTAND_OBEN = 10; // abstand oben in button
    static final int ABSTAND_TEXT = 20; // abstand zwischen textfeld und Label
    static final int CONTROLFRAMESIZE_WIDTH = 900;
    static final int CONTROLFRAMESIZE_HEIGHT = 700;
    //private double zoomFactor = 1.0; // Standard-Zoom

    public static void main(String[] args) {
        FractalGui gui = new FractalGui();
        gui.go();
        
    }

    public void go(){
        frame = new JFrame("Fractal Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
     


       // Panel mit control panel und parameter Pannel
        rightPanel = new JPanel(new BorderLayout());
        
       // Panel mit : Generate, choose Fractal, zoom, color

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Abstände


        fractalChooser = new JComboBox<>(FractalType.values());
        fractalChooser.addActionListener(new FractalChooserLiestener());
        
        JButton buttonGenerate = new JButton("Generate");
        buttonGenerate.addActionListener(new TiefListener());

        JButton zoomInButton = new JButton("Zoom In");
        zoomInButton.addActionListener(new ZoomListener(1.2));

        JButton zoomOutButton = new JButton("Zoom Out");
        zoomOutButton.addActionListener(new ZoomListener(0.8));
        
        JButton colorButton = new JButton("Change Color");
        colorButton.addActionListener(new ColorChoosserListener());

        

        //Gui Layout
        controlPanel.add(Box.createVerticalStrut(ABSTAND_OBEN));
        controlPanel.add(new JLabel("Choose Fractal:"));
        controlPanel.add(fractalChooser);
        controlPanel.add(Box.createVerticalStrut(ABSTAND_TEXT));
        controlPanel.add(buttonGenerate);
        controlPanel.add(Box.createVerticalStrut(ABSTAND_TEXT));
        controlPanel.add(zoomInButton);
        controlPanel.add(Box.createVerticalStrut(ABSTAND_TEXT));
        controlPanel.add(zoomOutButton);
        controlPanel.add(Box.createVerticalStrut(ABSTAND_TEXT));
        controlPanel.add(colorButton);
        controlPanel.add(Box.createVerticalGlue());
    

      // Fractal panel
        fractalPanel = new JPanel(cardLayout = new CardLayout());
        for (FractalType fractalType : FractalType.values()){
            fractalPanel.add(fractalType.getFractalPanel(),fractalType.name());
        }

        // Paremter panel
        paremeterPanel = new JPanel(parameterCardLayout = new CardLayout());
        for( FractalType fractalType : FractalType.values()){
            if( fractalType.getFractalPanel() instanceof ConfigurableFractal){
                ConfigurableFractal fractal = (ConfigurableFractal) fractalType.getFractalPanel();
                paremeterPanel.add(fractal.getConfigPanel(), fractalType.name());

            }
        }
        rightPanel.add(controlPanel, BorderLayout.NORTH); // Control-Panel oben
        rightPanel.add(paremeterPanel, BorderLayout.CENTER); // Parameter-Panel unten

       
  
        frame.add(BorderLayout.EAST, rightPanel);
        frame.add(BorderLayout.CENTER, fractalPanel);
        frame.setPreferredSize(new Dimension(CONTROLFRAMESIZE_WIDTH, CONTROLFRAMESIZE_HEIGHT));
        //frame.setSize(800, 600);
        frame.pack();
        frame.setVisible(true); 
    }


    //Listener für das Dropdown-menu
    public class FractalChooserLiestener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            
            FractalType selectedFractal = (FractalType) fractalChooser.getSelectedItem();
            if(selectedFractal !=null){
                cardLayout.show(fractalPanel, selectedFractal.name());
                parameterCardLayout.show(paremeterPanel, selectedFractal.name());
            }
        }
    }
    public class TiefListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            Component currentFractalPanel = null;
            for(Component comp : fractalPanel.getComponents()){
                if(comp.isVisible()){
                    currentFractalPanel = comp;
                    break;
                }
            }

            if(currentFractalPanel instanceof ConfigurableFractal){
                ConfigurableFractal fractal = (ConfigurableFractal)currentFractalPanel;
                fractal.applyParameters();
            } else{
                JOptionPane.showMessageDialog(frame, "Kein Fraktal ausgewählt!", "Fehler", JOptionPane.ERROR_MESSAGE);
            }

        
        }
    }
    public class ZoomListener implements ActionListener {
        private double factor; // Der Zoom-Faktor
    
        public ZoomListener(double factor) {
            this.factor = factor;
        }
    
        @Override
        public void actionPerformed(ActionEvent event) {
            // Auf alle Fraktale im Enum anwenden
            for (FractalType fractalType : FractalType.values()) {
                if (fractalType.getFractalPanel() instanceof FractalBase) {
                    FractalBase fractal = (FractalBase) fractalType.getFractalPanel();
                    fractal.setZoomFactor(fractal.getZoomFactor() * factor); // Zoom anpassen
                    fractal.repaint(); // Panel neu zeichnen
                }
            }
        }
    }
    public class ColorChoosserListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            Color newColor = JColorChooser.showDialog(frame, "Choose a Color", Color.BLACK);
            if(newColor != null){
                for (Component comp : fractalPanel.getComponents()) {
                    if (comp.isVisible() && comp instanceof FractalBase) {
                        FractalBase activeFractal = (FractalBase) comp;
                        System.out.println("new Color:" + newColor);
                        System.out.println("Aktives Fraktal: " + activeFractal.getClass().getName());
                        activeFractal.setColor(newColor); 
                        break;
                    }
                }
            
            }
        }

    }
    
    



}
