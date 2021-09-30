package SnakeTry3;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Highscores{
    private ArrayList<Highscore> highscoreList;
    private String fileName = "D:\\Moje\\Java\\Snake\\scores.txt";

    public ArrayList<Highscore> getHighscoreList() {
        //readScoresFromFile();
        return highscoreList;
    }

    public Highscores() {
        this.highscoreList = new ArrayList<>();
        try {
            readScoresFromFile();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "File is missing or broken. Resetting highscore list.");
            initializeSafetyList();

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Class not found error. Resetting highscore list.");
            initializeSafetyList();

        }
    }

    public Highscores(boolean safetyMode) {
        this.highscoreList = new ArrayList<>();
        initializeSafetyList();
    }

    public void saveScoresToFile() {
        //System.out.println("save to file");
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

    public void readScoresFromFile() throws IOException, ClassNotFoundException {
        //System.out.println("read from file");
        FileInputStream fileInputStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        highscoreList = (ArrayList<Highscore>) objectInputStream.readObject();
    }

    private void initializeSafetyList(){
        highscoreList.add(new Highscore("Sam", -100));
        highscoreList.add(new Highscore("Evan", -90));
        highscoreList.add(new Highscore("Jennifer", -120));
        highscoreList.add(new Highscore("Brock", -10));
        highscoreList.add(new Highscore("Alex", -50));
    }




}
