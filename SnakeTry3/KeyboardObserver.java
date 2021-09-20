package SnakeTry3;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class KeyboardObserver implements KeyListener{
    private Queue<KeyEvent> keyEvents = new ArrayBlockingQueue<>(100);
    private Container container;
    private boolean active;

    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("klawisz");
        if (active) keyEvents.add(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {}

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
}