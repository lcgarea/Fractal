package Fractal;


import java.awt.Graphics;
import java.awt.Graphics2D;

public class SquareFractal extends KonstruktiveBase {

    public SquareFractal(){
        super();
    }


    private void drawSquare(Graphics g, int tief, int startX, int startY, int side, int delta ){
        
        if (side <= 2 || tief <= 0) return;
        g.drawRect(startX, startY, side, side);
        int newStartX = startX + delta;
        int newStartY = startY + delta;
        drawSquare(g, tief -1, newStartX, newStartY, side -2*delta, delta);
        
        
    }
    protected void setupTransform(Graphics2D g) {

        int width = getWidth();
        int height = getHeight();
    
        // Transformation: Zentrieren und skalieren
        g.translate(width / 2, height / 2); // Zentriert den Ursprung
        double scale = Math.min(width, height) / 400.0; // Einheitliche Skalierung
        g.scale(scale, scale);
    }
    @Override
    public void paintComponent (Graphics g){
        super.paintComponent(g);    
        Graphics2D g2 = (Graphics2D)g;
        setupTransform(g2);
        g2.setColor(color);
        drawSquare(g2, tief, -150, -150, 300, 10);
    }

}
