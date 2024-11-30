package FractalGui;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Fractal.ConfigurableFractal;
import Fractal.KonstruktiveBase;
import Fractal.FractalType;
import Fractal.KomplexeBase;

public class KomplexeFraktaleGui {
    private JFrame frame;
    private JPanel rightPanel;
    private JComboBox <FractalType> fractalChooser;
    private CardLayout cardLayout;
    private CardLayout parameterCardLayout;
    private JPanel fractalPanel;
    private JPanel paremeterPanel;
    private JSlider iterSlider;
    private JSlider redSlider;
    private JSlider greenSlider;
    private JSlider blueSlider;
    private JSlider red2Slider;
    private JSlider green2Slider;
    private JSlider blue2Slider;

    static final int ABSTAND_OBEN = 10; // abstand oben in button
    static final int ABSTAND_TEXT = 20; // abstand zwischen textfeld und Label
    static final int ABSTAND_PANEL = 40; // abstand zwischen textfeld und Panel
    static final int CONTROLFRAMESIZE_WIDTH = 900;
    static final int CONTROLFRAMESIZE_HEIGHT = 700;
    
    public static void main(String[] args) {
        KomplexeFraktaleGui gui = new KomplexeFraktaleGui();
        gui.go();
    }

    public void go(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //Panell mit den Controll und Parmetercontrolle

        rightPanel = new JPanel(new BorderLayout());

        //Control Panel Algemeine Controlle: Level und Farbe
       JPanel controlPanel = new JPanel();
       controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
       controlPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10, 10));
        
       // Fractal Chooser, select nur die fractale die nicht Konstruktive sind

       fractalChooser = new JComboBox<>(
        Arrays.stream(FractalType.values())
        .filter(f -> !(f.getFractalPanel() instanceof KonstruktiveBase))
        .toArray(FractalType[]:: new)
       );
       fractalChooser.addActionListener(new FractalChooserLiestener());

       //Slider iteration// Log Skale
       // Configuration der Slider
       int iterSliderMin = 0;
       int iterSliderMax = 100;
       iterSlider = new JSlider(JSlider.HORIZONTAL, iterSliderMin, iterSliderMax,50);
       //Logarithmischer Wertebereich
       int logMin = 10;
       int logMax = 2000;
       //Label Hashtable
       Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(0, new JLabel("10"));    // 10^1
        labelTable.put(25, new JLabel("100"));  // 10^2
        labelTable.put(50, new JLabel("500"));  // ungefähr
        labelTable.put(75, new JLabel("1000")); // 10^3
        labelTable.put(100, new JLabel("2000"));// ungefähr

        iterSlider.setLabelTable(labelTable);
       iterSlider.setMajorTickSpacing(25);
     
       iterSlider.setPaintTicks(true);
       iterSlider.setPaintLabels(true);
       iterSlider.addChangeListener(new iterListener());

       //Slider Color
       //Slider COlor base
       redSlider = new JSlider(0,255,0);
       greenSlider = new JSlider(0, 255, 0);
       blueSlider = new JSlider(0, 255, 0);

       redSlider.addChangeListener(new colorListener());
       greenSlider.addChangeListener(new colorListener());
       blueSlider.addChangeListener(new colorListener());
       //Slider Color Sekundär
       red2Slider = new JSlider(0,255,0);
       green2Slider = new JSlider(0, 255, 0);
       blue2Slider = new JSlider(0, 255, 0);

       red2Slider.addChangeListener(new color2Listener());
       
       green2Slider.addChangeListener(new color2Listener());
       blue2Slider.addChangeListener(new color2Listener());


       //Layout
        controlPanel.add(Box.createVerticalStrut(ABSTAND_OBEN));
        controlPanel.add(new JLabel("Choose Fractal:"));
        controlPanel.add(fractalChooser);
        controlPanel.add(Box.createVerticalStrut(ABSTAND_PANEL));
        controlPanel.add(new JLabel("Iteration"));
        controlPanel.add(iterSlider);

        

        controlPanel.add(Box.createVerticalStrut(ABSTAND_PANEL));
        controlPanel.add(new JLabel("Mandelbrot Set Color"));
        controlPanel.add(Box.createVerticalStrut(ABSTAND_TEXT));
        controlPanel.add(new JLabel("Red"));
        controlPanel.add(redSlider);
        controlPanel.add(Box.createVerticalStrut(ABSTAND_TEXT));
        controlPanel.add(new JLabel("Green"));
        controlPanel.add(greenSlider);
        controlPanel.add(Box.createVerticalStrut(ABSTAND_TEXT));
        controlPanel.add(new JLabel("Blue"));
        controlPanel.add(blueSlider);
        controlPanel.add(Box.createVerticalStrut(ABSTAND_PANEL));
        controlPanel.add(new JLabel("Color of Escape Points"));
        controlPanel.add(Box.createVerticalStrut(ABSTAND_TEXT));
        controlPanel.add(new JLabel("Red"));
        controlPanel.add(red2Slider);
        controlPanel.add(Box.createVerticalStrut(ABSTAND_TEXT));
        controlPanel.add(new JLabel("Green"));
        controlPanel.add(green2Slider);
        controlPanel.add(Box.createVerticalStrut(ABSTAND_TEXT));
        controlPanel.add(new JLabel("Blue"));
        controlPanel.add(blue2Slider);

        // Fractal panel 
        fractalPanel =  new JPanel(cardLayout = new CardLayout());
        for(FractalType fractalType : FractalType.values()){
            if(!(fractalType.getFractalPanel() instanceof KonstruktiveBase)){
                fractalPanel.add(fractalType.getFractalPanel(), fractalType.name());
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

        rightPanel.add(controlPanel, BorderLayout.NORTH);
        rightPanel.add(paremeterPanel, BorderLayout.CENTER);


        frame.add(BorderLayout.EAST, rightPanel);
        frame.add(BorderLayout.CENTER, fractalPanel);
        frame.setPreferredSize(new Dimension(CONTROLFRAMESIZE_WIDTH, CONTROLFRAMESIZE_HEIGHT));
        frame.pack();
        frame.setVisible(true); 
       

    } 


    private void update(Consumer<KomplexeBase> action){
        Component currentFractalPanel = null;
        for(Component comp : fractalPanel.getComponents()){
            if (comp.isVisible()){
                currentFractalPanel = comp;
                break;
            }

        }
        if (currentFractalPanel instanceof KomplexeBase){
            KomplexeBase fractal = (KomplexeBase) currentFractalPanel;
            action.accept(fractal);;
            fractal.repaint();
        }
    }


    public class FractalChooserLiestener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            FractalType selectedFractal = (FractalType) fractalChooser.getSelectedItem();
            if(selectedFractal != null){
                cardLayout.show(fractalPanel, selectedFractal.name());
                parameterCardLayout.show(paremeterPanel, selectedFractal.name());
            }
            update(fractal ->{
                Color fractalColor = fractal.getColor()!= null ? fractal.getColor():new Color(77, 153, 77);
                redSlider.setValue(fractalColor.getRed());
                greenSlider.setValue(fractalColor.getGreen());
                blueSlider.setValue(fractalColor.getBlue());
                iterSlider.setValue(fractal.getMaxIter());
            });
            
        }
    }

    public class iterListener implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent e){
            int iterSliderMin = 0;
            int iterSliderMax = 100;
            int logMin = 10;
             int logMax = 2000;
            int iterSliderValue = iterSlider.getValue();
            double logValue = Math.pow(10,Math.log10(logMin) + 
                                        iterSliderValue *  (Math.log10(logMax) - Math.log10(logMin)) / iterSliderMax);
            
            update(fractal -> fractal.setMaxIter((int)logValue));
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

    public class color2Listener implements ChangeListener{
        @Override 
        public void stateChanged(ChangeEvent e){
            int red = red2Slider.getValue();
            int green = green2Slider.getValue();
            int blue = blue2Slider.getValue();
            Color newColor  = new Color(red, green, blue);
            update(fractal -> fractal.setColorSecund(newColor));

            
        }
    }

}
