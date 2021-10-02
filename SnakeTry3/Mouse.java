package SnakeTry3;


import java.io.Serializable;

public class Mouse implements Serializable {
    private final int x;
    private final int y;

    //Constructor
    public Mouse(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //Getters and Setters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
