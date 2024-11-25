import javax.swing.*    ;

import java.awt.BorderLayout;
import java.awt.event.*;
public class testGui { 
   
    private JFrame frame;
    private JLabel label;

    public static void main(String[] args) {
        testGui gui = new testGui();
        gui.go();
    }
    public void go(){ 
       
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton buttonLabel = new JButton();
        buttonLabel.addActionListener(new LabelListener() );

        JButton button = new JButton("Change Color");
        button.addActionListener(new ColorListener());
        label = new JLabel("dsdsddsd");



        MyDrawPanel drawPanel = new MyDrawPanel();

        frame.getContentPane().add(BorderLayout.SOUTH, button);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.getContentPane().add(BorderLayout.EAST, buttonLabel);
        frame.getContentPane().add(BorderLayout.WEST, label);
        frame.setSize(300,300);
        frame.setVisible(true);
        
    }      


    public class LabelListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            label.setText("eso");
        }       
    }

    public class ColorListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            frame.repaint();
        }
    }

       

        
  
        
    

}
