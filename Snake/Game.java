package Snake;

import Snake.View.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;

public class Game implements ActionListener, Serializable {
    private static final long serialVersionUID = 1L;
    private final int width; //game field size horizontally
    private final int height; //game field size vertically
    private final Snake snake;
    private Mouse mouse;
    private final View view;
    private transient Main main;
    private transient KeyboardObserver keyboardObserver;
    private Timer timer;
    private boolean isPaused;
    private int speed=0;
    private int score=0;

    //Constructor
    public Game(int width, int height, Snake snake, Main main, KeyboardObserver keyboardObserver) {
        this.width = width;
        this.height = height;
        this.snake = snake;
        this.view = new View(width, height, snake);
        createMouse();

        this.main = main;
        this.keyboardObserver = keyboardObserver;
    }

    //Methods
    public void run() {
        int delay=300;
        if (speed!=0) delay=delay-speed*10;
        timer = new Timer(delay,this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isPaused) takeStep();
    }

    public void takeStep(){
        if(!snake.isAlive()) {
            timer.stop();
            keyboardObserver.turnOff();
            main.checkHighscore();
            return;
        }
        if (keyboardObserver.hasKeyEvents()) {
            KeyEvent event = keyboardObserver.getEventFromTop();
            if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                snake.setDirection(SnakeDirection.LEFT);
            }
            else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                snake.setDirection(SnakeDirection.RIGHT);
            else if (event.getKeyCode() == KeyEvent.VK_UP)
                snake.setDirection(SnakeDirection.UP);
            else if (event.getKeyCode() == KeyEvent.VK_DOWN)
                snake.setDirection(SnakeDirection.DOWN);
        }
        snake.move();
        print();
    }

    public void eatMouse() {
        createMouse();
    }

    public void createMouse() {
        int x;
        int y;
        while (true){
            x = (int) (Math.random() * width);
            y = (int) (Math.random() * height);
            ArrayList<SnakeSection> sections = snake.getSections();
            if (!sections.contains(new SnakeSection(x,y))) break; //just to check if this field is not occupied by snak section
        }
        mouse = new Mouse(x, y);
        view.setMouse(mouse);
    }

    public void changeDelay() {
        int delayStep = 10;
        if (timer.getDelay()>100){
            timer.setDelay(timer.getDelay()-delayStep);
        }
        main.setSpeed((300-timer.getDelay())/10);
        main.setScore(main.getScore()+snake.getSections().size()* main.getSpeed());
    }

    public void print() {
        main.getFrame().getCenterPanel().repaint();
        main.getFrame().getUpPanel().repaint();
    }

    public  void pause(){
        isPaused=true;
        keyboardObserver.turnOff();
    }

    public void resume() {
        isPaused=false;
        if (keyboardObserver==null) System.out.println("null");
        keyboardObserver.turnOn();
    }

    //Getters and Setters
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSpeed() {
        return speed;
    }

    public int getScore() {
        return score;
    }

    public void setKeyboardObserver(KeyboardObserver keyboardObserver) {
        this.keyboardObserver = keyboardObserver;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public View getView() {
        return view;
    }

    public Snake getSnake() {
        return snake;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}