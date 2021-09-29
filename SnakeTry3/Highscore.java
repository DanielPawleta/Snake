package SnakeTry3;

import java.io.Serializable;

public class Highscore implements Serializable, Comparable<Highscore> {
    String name;
    int score;

    public Highscore(String name, int score) {
        this.name = name;
        this.score = score;
        System.out.println("Added to list: " + name);
    }

    @Override
    public int compareTo(Highscore o) {
        int result = this.score - o.score;
        if (result < 0) return -1;
        else if (result==0) return 0;
        else return 1;
    }
}
