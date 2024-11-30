package Fractal;


public abstract class KonstruktiveBase extends FraktalBase{
  
    protected int tief;

    public KonstruktiveBase(){
        //super();
        this.tief = 1;
    }


 
     public void setTief(int tief){
        this.tief = tief;
    }

    public int getTief(){
        return this.tief;
    }
  


}
