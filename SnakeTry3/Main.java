package SnakeTry3;

import SnakeTry3.Exceptions.IllegalCharacterException;
import SnakeTry3.Exceptions.NameTooLongException;
import SnakeTry3.Exceptions.SizeTooBigException;
import SnakeTry3.Exceptions.SizeTooSmallException;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    private View view;  //JPanel with game area
    private MyFrame frame; //JFrame
    private CardLayout cardLayout;
    private ButtonPanel buttonPanel; //JPanel
    private AboutPanel aboutPanel; //JPanel
    private HighscorePanel highscorePanel; //JPanel
    private Game game;
    private KeyboardObserver keyboardObserver;
    private boolean isPaused;
    private int width=20;
    private int height=20;
    private int speed=0;
    private int score=0;

    //Methods
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

    public void createGame(int width, int height){
        Snake snake = new Snake(10,10);
        game = new Game(width,height, snake, this, keyboardObserver);
        this.setView(game.getView());
        snake.setGame(game);
        game.getSnake().setDirection(SnakeDirection.DOWN);
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

        if (!getGameScreenSize()) return; //when user clicked cancel button

        createGame(width,height);

        frame.getCenterPanel().add(game.getView(),"GamePanel");
        frame.setMain(this);

        cardLayout.show(frame.getCenterPanel(),"GamePanel");
        frame.ableUpAndDownButtonsVisibility();
        frame.pack();
        game.run();
    }

    public void switchToGamePanel(Game game) {
        this.game = game;
        this.speed = game.getSpeed();
        this.score = game.getScore();
        keyboardObserver = new KeyboardObserver();
        keyboardObserver.setContainer(frame);
        keyboardObserver.run();
        this.game.setKeyboardObserver(keyboardObserver);
        this.game.setMain(this);
        this.setView(game.getView());

        frame.getCenterPanel().add(game.getView(), "GamePanel");
        frame.setMain(this);

        cardLayout.show(frame.getCenterPanel(), "GamePanel");
        frame.ableUpAndDownButtonsVisibility();
        frame.pack();
        this.game.run();
        isPaused = true;
        frame.changePauseButtonText();
    }

    public void switchToButtonPanel() {
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
        highscorePanel.resetBest5Scores();
        frame.getCenterPanel().add(highscorePanel,"HighscorePanel");
        highscorePanel.repaint();
        cardLayout.show(frame.getCenterPanel(),"HighscorePanel");
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
        if (score > highscoreList.get(4).getScore()){
            String name = getPlayerName();
            if (name!=null){
                highscoreList.add(new Highscore(name,score));
                highscorePanel.getHighscores().saveScoresToFile();
                switchToHighscorePanel();
            }
        }
        else {
            switchToButtonPanel();
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
                    if (!name.matches("[a-zA-Z]+")) throw new IllegalCharacterException();
                    break;
                }
                catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(jPanel,"Please enter only numbers","Warning", JOptionPane.WARNING_MESSAGE);
                } catch (NameTooLongException e) {
                    JOptionPane.showMessageDialog(jPanel,"Name is too long, please enter shorter word!","Warning", JOptionPane.WARNING_MESSAGE);
                } catch (IllegalCharacterException e) {
                    JOptionPane.showMessageDialog(jPanel,"Please enter only letters","Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        return name;
    }

    public void saveGame() {
        File saveFile;
        JFileChooser jFileChooser = new JFileChooser("D:\\Moje\\Java\\Snake\\");

        if (jFileChooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
            saveFile = jFileChooser.getSelectedFile();
            if (!saveFile.getName().endsWith(".txt")) {
                String saveFileString = saveFile + ".txt";
                saveFile = new File(saveFileString);
            }
        }
        else return;

        Path savePath = saveFile.toPath();
        if (Files.notExists(savePath)) {
            try {
                Files.createDirectories(savePath.getParent());
                Files.createFile(savePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try  (FileOutputStream fileOutputStream =
                      new FileOutputStream(saveFile);
              ObjectOutputStream objectOutputStream =
                      new ObjectOutputStream(fileOutputStream)
        )
        {
            game.setScore(score);
            game.setSpeed(speed);
            objectOutputStream.writeObject(game);
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGame() {
        File loadFile;
        JFileChooser jFileChooser = new JFileChooser("D:\\Moje\\Java\\Snake\\");

        while (true) {
            if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                loadFile = jFileChooser.getSelectedFile();
                if (loadFile.getName().endsWith(".txt")) {
                    break;
                }
                else {
                    JOptionPane.showMessageDialog(jFileChooser,"Please select only txt file","Warning", JOptionPane.WARNING_MESSAGE);
                }
            } else return;
        }

        try  (FileInputStream fileInputStream =
                      new FileInputStream(loadFile);
              ObjectInputStream objectInputStream =
                      new ObjectInputStream(fileInputStream)
        )
        {
            game = (Game) objectInputStream.readObject();
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Incompatible file", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        switchToGamePanel(game);
    }

    //Getters and Setters
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

    public MyFrame getFrame() {
        return frame;
    }

    public void setView(View view) {
        this.view = view;
    }
}