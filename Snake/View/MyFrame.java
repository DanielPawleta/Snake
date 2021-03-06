package Snake.View;



import Snake.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame implements ActionListener {
    private MyUpPanel upPanel;
    private JPanel downPanel;
    private JButton returnButton;
    private JButton pauseButton;
    private final String PAUSE_BUTTON_TEXT = "Pause";
    private JButton saveButton;
    private boolean areButtonsVisible=true;
    private JPanel leftPanel ;
    private JPanel rightPanel;
    private JPanel centerPanel;
    private final int PANEL_SIZE =30;
    private final int GAP_IN_BORDER_LAYOUT_SIZE =10;
    private Main main;

    //Constructor
    public MyFrame() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Snake by Daniel");
        this.setLayout(new BorderLayout(GAP_IN_BORDER_LAYOUT_SIZE,GAP_IN_BORDER_LAYOUT_SIZE));
        this.getContentPane().setBackground(Color.BLACK);
        initializePanels();
        this.setVisible(true);
    }

    //Methods
    private void initializePanels(){
        upPanel = new MyUpPanel();
        //initializeUpPanel();
        downPanel = new JPanel();
        initializeDownPanel();
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        centerPanel = new JPanel();

        upPanel.setBackground(new Color(0x6E14D9));
        downPanel.setBackground(new Color(0x6E14D9));
        leftPanel.setBackground(new Color(0x783EC0));
        rightPanel.setBackground(new Color(0x783EC0));
        centerPanel.setBackground(new Color(0xA8D0E1));

        upPanel.setPreferredSize(new Dimension(0, PANEL_SIZE));
        downPanel.setPreferredSize(new Dimension(0, PANEL_SIZE));
        leftPanel.setPreferredSize(new Dimension(PANEL_SIZE,0));
        rightPanel.setPreferredSize(new Dimension(PANEL_SIZE,0));

        this.add(upPanel, BorderLayout.NORTH);
        this.add(downPanel, BorderLayout.SOUTH);
        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.EAST);
        this.add(centerPanel,BorderLayout.CENTER);
    }

    public void initializeDownPanel() {
        downPanel.setLayout(new GridLayout(1,3,50,10) );

        returnButton = new JButton("Return");
        pauseButton = new JButton(PAUSE_BUTTON_TEXT);
        saveButton = new JButton("Save game");

        returnButton.addActionListener(this);
        pauseButton.addActionListener(this);
        saveButton.addActionListener(this);

        downPanel.add(returnButton);
        downPanel.add(pauseButton);
        downPanel.add(saveButton);

        returnButton.setFocusable(false);
        pauseButton.setFocusable(false);
        saveButton.setFocusable(false);

        returnButton.setVisible(false);
        pauseButton.setVisible(false);
        saveButton.setVisible(false);

        saveButton.setEnabled(false);
    }

    public void ableUpAndDownButtonsVisibility(){
        upPanel.ableUpPanelsVisibility();

        returnButton.setVisible(true);
        pauseButton.setVisible(true);
        saveButton.setVisible(true);
    }

    public void disableUpAndDownButtonsVisibility(){
        upPanel.disableUpPanelsVisibility();

        returnButton.setVisible(false);
        pauseButton.setVisible(false);
        saveButton.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==returnButton){
            main.resume();
            resetPauseButtonText();
            main.killSnake();
            main.switchToButtonPanel();
        }
        if (e.getSource()==pauseButton){
            changePauseButtonText();
            main.pause();
        }
        if (e.getSource()== saveButton){
            main.saveGame();
        }
    }

    public void changePauseButtonText(){
        if (pauseButton.getText().equals(PAUSE_BUTTON_TEXT)) {
            saveButton.setEnabled(true);
            pauseButton.setText("Resume");
        }
        else {
            saveButton.setEnabled(false);
            pauseButton.setText(PAUSE_BUTTON_TEXT);
        }
    }

    private void resetPauseButtonText(){
        pauseButton.setText(PAUSE_BUTTON_TEXT);
    }

    //Getters and Setters
    public void setMain(Main main) {
        this.main = main;
        upPanel.setMain(main);
    }

    public JPanel getUpPanel() {
        return upPanel;
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }
}
