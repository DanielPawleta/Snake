package SnakeTry3;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Highscores{
    private ArrayList<Highscore> highscoreList;
    private String fileName = "D:\\Moje\\Java\\Snake\\scores.txt";

    public ArrayList<Highscore> getHighscoreList() {
        return highscoreList;
    }

    public Highscores() throws IOException, ClassNotFoundException {
        this.highscoreList = new ArrayList<>();
        readScoresFromFile();
    }

    public Highscores(boolean safetyMode) {
        this.highscoreList = new ArrayList<>();
        initializeSafetyList();
    }

    public void saveScoresToFile() {
        try  (FileOutputStream fileOutputStream =
             new FileOutputStream(fileName);
             ObjectOutputStream objectOutputStream =
             new ObjectOutputStream(fileOutputStream)
        )

        {
            objectOutputStream.writeObject(highscoreList);
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readScoresFromFile() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        highscoreList = (ArrayList<Highscore>) objectInputStream.readObject();
    }

    private void initializeSafetyList(){
        highscoreList.add(new Highscore("Sam", 100));
        highscoreList.add(new Highscore("Evan", 90));
        highscoreList.add(new Highscore("Jennifer", 120));
        highscoreList.add(new Highscore("Brock", 10));
        highscoreList.add(new Highscore("Alex", 50));
    }




}
