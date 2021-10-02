package SnakeTry3;

import java.io.Serializable;

public class Highscore implements Serializable, Comparable<Highscore> {
    private final String name;
    private final int score;

    //Constructor
    public Highscore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    //Methods
    @Override
    public int compareTo(Highscore o) {
        int result = this.score - o.score;
        if (result < 0) return -1;
        else if (result==0) return 0;
        else return 1;
    }

    //Getters and Setters
    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
