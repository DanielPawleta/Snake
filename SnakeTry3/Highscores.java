package SnakeTry3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Highscores{
    private ArrayList<Highscore> highscoreList;

    public ArrayList<Highscore> getHighscoreList() {
        return highscoreList;
    }

    public Highscores() {
        this.highscoreList = new ArrayList<>();
        highscoreList.add(new Highscore("Sam", 100));
        highscoreList.add(new Highscore("Evan", 90));
        highscoreList.add(new Highscore("Jennifer", 120));
        highscoreList.add(new Highscore("Brock", 10));
        highscoreList.add(new Highscore("Alex", 50));

        saveScoresToFile();
        readScoresFromFile();
    }

    private void saveScoresToFile() {
        try  (FileOutputStream fileOutputStream =
             new FileOutputStream("D:\\Moje\\Java\\Snake\\scores.txt");
             ObjectOutputStream objectOutputStream =
             new ObjectOutputStream(fileOutputStream)
        )

        {
            for (Highscore highscore : highscoreList){
                System.out.println("saved " + highscore.name);
                objectOutputStream.writeObject(highscore);
            }

        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readScoresFromFile() {
    }


    public static class Highscore implements Serializable {
        String name;
        int score;

        public Highscore(String name, int score) {
            this.name = name;
            this.score = score;
        }

        @Override
        public boolean equals(Object obj) {
            Highscore that = (Highscore) obj;
            int result = this.score-that.score;
            if (result==0) return this.name.compareTo(that.name)>0;
            return result>0;
        }
    }
}
