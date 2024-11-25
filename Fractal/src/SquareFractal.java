import java.awt.Canvas;
import java.awt.Graphics;

public class SquareFractal extends Canvas {
    private int tief = 0;
    public void setTief(int tief){
        this.tief = tief;
    }


    private void drawSquare(Graphics g, int tief, int startX, int startY, int side, int delta ){
        g.drawRect(startX, startY, side, side);
        if( tief >0 ){
        int newStartX = startX + delta;
        int newStartY = startY + delta;
        drawSquare(g, tief -1, newStartX, newStartY, side -2*delta, delta);
        }
        
    }
    public void paint(Graphics g){
        drawSquare(g, tief, 20, 20, 300, 10);
    }

}
