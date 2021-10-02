package SnakeTry3;

import javax.swing.*;
import java.awt.*;

public class MyUpPanel extends JPanel {
    private JLabel speedLabel;
    private JLabel scoreLabel;
    private Main main;
    private int speed;
    private int score;

    //Constructor
    public MyUpPanel() {
        speed=0;
        score=0;
        if (main!=null) speed = main.getSpeed();
        if (main!=null) score = main.getScore();

        this.setLayout(new GridLayout(1,2,10,5));

        speedLabel = new JLabel("Speed: " + speed);
        speedLabel.setFont(new Font("",Font.PLAIN,20));
        speedLabel.setForeground(Color.MAGENTA);
        speedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        speedLabel.setVisible(false);

        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font("",Font.PLAIN,20));
        scoreLabel.setForeground(Color.MAGENTA);
        scoreLabel.setVisible(false);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(speedLabel);
        this.add(scoreLabel);
    }

    //Methods
    private void updateCounters(){
        speed=main.getSpeed();
        speedLabel.setText("Speed: " + speed);

        score=main.getScore();
        scoreLabel.setText("Score: " + score);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (main!=null) updateCounters();
    }

    public void ableUpPanelsVisibility() {
        speedLabel.setVisible(true);
        scoreLabel.setVisible(true);
    }

    public void disableUpPanelsVisibility() {
        speedLabel.setVisible(false);
        scoreLabel.setVisible(false);
    }

    //Getters and Setters
    public void setMain(Main main) {
        this.main = main;
    }
}
