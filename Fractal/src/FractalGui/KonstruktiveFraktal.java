package FractalGui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.function.Consumer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Fractal.ConfigurableFractal;
import Fractal.KonstruktiveBase;
import Fractal.FractalType;
import Fractal.KomplexeBase;

public class KonstruktiveFraktal {
    private JFrame frame;
    private JPanel fractalPanel;
    private JPanel paremeterPanel;
    private CardLayout parameterCardLayout;
    private CardLayout cardLayout;
    private JComboBox<FractalType> fractalChooser;
    private JSlider tiefSlider;
    private JSlider redSlider;
    private JSlider greenSlider;
    private JSlider blueSlider;
    JPanel rightPanel;
    static final int ABSTAND_OBEN = 10; // abstand oben in button
    static final int ABSTAND_TEXT = 20; // abstand zwischen textfeld und Label
    static final int CONTROLFRAMESIZE_WIDTH = 900;
    static final int CONTROLFRAMESIZE_HEIGHT = 700;
    
    public static void main(String[] args) {
        KonstruktiveFraktal gui = new KonstruktiveFraktal();
        gui.go();
        
    }

    public void go(){
        frame = new JFrame("Fractal Generator");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        

        rightPanel = new JPanel(new BorderLayout());
        
      

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Abstände

        fractalChooser = new JComboBox<>(
            Arrays.stream(FractalType.values())
            .filter(f -> !(f.getFractalPanel() instanceof KomplexeBase))
            .toArray(FractalType[]::new)
        );

        //fractalChooser = new JComboBox<>(FractalType.values());
        fractalChooser.addActionListener(new FractalChooserLiestener());
        

        
      
        //---------------------------------
        tiefSlider = new JSlider(JSlider.HORIZONTAL, 0, 15, 1);
        tiefSlider.setMajorTickSpacing(2);
        tiefSlider.setMinorTickSpacing(1);
        tiefSlider.setPaintTicks(true);
        tiefSlider.setPaintLabels(true);
        tiefSlider.addChangeListener(new tiefListener2());
      


        // Color Slider
        redSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        redSlider.addChangeListener(new colorListener());
        greenSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        greenSlider.addChangeListener(new colorListener());
        blueSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        blueSlider.addChangeListener(new colorListener());

        

        //Gui Layout
        controlPanel.add(Box.createVerticalStrut(ABSTAND_OBEN));
        controlPanel.add(new JLabel("Choose Fractal:"));
        controlPanel.add(fractalChooser);

        controlPanel.add(Box.createVerticalStrut(ABSTAND_TEXT));
        controlPanel.add(new JLabel("Color"));
        controlPanel.add(Box.createVerticalStrut(ABSTAND_TEXT));
        controlPanel.add(new JLabel("Red"));
        controlPanel.add(redSlider);
        controlPanel.add(Box.createVerticalStrut(ABSTAND_TEXT));
        controlPanel.add(new JLabel("Green"));
        controlPanel.add(greenSlider);
        controlPanel.add(Box.createVerticalStrut(ABSTAND_TEXT));
        controlPanel.add(new JLabel("Blue"));
        controlPanel.add(blueSlider);


       controlPanel.add(Box.createVerticalStrut(ABSTAND_TEXT));
       controlPanel.add(new JLabel("Level"));
       controlPanel.add(Box.createVerticalStrut(ABSTAND_TEXT));
        controlPanel.add(tiefSlider);
       
            

      // Fractal panel
        fractalPanel = new JPanel(cardLayout = new CardLayout());
        for (FractalType fractalType : FractalType.values()){
            if(!(fractalType.getFractalPanel() instanceof KomplexeBase)){
                fractalPanel.add(fractalType.getFractalPanel(),fractalType.name());
            }
            
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

    private void update(Consumer<KonstruktiveBase> action){
        Component currentFractalPanel = null;
        for(Component comp : fractalPanel.getComponents()){
            if (comp.isVisible()){
                currentFractalPanel = comp;
                break;
            }

        }
        if (currentFractalPanel instanceof KonstruktiveBase){
            KonstruktiveBase fractal = (KonstruktiveBase) currentFractalPanel;
            action.accept(fractal);;
            fractal.repaint();
        }
    }

    public class FractalChooserLiestener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            FractalType selectedFractal = (FractalType) fractalChooser.getSelectedItem();
            if (selectedFractal != null) {
                
                cardLayout.show(fractalPanel, selectedFractal.name());
                parameterCardLayout.show(paremeterPanel, selectedFractal.name());
                update(fractal ->{
                    Color fractalColor = fractal.getColor()!= null ? fractal.getColor():new Color(77, 153, 77);
                    redSlider.setValue(fractalColor.getRed());
                    greenSlider.setValue(fractalColor.getGreen());
                    blueSlider.setValue(fractalColor.getBlue());
                    tiefSlider.setValue(fractal.getTief());
                });
            }
        }
    }
    public class tiefListener2 implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent e){
          update(fractal -> fractal.setTief(tiefSlider.getValue()));
        }
    }
    public class colorListener implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent e){
            int red = redSlider.getValue();
            int green = greenSlider.getValue();
            int blue = blueSlider.getValue();
            Color newColor  = new Color(red, green, blue);
            update(fractal -> fractal.setColor(newColor));
        }
    } 
    



}
