package SnakeTry3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Collections;

public class HighscorePanel extends JPanel implements ActionListener {
    private JLabel highscoreLabel;
    private JLabel firstLabel;
    private JLabel secondLabel;
    private JLabel thirdLabel;
    private JLabel fourthLabel;
    private JLabel fifthLabel;
    private JButton okButton;
    private Highscores highscores;
    private Main main;

    public HighscorePanel(Main main) {
        this.main = main;
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        highscoreLabel = new JLabel("HIGHSCORES");
        highscoreLabel.setFont(new Font("Arial",Font.PLAIN,25));
        highscoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(highscoreLabel);

        showHighscoreList();
        initializeButtons();
    }

    private void initializeButtons(){
        okButton = new JButton("OK");
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        okButton.addActionListener(this);

        this.add(Box.createVerticalGlue());
        this.add(okButton);
        this.add(Box.createRigidArea(new Dimension(0,20)));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            highscores.saveScoresToFile();
            main.switchToButtonPanel();
        }
    }

    private void showHighscoreList() {
        try {
            highscores = new Highscores();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "File is broken. Resetting highscore list.");
            highscores = new Highscores(true);
        } catch (ClassNotFoundException e) {
            System.out.println("class not found");
            highscores = new Highscores(true);
        }
        ArrayList<Highscore> list = highscores.getHighscoreList();
        Collections.sort(list);
        Collections.reverse(list);


        for (Highscore highscore : list){

            System.out.println(highscore.name + "   " + highscore.score);
        }

        showBest5Scores();

    }

    private void showBest5Scores() {
        for (int i=0;i<5;i++){

        }
    }


}
