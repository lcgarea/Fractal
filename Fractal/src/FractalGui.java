import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FractalGui {
    private JFrame frame;
    private JPanel fractalPanel;
    private JPanel paremeterPanel;
    private CardLayout parameterCardLayout;
    private CardLayout cardLayout;
    private JComboBox<FractalType> fractalChooser;
    private JSlider tiefSlider;
    private JSlider colorSlider;
    JPanel rightPanel;
    static final int ABSTAND_OBEN = 10; // abstand oben in button
    static final int ABSTAND_TEXT = 20; // abstand zwischen textfeld und Label
    static final int CONTROLFRAMESIZE_WIDTH = 900;
    static final int CONTROLFRAMESIZE_HEIGHT = 700;
    
    public static void main(String[] args) {
        FractalGui gui = new FractalGui();
        gui.go();
        
    }

    public void go(){
        frame = new JFrame("Fractal Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        

        rightPanel = new JPanel(new BorderLayout());
        
      

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Abstände


        fractalChooser = new JComboBox<>(FractalType.values());
        fractalChooser.addActionListener(new FractalChooserLiestener());
        


        // Start tesst 
        //---------------------------------
        tiefSlider = new JSlider(JSlider.HORIZONTAL, 0, 14, 1);
        tiefSlider.setMajorTickSpacing(2);
        tiefSlider.setMinorTickSpacing(1);
        tiefSlider.setPaintTicks(true);
        tiefSlider.setPaintLabels(true);
        tiefSlider.addChangeListener(new tiefListener2());

        colorSlider = new JSlider(JSlider.HORIZONTAL,0, 255, 50);
        colorSlider.setMajorTickSpacing(50);
        colorSlider.setMinorTickSpacing(10);
        colorSlider.setPaintTicks(true);
        colorSlider.setPaintLabels(true);
        colorSlider.addChangeListener(new colorListener()); 
        

        //Gui Layout
        controlPanel.add(Box.createVerticalStrut(ABSTAND_OBEN));
        controlPanel.add(new JLabel("Choose Fractal:"));
        controlPanel.add(fractalChooser);
        
        controlPanel.add(Box.createVerticalStrut(ABSTAND_TEXT));
        controlPanel.add(new JLabel("Color"));
        controlPanel.add(Box.createVerticalStrut(ABSTAND_TEXT));
        controlPanel.add(colorSlider);

       controlPanel.add(Box.createVerticalStrut(ABSTAND_TEXT));
       controlPanel.add(new JLabel("Level"));
       controlPanel.add(Box.createVerticalStrut(ABSTAND_TEXT));
        controlPanel.add(tiefSlider);
       
        
        //controlPanel.createGlue();
        

      // Fractal panel
        fractalPanel = new JPanel(cardLayout = new CardLayout());
        for (FractalType fractalType : FractalType.values()){
            fractalPanel.add(fractalType.getFractalPanel(),fractalType.name());
        }

        paremeterPanel = new JPanel(parameterCardLayout = new CardLayout());
        for (FractalType fractalType : FractalType.values()) {
        if (fractalType.getFractalPanel() instanceof ConfigurableFractal) {
        ConfigurableFractal fractal = (ConfigurableFractal) fractalType.getFractalPanel();
        paremeterPanel.add(fractal.getConfigPanel(), fractalType.name());
            }
            else {
                // Leeres Panel für nicht-konfigurierbare Fraktale
                paremeterPanel.add(new JPanel(), fractalType.name());
            }
        }
        

        rightPanel.add(controlPanel, BorderLayout.NORTH); // Control-Panel oben
        rightPanel.add(paremeterPanel, BorderLayout.CENTER); // Parameter-Panel unten

       
  
        frame.add(BorderLayout.EAST, rightPanel);
        frame.add(BorderLayout.CENTER, fractalPanel);
        frame.setPreferredSize(new Dimension(CONTROLFRAMESIZE_WIDTH, CONTROLFRAMESIZE_HEIGHT));
        frame.pack();
        frame.setVisible(true); 
    }

    public class FractalChooserLiestener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            FractalType selectedFractal = (FractalType) fractalChooser.getSelectedItem();
            if (selectedFractal != null) {
                cardLayout.show(fractalPanel, selectedFractal.name());
                parameterCardLayout.show(paremeterPanel, selectedFractal.name());
            }
        }
    }


    //Listener für das Dropdown-menu
    /*public class FractalChooserLiestener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            
            FractalType selectedFractal = (FractalType) fractalChooser.getSelectedItem();
            if(selectedFractal !=null){
                cardLayout.show(fractalPanel, selectedFractal.name());
                parameterCardLayout.show(paremeterPanel, selectedFractal.name());
            }
            
        }
    }*/
    public class tiefListener2 implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent e){
            Component currentFractalPanel = null;
            for(Component comp : fractalPanel.getComponents()){
                if(comp.isVisible()){
                    currentFractalPanel = comp;
                    break;
                }
            }

            if(currentFractalPanel instanceof FractalBase){
                FractalBase fractal = (FractalBase)currentFractalPanel;
                fractal.setTief(tiefSlider.getValue());
                fractal.repaint();
            } 


        }
    }

    public class colorListener implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent e){
            Component currentFractalPanel = null;
            for (Component comp : fractalPanel.getComponents()){
                if(comp.isVisible()){
                    currentFractalPanel = comp;
                    break;
                }

            }
            if(currentFractalPanel instanceof FractalBase){
                FractalBase fractal = (FractalBase) currentFractalPanel;
                float hue = colorSlider.getValue()/255.0f;
                fractal.setColor(Color.getHSBColor(hue, 1.0f, 1.0f));
                fractal.repaint();
            }
            if (currentFractalPanel instanceof PythagorasBaum) {
                PythagorasBaum fractal = (PythagorasBaum) currentFractalPanel;
                float hue = colorSlider.getValue() / 255.0f;
                fractal.setBaseHue(hue);
                fractal.repaint();
            }
        }
    } 



}
