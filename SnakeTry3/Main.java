package SnakeTry3;


import java.awt.*;

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
    private boolean downPanelInitialized = false;
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

    public void switchToGamePanel() {
        if (keyboardObserver==null) {
            keyboardObserver = new KeyboardObserver();
            keyboardObserver.setContainer(frame);
            keyboardObserver.run();
        }

        keyboardObserver.turnOn();

        createGame(25,20);
        frame.getCenterPanel().add(game.getView(),"GamePanel");

        if (!downPanelInitialized){
            downPanelInitialized=true;
        }

        frame.setMain(this);

        cardLayout.show(frame.getCenterPanel(),"GamePanel");
        frame.ableUpAndDownButtonsVisibility();

        frame.pack();
        game.run();
    }

    public void createGame(int width, int height){
        //Mouse mouse = new Mouse(5,5);
        Snake snake = new Snake(10,10);
        game = new RoomV2(width,height, snake, this, keyboardObserver);
        this.setView(game.getView());
        snake.setGame(game);

        game.getSnake().setDirection(SnakeDirection.DOWN);
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
        highscorePanel = new HighscorePanel(this);
        frame.getCenterPanel().add(highscorePanel,"HighscorePanel");
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


}