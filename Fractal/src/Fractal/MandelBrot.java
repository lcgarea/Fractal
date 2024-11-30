package Fractal;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


/**
 * MandelBrotSet:el numero c complejo  pertenece al mandelBrottSet cuando para
 * Lim f(zn) para n-> inf  no tiende a infinito, donde Zn+1 = Zn + c
 * c = cRe + cIm*i 
 * Marthematicamente se ha demostrado que la mayoria de lo numpero de mandel pretenecen al conjunto de
 * 2.5 <= x <= 1  & -1 <= x <= 1;
 * mmatematicamente tambien se ha demostrado que cuando |zn| > 2 entonces zn crece exponecialmente con n
 * y por tanto diverge
 * el fractal de Mandel se obtine entonces difidiendo una imagen en pixel y asociando cada pixel a un numero
 * imaginario c , si c pertenece al mandelbrott set enntoces de pinta de negro y sino dependiendo de la velocidad de divergencia
 * otros colores.
 * Para implementaro en Java :
 * 1. mappin der Bild in pixel und associazion con un numero complejo
 * 2. Ierar por cad pixel
 *      comprobar si pertenece al mandel brott set
 *      assiganr color
 *  Para mapear la imagen, llevar al plano imaginario los pixel se usa:
 * x = xmin + px/ ancho * (xmax - xmin ) ( parte real de c cRe)
 * y = y min + py/altura * (ymx -ymin) (parte imaginaria de c cIm)
 * don de el pinxel p p(px, py)
 * 
 */

public class MandelBrot extends KomplexeBase {

    protected float hueInitial;
    public MandelBrot(){
        this.color = Color.BLACK;
        this.hueInitial = 0.7f;
        this.colorSecund= Color.getHSBColor(0.7f, 0.8f, 1.0f);
    }


    @Override
    public void setColor(Color color){
        super.setColor(color);
        repaint();
    }
    @Override
    public void setColorSecund(Color colorSecund){
        super.setColorSecund(colorSecund);
        repaint();
    }


    private double xmin = -2.5;
    private double xmax = 1;
    private double ymin = -1;
    private double ymax = 1;
    //private Color color = Color.BLACK;
    
   
 
    /**
     * Methode zum überprüfen, ob c gehört zum MandelBrott set
     * @param cRe Realteil c
     * @param cIm Imaginäreteil c
     * @return Anzahl der Iterationen
     */
    private  int isAMandelBrottSet( double cRe, double cIm){
         
        int iter = 0; // Anzahl der iterattionen
        double zRe = 0; // z = zRe + zIm + i
        double zIm = 0; 
        double zModQuad = 0; // modulo de z al cuadrado
        while( iter < maxIter && zModQuad <= 4){
            double temp = zRe;
            zRe = zRe * zRe - zIm * zIm + cRe;
            zIm = 2 * temp * zIm + cIm;
            zModQuad = zRe * zRe  + zIm * zIm;
            iter++; 
        
        }
       
        return iter;
    }
    private Color getFarbe(int iter) {
        if (iter == maxIter) {
            return this.color; // MandelBrottSet Black
        }
    
       //From slider die farben, der Punte die nicht zu mandelSet gehorz
        int red = colorSecund.getRed();
        int green = colorSecund.getGreen();
        int blue = colorSecund.getBlue();
    
        
        float[] hsb = Color.RGBtoHSB(red, green, blue, null);
        float hueInitial = hsb[0];
        float saturationInitial = hsb[1];
        float brightnessInitial = hsb[2];
    
        // Ajustar hue dinámicamente
        float hue = (hueInitial + (float) iter / maxIter * 0.7f) % 1.0f;
    
        // Ajustar saturación y brillo para permitir el blanco
        float saturation = Math.max(0.0f, saturationInitial - (float) iter / maxIter * 0.8f);
        float brightness = Math.min(1.0f, brightnessInitial + (float) iter / maxIter * 0.5f);
    
        // Withe 
        if (red == 255 && green == 255 && blue == 255) {
            saturation = 0.0f; // Blanco puro no tiene saturación
            brightness = 1.0f; // Blanco puro tiene brillo máximo
        }
    
        // Retornar el color ajustado
        return Color.getHSBColor(hue, saturation, brightness);
    }

  
    private void setupTransform(Graphics2D g){
        int width = getWidth();
        int height = getHeight();

        g.translate(width/2, height/2);
        double scale = Math.min(width, height)/4.0;
        g.scale(scale, scale);
    }

    
    @Override
    protected void paintComponent(Graphics g)  {

    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    //setupTransform(g2);
    int breite = getWidth();
    int hoehe = getHeight();
    BufferedImage image = new BufferedImage(breite, hoehe, BufferedImage.TYPE_INT_RGB);
    for (int px = 0; px < breite; px++) {
        for (int py = 0; py < hoehe; py++) {
            double cRe = xmin + ((double) px / (breite - 1)) * (xmax - xmin);
            double cIm = ymin + ((double) py / (hoehe - 1)) * (ymax - ymin);
            int iter = isAMandelBrottSet(cRe, cIm);
            image.setRGB(px, py, getFarbe(iter).getRGB());
        }
    }
    g.drawImage(image, 0, 0, null);
    }

   /*  public static void main(String[] args) {
        JFrame frame = new JFrame("Mandelbrot");
        MandelBrot mandelBrott = new MandelBrot();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.add(mandelBrott);
        frame.setVisible(true);
    } */
    

}
