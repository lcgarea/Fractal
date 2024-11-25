import javax.swing.*    ;

import java.awt.BorderLayout;
import java.awt.event.*;
public class testGui implements ActionListener { 
    private JButton button;
    private JFrame frame;
    public static void main(String[] args) {
        testGui gui = new testGui();
        gui.go();
    }
    public void go(){ 
        JFrame frame = new JFrame();
        button = new JButton("Change Color");
        button.addActionListener(this);
        MyDrawPanel drawPanel = new MyDrawPanel();

        frame.getContentPane().add(BorderLayout.SOUTH,button);
        frame.add(BorderLayout.CENTER,drawPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        frame.setVisible(true);
        

    }      
    
    public void actionPerformed(ActionEvent event){
        frame.repaint();
    }

       

        
  
        
    

}
