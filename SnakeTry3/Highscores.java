package SnakeTry3;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Highscores{
    private ArrayList<Highscore> highscoreList;
    private String fileName = "D:\\Moje\\Java\\Snake\\scores.txt";

    public ArrayList<Highscore> getHighscoreList() {
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

    public void saveScoresToFile() {
        Path path = Paths.get(fileName);
        if (Files.notExists(path)) {
            //System.out.println("not exisiting");
            try {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
