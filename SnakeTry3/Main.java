package SnakeTry3;


import java.awt.*;

public class Main {
    private View view;  //JPanel with game area
    private MyFrame frame; //JFrame
    private CardLayout cardLayout;
    private ButtonPanel buttonPanel; //JPanel
    private AboutPanel aboutPanel; //JPanel
    private RoomV2 game;
    private KeyboardObserver keyboardObserver;
    private boolean isPaused;
    private boolean downPanelInitialized = false;


    public KeyboardObserver getKeyboardObserver() {
        return keyboardObserver;
    }

    public MyFrame getFrame() {
        return frame;
    }

    public void setView(View view) {
        this.view = view;
    }

    public static void main(String[] args) {
         Main main = new Main();
         main.run();
    }



    public void run() {
        frame = new MyFrame(); //main frame with JPanels to switch between
        cardLayout = new CardLayout();
        frame.getCenterPanel().setLayout(cardLayout);

        buttonPanel = new ButtonPanel(this);
        frame.getCenterPanel().add(buttonPanel,"ButtonPanel");

        frame.setFocusable(true);
        frame.setSize(new Dimension(500,400));
        frame.setResizable(true);
        frame.setVisible(true);
    }

    public void switchToGamePanel() {


        keyboardObserver = new KeyboardObserver();
        keyboardObserver.setContainer(frame);
        keyboardObserver.run();

        createGame(25,20);
        frame.getCenterPanel().add(game.getView(),"GamePanel");

        if (!downPanelInitialized){
            frame.initializeDownPanel();
            downPanelInitialized=true;
        }

        frame.setMain(this);

        cardLayout.show(frame.getCenterPanel(),"GamePanel");
        frame.ableDownButtonsVisibility();

        frame.pack();
        game.run();
    }

    public void createGame(int width, int height){
        //Mouse mouse = new Mouse(5,5);
        Snake snake = new Snake(10,10);
        game = new RoomV2(width,height, snake, this, keyboardObserver);
        this.setView(game.getView());
        snake.setGame(game);

        game.getSnake().setDirection(SnakeDirection.DOWN);
    }



    public void switchToButtonPanel() {
        frame.disableDownButtonsVisibility();
        frame.setSize(new Dimension(500,400));
        cardLayout.show(frame.getCenterPanel(),"ButtonPanel");
    }

    public void switchToAboutPanel() {
        aboutPanel = new AboutPanel(this);
        frame.getCenterPanel().add(aboutPanel,"AboutPanel");
        cardLayout.show(frame.getCenterPanel(),"AboutPanel");
    }

    public void pause(){
        if (!isPaused) {
            isPaused = true;
            game.pause();
        }
        else game.resume();
    }
}