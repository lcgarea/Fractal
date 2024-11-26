import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class FractalParameterPanelHelper {
    private JSlider tiefSlider;
   
    public FractalParameterPanelHelper(ConfigurableFractal fractal) {
        // Initialisiere Slider für "Level"
        tiefSlider = new JSlider(JSlider.VERTICAL, 0, 12, 1);
        tiefSlider.setMajorTickSpacing(2);
        tiefSlider.setMinorTickSpacing(1);
        tiefSlider.setPaintTicks(true);
        tiefSlider.setPaintLabels(true);

        tiefSlider.addChangeListener(e -> fractal.applyParameters());
    }

    public JSlider getTiefSlider() {
        return tiefSlider;
    }

    public JPanel createBaseConfigPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Level:");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);

        panel.add(tiefSlider);

        return panel;
    }

}
