package SnakeTry3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel implements ActionListener {
    private JLabel snakeLabel;
    private JButton newGameButton;
    private JButton loadGameButton;
    private JButton highscoreButton;
    private JButton aboutButton;
    private JButton exitButton;
    private Main main;

    //Constructor
    public ButtonPanel(Main main) {
        this.main = main;
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        snakeLabel = new JLabel("SNAKE");
        snakeLabel.setFont(new Font("Arial",Font.PLAIN,25));
        snakeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(snakeLabel);

        initializeButtons();
    }

    //Methods
    private void initializeButtons(){
        newGameButton = new JButton("New Game");
        loadGameButton =new JButton("Load Game");
        highscoreButton =new JButton("Highscore");
        aboutButton =new JButton("About");
        exitButton =new JButton("Exit");

        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        highscoreButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        newGameButton.addActionListener(this);
        loadGameButton.addActionListener(this);
        highscoreButton.addActionListener(this);
        aboutButton.addActionListener(this);
        exitButton.addActionListener(this);

        newGameButton.setFocusable(false);
        loadGameButton.setFocusable(false);
        highscoreButton.setFocusable(false);
        aboutButton.setFocusable(false);
        exitButton.setFocusable(false);

        this.add(Box.createVerticalGlue());
        this.add(newGameButton);
        this.add(Box.createVerticalGlue());
        this.add(loadGameButton);
        this.add(Box.createVerticalGlue());
        this.add(highscoreButton);
        this.add(Box.createVerticalGlue());
        this.add(aboutButton);
        this.add(Box.createVerticalGlue());
        this.add(exitButton);
        this.add(Box.createVerticalGlue());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==newGameButton) main.switchToGamePanel();
        if (e.getSource()==loadGameButton) main.loadGame();
        if (e.getSource()==aboutButton) main.switchToAboutPanel();
        if (e.getSource()==highscoreButton) main.switchToHighscorePanel();
        if (e.getSource()==exitButton) System.exit(0);
    }
}
