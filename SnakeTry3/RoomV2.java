package SnakeTry3;



import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class RoomV2 implements ActionListener {
    private int width; //wielkosc pola rozgrywki w poziomie
    private int height; //wielkosc pola rozgrywki w pionie
    private Snake snake;
    private Mouse mouse;
    private View view;
    private Main main;
    private KeyboardObserver keyboardObserver;
    private Timer timer;
    private boolean isPaused;


    public RoomV2(int width, int height, Snake snake, Main main, KeyboardObserver keyboardObserver) {
        this.width = width;
        this.height = height;
        this.snake = snake;
        //this.mouse = mouse;
        this.view = new View(width, height, snake);
        createMouse();

        this.main = main;
        this.keyboardObserver = keyboardObserver;
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

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    public void run() {
        timer = new Timer(500,this);
        timer.start();
    }

    public  void pause(){
        isPaused=true;
        keyboardObserver.turnOff();
    }


    public void resume() {
        isPaused=false;
        keyboardObserver.turnOn();
    }

    public void print() {
        main.getFrame().getCenterPanel().repaint();
        main.getFrame().getUpPanel().repaint();
    }

    public void eatMouse() {
        createMouse();
    }

    public void createMouse() {
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);
        mouse = new Mouse(x, y);
        view.setMouse(mouse);
    }

    public void changeDelay() {
        int delayStep = 20;
        if (timer.getDelay()>200){
            timer.setDelay(timer.getDelay()-delayStep);
        }
        main.setSpeed((500-timer.getDelay())/20);
        main.setScore(main.getScore()+snake.getSections().size()* main.getSpeed());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isPaused) takeStep();
    }

    public void takeStep(){
        if(!snake.isAlive()) {
            timer.stop();
            keyboardObserver.turnOff();
            main.switchToButtonPanel();
            return;
        }

        if (keyboardObserver.hasKeyEvents()) {
            KeyEvent event = keyboardObserver.getEventFromTop();

            //if (event.getKeyChar() == 'q') return;
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
}