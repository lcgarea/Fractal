
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;



public class SquareFractal extends FractalBase implements ConfigurableFractal{
    private FractalParameterPanelHelper parameterHelper;
    public SquareFractal() {
        parameterHelper = new FractalParameterPanelHelper(this);
        this.tief = parameterHelper.getTiefSlider().getValue();
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
        
    }



    private void drawSquare(Graphics g, int tief, int startX, int startY, int side, int delta ){
        
        if (side <= 2 || tief <= 0) return;
        g.drawRect(startX, startY, side, side);
        int newStartX = startX + delta;
        int newStartY = startY + delta;
        drawSquare(g, tief -1, newStartX, newStartY, side -2*delta, delta);
        
        
    }
    
    public void paint(Graphics g){
        super.paintComponent(g);    
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(color);
        drawSquare(g2, tief, 20, 20, 300, 10);
    }

}
