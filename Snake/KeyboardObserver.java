package Snake;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class KeyboardObserver implements KeyListener{
    private final Queue<KeyEvent> keyEvents = new ArrayBlockingQueue<>(1000);
    private Container container;
    private boolean active;

    //Methods
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (active) keyEvents.add(e);
        turnOff();  //turn off keyboard to prevent from reading
                    // one long keypress as many single press
    }

    @Override
    public void keyReleased(KeyEvent e) {
        turnOn();
    }

    public void run() {
        active=true;
        container.addKeyListener(this);
    }

    public boolean hasKeyEvents() {
        return !keyEvents.isEmpty();
    }

    public KeyEvent getEventFromTop() {
        return keyEvents.poll();
    }

    public void turnOff(){
        active=false;
    }

    public void turnOn(){
        active=true;
    }

    //Getters and Setters
    public void setContainer(Container container) {
        this.container = container;
    }
}