import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.*;

public class FractalGui {
    private JFrame frame;
    private JButton buttonTief;
    private JLabel menuFractalType;
    private JTextField tiefText;
    private SquareFractal square;


    public static void main(String[] args) {
        FractalGui gui = new FractalGui();
        gui.go();
        
    }

    public void go(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        tiefText = new JTextField(10);
        
        JButton buttonPaint = new JButton("Generate");
        buttonPaint.addActionListener(new TiefListener());

        //JButton menuFractalTypButton  = new JButton("Fractal type");
        //menuFractalTypButton.addActionListener(new FractalListener());

        square = new SquareFractal();
        JPanel linkPanel = new JPanel();
        linkPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        linkPanel.add(new Label("Level"));
        linkPanel.add(tiefText);
       // linkPanel.add(buttonTief);
        linkPanel.add(buttonPaint);

        //frame.getContentPane().add(BorderLayout.SOUTH, buttonTief);
        frame.getContentPane().add(BorderLayout.EAST, linkPanel);
        frame.getContentPane().add(BorderLayout.CENTER, square);
        frame.setSize(800,800);
        frame.setVisible(true);
    }
    public class TiefListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            String text = tiefText.getText();
            int tief = Integer.parseInt(text);
            square.setTief(tief);
            square.repaint();;
        }
    }
    public class PaintLiestener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            frame.repaint();
        }
    }
    public class FractalListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            menuFractalType.setText("Square");
        }
    }


}
