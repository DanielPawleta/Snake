package SnakeTry3;

import java.io.Serializable;

public class SnakeSection implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int x; // x - coordinate of this section in width
    private final int y; // y - coordinate of this section in height

    //Constructor
    public SnakeSection(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //Methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SnakeSection that = (SnakeSection) o;

        if (x != that.x) return false;
        if (y != that.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    //Getters and Setters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
