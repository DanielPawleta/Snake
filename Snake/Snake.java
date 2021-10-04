package Snake;

import java.io.Serializable;
import java.util.ArrayList;

public class Snake implements Serializable {
    private SnakeDirection direction;
    private boolean isAlive;
    private final ArrayList<SnakeSection> sections;
    private Mouse mouse;
    private Game game;

    //Constructor
    public Snake(int x, int y) {
        sections = new ArrayList<>();
        sections.add(new SnakeSection(x, y));
        //this.mouse = mouse;
        isAlive = true;
    }

    //Methods
    public void move() {
        if (!isAlive) return;

        if (direction == SnakeDirection.UP)
            move(0, -1);
        else if (direction == SnakeDirection.RIGHT)
            move(1, 0);
        else if (direction == SnakeDirection.DOWN)
            move(0, 1);
        else if (direction == SnakeDirection.LEFT)
            move(-1, 0);
    }

    private void move(int dx, int dy) {
        SnakeSection head = sections.get(0);
        head = new SnakeSection(head.getX() + dx, head.getY() + dy);

        checkBorders(head);
        if (!isAlive) return;

        checkBody(head);
        if (!isAlive) return;

        mouse = game.getMouse();

        if (head.getX() == mouse.getX() && head.getY() == mouse.getY()) // Ate it
        {
            sections.add(0, head);
            game.eatMouse();
            game.changeDelay();
        } else
        {
            sections.add(0, head);
            sections.remove(sections.size() - 1);
        }
    }

    private void checkBorders(SnakeSection head) {
        if ((head.getX() < 0 || head.getX() >= game.getWidth()) || head.getY() < 0 || head.getY() >= game.getHeight()) {
            isAlive = false;
        }
    }

    private void checkBody(SnakeSection head) {
        if (sections.contains(head)) {
            isAlive = false;
        }
    }

    public void kill() {
        isAlive=false;
    }

    public int getHeadX() {
        return sections.get(0).getX();
    }

    public int getHeadY() {
        return sections.get(0).getY();
    }

    //Getters and Setters
    public boolean isAlive() {
        return isAlive;
    }

    public ArrayList<SnakeSection> getSections() {
        return sections;
    }

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}