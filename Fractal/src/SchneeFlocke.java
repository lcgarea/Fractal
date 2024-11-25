import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Frame;
/**
 * Die Koch-Scheeflocke entssteht, indem eine Linier Rekursive in vier Teile
 * geteil wird:
 * <ul> 
 * <li>Der mittlere Teil der Linier wir durch ein gleicseitiges Dreieck ersetzt.</li>
 * <li> Der ursprüngliche mittlere Teil wird entfernt. </li>
 * <li>  Dies wird wiederholt, bis die gewünschte Rekursionstiefe erreicht ist </li>
 * </ul>
 */
public class SchneeFlocke  extends Canvas{
    private int tief = 9;
    
    public void setTief(int tief){
        this.tief = tief;
    }

    private void drawKoch(Graphics g, int tief, int p1X, int p1Y, int p2X, int p2Y ){
        if( tief == 0){
            g.drawLine(p1X, p1Y, p2X, p2Y);
        }
        else{
            int deltaX = p2X -p1X;
            int deltaY = p2Y - p2Y;

            //teile die Linie
            int pAX = p1X + deltaX/3;
            int pAY = p1Y + deltaY/3;

            int pBX = p2X - deltaX/3;
            int pBY = p2Y - deltaY/3;

            double winkel = Math.PI / 3;
            int xPeak = (int) (pAX + Math.cos(winkel)*(pBX - pAX) - Math.sin(winkel)*(pBY - pAY));
            int yPeak = (int) (pAY + Math.sin(winkel)*(pBX - pBX) - Math.cos(winkel)*(pBY - pAY));

            //rekursive Aufrufe für die vier neuen Segmmente

            drawKoch(g, tief -1, p1X, p1Y, pAX, pAY);
            drawKoch(g, tief - 1, pAX, pAY, xPeak, yPeak);
            drawKoch(g, tief - 1, xPeak, yPeak, pBX, pBY);
            drawKoch(g, tief - 1, pBX, pBY, p2X, p2Y);
        }
    }


}
