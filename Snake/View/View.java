package Snake.View;


import Snake.Mouse;
import Snake.Snake;
import Snake.SnakeSection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View extends JPanel {
    private static final Color BG_COLOR = new Color(0xbbada0); //backgroung color
    private static final int TILE_SIZE = 20; //one field size
    private static final int TILE_MARGIN = 2; //margin size
    private final int width; //game field size horizontally
    private final int height;//game field size vertically
    private final Snake snake;
    private Mouse mouse;

    //Constructor
    public View(int width, int height, Snake snake) {
        this.width=width;
        this.height=height;
        this.snake = snake;
        this.setPreferredSize(getDimension());
    }

    //Methods
    private static int offsetCoors(int arg) {
        return arg * (TILE_MARGIN + TILE_SIZE) + TILE_MARGIN;
    }

    public Dimension getDimension(){
        int widthDimension = width*TILE_SIZE+(width+1)*TILE_MARGIN;
        int heightDimension = height*TILE_SIZE+(height+1)*TILE_MARGIN;
        return new Dimension(widthDimension,heightDimension);
    }

    public Color getColor(int value){
        switch (value) {
            case 1: {  //snake's body
                return new Color(0xB65454);
            }
            case 2: { //snake's head
                return new Color(0xCE1E1E);
            }
            case 3: { //mouse
                return new Color(0xF19E06);
            }
            case 4: { // RIP
                return new Color(0x003799);
            }
            default: return new Color(0xFFFFFF); //blank space
        }
    }
    @Override
    public void paint(Graphics g) {
        g.setColor(BG_COLOR); //background color
        g.fillRect(0, 0, this.getSize().width, this.getSize().height); //background as rectangle

        int[][] matrix = new int[width][height]; //game logic uses matrix as a game-filed
        //number 0 on matrix stands for blank space
        matrix[mouse.getX()][mouse.getY()] = 3; //number 3 on matrix stands for mouse

        ArrayList<SnakeSection> sections = snake.getSections();
        for (SnakeSection snakeSection : sections) {
            matrix[snakeSection.getX()][snakeSection.getY()] = 1;//number 1 on matrix stands for snake section
        }
        matrix[snake.getHeadX()][snake.getHeadY()] = snake.isAlive() ? 2 : 4; //number 4 on matrix stands for snake's head

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                drawTile(g, matrix[x][y], x, y); //drawing every field
            }
        }
    }

    private void drawTile(Graphics g2, int sectionNumber, int x, int y) {
        Graphics2D g = ((Graphics2D) g2);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int xOffset = offsetCoors(x); //rect beginning on X axis
        int yOffset = offsetCoors(y); //rect beginning on Y axis
        g.setColor(getColor(sectionNumber)); //get color depending on number in matrix
        g.fillRoundRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE , 8, 8); //draw all game obects as rects
    }

    //Getters and Setters
    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }
}