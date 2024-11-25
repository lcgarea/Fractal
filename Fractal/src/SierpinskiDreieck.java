import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Frame;
/**
 * Teilt man das Dreieck in vier zueinander kongruente und zum Ausgangsdreieck 
 * ähnliche Dreiecke, deren Eckpunkte die Seitenmittelpunkte des Ausgangsdreiecks 
 * sind, dann sind die Teilmengen des Fraktals in den drei äußeren Dreiecken
 *  skalierte Kopien des gesamten Fraktals, während das mittlere Teildreieck 
 * nicht zum Fraktal gehört.
 */


public class SierpinskiDreieck extends Canvas{
    private int tief = 9;
    
    public void setTief(int tief){
       this.tief =tief;

    }

    private void drawSpierpinski ( Graphics g, int tief, int p1X, int p1Y,  int p2X, int p2Y,  int p3X, int p3Y ){
        g.drawLine(p1X, p1Y, p2X, p2Y);
        g.drawLine(p2X, p2Y, p3X, p3Y);
        g.drawLine(p3X, p3Y, p1X, p1Y);
        if (tief > 0){
            // mittelPunkt von der Linien
            int mp1X = (p1X + p2X) / 2;
            int mp1Y = (p1Y + p2Y) / 2;
            int mp2X = (p2X + p3X) / 2;
            int mp2Y = (p2Y + p3Y) / 2;
            int mp3X = (p3X + p1X) / 2;
            int mp3Y = (p3Y + p1Y) / 2;
            drawSpierpinski(g, tief - 1, p1X, p1Y, mp1X, mp1Y, mp3X, mp3Y);
            drawSpierpinski(g, tief -1, p2X, p2Y, mp1X, mp1Y, mp2X, mp2Y);
            drawSpierpinski(g, tief - 1, p3X, p3Y, mp2X, mp2Y, mp3X, mp3Y);
        }

    }

    public void paint(Graphics g){
        drawSpierpinski(g, tief, 10, 10, 390, 10, 200, 390);
    }

    /*public static void main(String[] args) {
        // Erstelle das Frame
        Frame frame = new Frame("Sierpinski Dreieck");
        SierpinskiDreieck canvas = new SierpinskiDreieck();

        // Füge das Canvas hinzu
        frame.add(canvas);
        frame.setSize(400, 400);
        frame.setVisible(true);

        // Beende das Programm beim Schließen des Fensters
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });
    }*/


}
