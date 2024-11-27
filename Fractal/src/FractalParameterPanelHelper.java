import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class FractalParameterPanelHelper {
    private JSlider tiefSlider;
    private JSlider colorSlider;
   
    public FractalParameterPanelHelper(ConfigurableFractal fractal) {
        // Initialisiere Slider für "Level"
        //tiefSlider = new JSlider(JSlider.VERTICAL, 0, 12, 1);
        tiefSlider = new JSlider(JSlider.HORIZONTAL, 0, 12, 1);
        tiefSlider.setMajorTickSpacing(2);
        tiefSlider.setMinorTickSpacing(1);
        tiefSlider.setPaintTicks(true);
        tiefSlider.setPaintLabels(true);
        tiefSlider.addChangeListener(e -> fractal.applyParameters());

        colorSlider = new JSlider(JSlider.HORIZONTAL,0, 255, 50);
        colorSlider.setMajorTickSpacing(50);
        colorSlider.setMinorTickSpacing(10);
        colorSlider.setPaintTicks(true);
        colorSlider.setPaintLabels(true);
        colorSlider.addChangeListener(e ->fractal.applyParameters());
    }

    public JSlider getTiefSlider() {
        return tiefSlider;
    }

    public JSlider getColoSlider(){
        return colorSlider;
    }

    public JPanel createBaseConfigPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel labelLevel = new JLabel("Level:");
        labelLevel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(labelLevel);
        panel.add(tiefSlider);


        JLabel labelColor = new JLabel("Color:");
        labelLevel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(labelColor);
        panel.add(colorSlider);

        return panel;
    }

}
