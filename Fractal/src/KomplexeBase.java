import java.awt.Color;

public class KomplexeBase extends FraktalBaseV0{
    protected int maxIter = 1000;
    protected Color colorSecund ;

    public void setMaxIter( int iter){
        this.maxIter = iter;
    }

    public int getMaxIter(){
        return this.maxIter;
    }

    public void setColorSecund(Color colorSecund){
        this.colorSecund = colorSecund;
     }

    public Color getColorSecund() {
        return this.colorSecund;
    }
   

}
