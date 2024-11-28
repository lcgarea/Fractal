import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class StartFenster {
    private static  final int FRAME_WIDTH = 900;
    private static final int FRAME_HEIGT = 700;
    public static void main(String[] args) {
        
        // Hauptfenster 
        JFrame startFrame = new JFrame();
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setSize(FRAME_WIDTH, FRAME_HEIGT);
        startFrame.setLayout(new BorderLayout());

        //Panel für Wilkomen
        JPanel willkomemPanel = new JPanel();
        willkomemPanel.setBackground(Color.lightGray);
        willkomemPanel.setLayout(new BorderLayout());
        willkomemPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel willkommenLabel = new JLabel("Fraktale ", SwingConstants.CENTER);
        willkommenLabel.setFont(new Font("Arial", Font.BOLD, 24));
        willkomemPanel.add(willkommenLabel);

        //Panel für Button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20,10));
        buttonPanel.add(Box.createVerticalGlue());
       
      

        //buttons zum fraktale fenster
        JButton kosntrButton = new JButton("Konstruktive Fraktale");
        buttonPanel.add(kosntrButton);
        kosntrButton.addActionListener(e->KonstruktiveFraktal.main(args));
        buttonPanel.add(Box.createVerticalStrut(20));

        JButton komplexButton = new JButton("Komplexe Fraktale");
        buttonPanel.add(komplexButton);
        komplexButton.addActionListener(e->KomplexeFraktale.main(args));
        buttonPanel.add(Box.createVerticalGlue());
        //add Acction liesterne

        startFrame.add(willkomemPanel, BorderLayout.CENTER);
        startFrame.add(buttonPanel, BorderLayout.EAST);

        startFrame.setVisible(true);





    }

}
