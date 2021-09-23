package SnakeTry3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
        if (e.getSource() == okButton) main.switchToButtonPanel();
    }

    private void showHighscoreList(){
        highscores = new Highscores();
        ArrayList<Highscores.Highscore> list = highscores.getHighscoreList();





    }








}
