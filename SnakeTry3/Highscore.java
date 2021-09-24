package SnakeTry3;

import java.io.Serializable;

public class Highscore implements Serializable, Comparable {
    String name;
    int score;

    public Highscore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(Object o) {
        Highscore that = (Highscore) o;
        int result = this.score - that.score;
        if (result < 0) return -1;
        else if (result==0) return 0;
        else return 1;
    }
}
