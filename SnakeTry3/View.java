package SnakeTry3;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View extends JPanel {
    private static final Color BG_COLOR = new Color(0xbbada0); //kolor tła
    private static final int TILE_SIZE = 20; //rozmiar kazdej komorki
    private static final int TILE_MARGIN = 2; //marginesy
    private final int width; //wielkosc pola rozgrywki w poziomie (nie ilosc pixeli tylko liczba kratek)
    private final int height;//wielkosc pola rozgrywki w pionie (nie ilosc pixeli tylko liczba kratek)
    private Snake snake;
    private Mouse mouse;

    public View(int width, int height, Snake snake) {
        this.width=width;
        this.height=height;
        this.snake = snake;
        //this.mouse = mouse;
        this.setPreferredSize(getDimension());
    }

    public Dimension getDimension(){
        int widthDimension = width*TILE_SIZE+(width+1)*TILE_MARGIN;
        int heightDimension = height*TILE_SIZE+(height+1)*TILE_MARGIN;
        return new Dimension(widthDimension,heightDimension);
    }

    public static int setSize(int fieldNumber){
        return ((fieldNumber+2)*TILE_SIZE+(fieldNumber+1)*TILE_MARGIN);
    }

    public Color getColor(int value){
        switch (value) {
            case 1: {  //ciało węża
                return new Color(0xB65454);
            }
            case 2: { //glowa weza
                return new Color(0xCE1E1E);
            }
            case 3: { //mysz
                return new Color(0xF19E06);
            }
            case 4: { // RIP
                return new Color(0x003799);
            }
            default: return new Color(0xFFFFFF); //puste pole białe jak tło
        }
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    @Override //kazde wywolanie metody paint "resetuje" pole gry
    public void paint(Graphics g) {  //huj wie co to za Graphics g
        super.paint(g); //nie wiem czy potrzebne
        g.setColor(BG_COLOR); //kolor tła
        g.fillRect(0, 0, this.getSize().width, this.getSize().height); //rysuje tlo jako prostokat od (0,0) do (width,height)
        int[][] matrix = new int[width][height];

        //Mouse mouse = snake.getMouse();
        matrix[mouse.getX()][mouse.getY()] = 3;

        ArrayList<SnakeSection> sections = snake.getSections();

        for (SnakeSection snakeSection : sections) {
            matrix[snakeSection.getX()][snakeSection.getY()] = 1;
        }
        matrix[snake.getX()][snake.getY()] = snake.isAlive() ? 2 : 4; //snake's head color




        //mamy tablice wypelniona liczbami:
        // 0 - puste pole
        // 1 - cialo weza
        // 2 - alive sanake's head
        // 3 - mouse
        // 4 - dead snake's head

        //printMatrix(matrix);
        //System.out.println();
        //System.out.println();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                drawTile(g, matrix[x][y], x, y); //iterujemy po tablicy width x height i rysujemy kazda komorke z tablicy
                //drawTile(g, matrix[x][y], x, y); //iterujemy po tablicy width x height i rysujemy kazda komorke z tablicy
                // chyba powinno byc [x][y] zeby dalo sie robic prostokatne pola
            }
        }
    }

    private void drawTile(Graphics g2, int sectionNumber, int x, int y) {
        Graphics2D g = ((Graphics2D) g2);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int xOffset = offsetCoors(x); //poczatek na osi x
        int yOffset = offsetCoors(y); //poczatek na osi y
        g.setColor(getColor(sectionNumber)); //pobieramy kolor z obiektu
        g.fillRoundRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE , 8, 8); //rysowanie kazej komorki jako zaokraglony prostokat
    }

    private static int offsetCoors(int arg) { //okreslanie poczatku prostokata
        return arg * (TILE_MARGIN + TILE_SIZE) + TILE_MARGIN;
    }

    private void printMatrix(int[][] matrix) {
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[0].length; y++) {
                System.out.print(matrix[x][y]);
            }
            System.out.println();
        }
    }
}