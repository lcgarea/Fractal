
import javax.swing.JPanel;
public enum FractalType {
    SQUARE("Square Fractal", new SquareFractal()),
    SIERPINSKi("Sierpinski Triangle", new SierpinskiDreieck()),
    SCHNEEFLOCKE("Scheenflocke", new SchneeFlocke()),
    PYTHAGORASBAUM("Pythagorasbaum", new PythagorasBaum());

    private final String name;
    private final JPanel fractalPanel;
    FractalType(String name, JPanel fractalPanel){
        this.name = name;
        this.fractalPanel = fractalPanel;

    }

    public String getName(){
        return this.name;
    }
    public JPanel getFractalPanel(){
        return this.fractalPanel;
    }

    @Override
    public String toString(){
        return name;
    }


}
