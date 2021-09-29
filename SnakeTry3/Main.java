package SnakeTry3;


import swingTest.JTextFieldTest;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    private View view;  //JPanel with game area
    private MyFrame frame; //JFrame
    private CardLayout cardLayout;
    private ButtonPanel buttonPanel; //JPanel
    private AboutPanel aboutPanel; //JPanel
    private HighscorePanel highscorePanel; //JPanel
    private RoomV2 game;
    private KeyboardObserver keyboardObserver;
    private boolean isPaused;
    //private boolean downPanelInitialized = false;
    private int width=20;
    private int height=20;
    private int speed=0;
    private int score=0;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public KeyboardObserver getKeyboardObserver() {
        return keyboardObserver;
    }

    public MyFrame getFrame() {
        return frame;
    }

    public void setView(View view) {
        this.view = view;
    }

    public static void main(String[] args) {
         Main main = new Main();
         main.run();
    }

    public void run() {
        frame = new MyFrame(); //main frame with JPanels to switch between
        cardLayout = new CardLayout();
        frame.getCenterPanel().setLayout(cardLayout);

        buttonPanel = new ButtonPanel(this);
        buttonPanel.setFocusable(false);
        frame.getCenterPanel().add(buttonPanel,"ButtonPanel");

        frame.setFocusable(true);
        frame.setSize(new Dimension(500,400));
        frame.setResizable(true);
        frame.setVisible(true);
    }

    private boolean getGameScreenSize(){
        JTextField widthFiled = new JTextField("20",5);
        JTextField heightFiled = new JTextField("20",5);

        while (true) {
            JPanel jPanel = new JPanel();
            jPanel.add(new JLabel("Width: "));
            jPanel.add(widthFiled);
            jPanel.add(Box.createHorizontalStrut(20));
            jPanel.add(new JLabel("Height: "));
            jPanel.add(heightFiled);

            int result = JOptionPane.showConfirmDialog(buttonPanel,jPanel,"Please enter game screen size", JOptionPane.OK_CANCEL_OPTION);

            if (result== JOptionPane.CANCEL_OPTION) {
                switchToButtonPanel();
                return false;
            }
            else {
                try {
                    width = Integer.parseInt(widthFiled.getText());
                    height = Integer.parseInt(heightFiled.getText());
                    if (width>50 || height>50) throw new SizeTooBigException();
                    if (width<15 || height<15) throw new SizeTooSmallException();
                    break;
                }
                catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(jPanel,"Please enter only numbers","Warning", JOptionPane.WARNING_MESSAGE);
                } catch (SizeTooBigException e) {
                    JOptionPane.showMessageDialog(jPanel,"Size is too big, please enter value equal or less than 50!","Warning", JOptionPane.WARNING_MESSAGE);
                } catch (SizeTooSmallException e) {
                    JOptionPane.showMessageDialog(jPanel,"Size is too small, please enter value equal or greater than 15!","Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        return true;
    }

    public void switchToGamePanel() {
        if (keyboardObserver==null) {
            keyboardObserver = new KeyboardObserver();
            keyboardObserver.setContainer(frame);
            keyboardObserver.run();
        }

        keyboardObserver.turnOn();

        if (!getGameScreenSize()) return;

        createGame(width,height);
        frame.getCenterPanel().add(game.getView(),"GamePanel");

        frame.setMain(this);

        cardLayout.show(frame.getCenterPanel(),"GamePanel");
        frame.ableUpAndDownButtonsVisibility();

        frame.pack();
        game.run();

    }

    public void showScore(){
        System.out.println("score is: " + score);



    }

    public void createGame(int width, int height){
        Snake snake = new Snake(10,10);
        game = new RoomV2(width,height, snake, this, keyboardObserver);
        this.setView(game.getView());
        snake.setGame(game);
        game.getSnake().setDirection(SnakeDirection.DOWN);
    }

    public void switchToButtonPanel() {
        //showScore();
        zeroCounters();
        frame.disableUpAndDownButtonsVisibility();
        frame.setSize(new Dimension(500,400));
        cardLayout.show(frame.getCenterPanel(),"ButtonPanel");
    }

    public void switchToAboutPanel() {
        aboutPanel = new AboutPanel(this);
        frame.getCenterPanel().add(aboutPanel,"AboutPanel");
        cardLayout.show(frame.getCenterPanel(),"AboutPanel");
    }

    public void switchToHighscorePanel() {
        frame.disableUpAndDownButtonsVisibility();
        highscorePanel = HighscorePanel.getHighscorePanelInstance(this);
        frame.getCenterPanel().add(highscorePanel,"HighscorePanel");
        cardLayout.show(frame.getCenterPanel(),"HighscorePanel");
    }

    private void initializeHighscorePanel(){

    }

    public void pause(){
        if (!isPaused) {
            isPaused = true;
            game.pause();
        }
        else {
            isPaused=false;
            game.resume();
        }
    }

    public void resume(){
        isPaused=false;
        game.resume();
    }

    private void zeroCounters() {
        speed = 0;
        score = 0;
    }

    public void killSnake() {
        game.getSnake().kill();
    }

    public void checkHighscore() {
        highscorePanel = HighscorePanel.getHighscorePanelInstance(this);
        ArrayList<Highscore> highscoreList = highscorePanel.getHighscores().getHighscoreList();
        Collections.sort(highscoreList);
        Collections.reverse(highscoreList);
        if (score > highscoreList.get(4).score){
            String name = getPlayerName();
            if (name!=null){
                highscoreList.add(new Highscore(name,score));
                switchToHighscorePanel();
            }
            System.out.println("yes");
        }
        else {
            switchToButtonPanel();
            System.out.println("no");
        }
    }

    private String getPlayerName(){
        String name;
        JTextField nameFiled = new JTextField("Name",5);

        while (true) {
            JPanel jPanel = new JPanel();
            jPanel.add(new JLabel("Your score is worth saving! Please enter your name: "));
            jPanel.add(nameFiled);
            jPanel.add(Box.createHorizontalStrut(20));

            int result = JOptionPane.showConfirmDialog(buttonPanel,jPanel,"Congratulations!", JOptionPane.OK_CANCEL_OPTION);

            if (result== JOptionPane.CANCEL_OPTION) {
                switchToButtonPanel();
                return null;
            }
            else {
                try {
                    name = nameFiled.getText();

                    if (name.length()>25) throw new NameTooLongException();
                    if (!name.matches("[a-zA-Z]")) throw new IllegalCharacterException();
                    break;
                }
                catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(jPanel,"Please enter only numbers","Warning", JOptionPane.WARNING_MESSAGE);
                } catch (NameTooLongException e) {
                    JOptionPane.showMessageDialog(jPanel,"Name is too long, please enter shorter word!","Warning", JOptionPane.WARNING_MESSAGE);
                } catch (IllegalCharacterException e) {
                    JOptionPane.showMessageDialog(jPanel,"Please enter onyl letters","Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        return name;
    }
}