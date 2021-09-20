package SnakeTry3;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame implements ActionListener {
    private JPanel upPanel;
    private JPanel downPanel;

    private JButton returnButton;
    private JButton pauseButton;
    private JButton saveBnutton;
    private boolean areButtonsVisible=false;

    private JPanel leftPanel ;
    private JPanel rightPanel;
    private JPanel centerPanel;
    private final int PANEL_SIZE =30;
    private final int GAP_IN_BORDER_LAYOUT_SIZE =10;

    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    public MyFrame() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Snake by Daniel");
        this.setLayout(new BorderLayout(GAP_IN_BORDER_LAYOUT_SIZE,GAP_IN_BORDER_LAYOUT_SIZE));
        this.getContentPane().setBackground(Color.BLACK);
        initializePanels();
        this.setVisible(true);
    }

    private void initializePanels(){
        upPanel = new JPanel();
        downPanel = new JPanel();
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
        initializeDownPanel();
    }

    public void initializeDownPanel() {
        downPanel.setLayout(new GridLayout(1,3,50,10) );

        returnButton = new JButton("Return");
        pauseButton = new JButton("Pause");
        saveBnutton = new JButton("Save game");

        returnButton.addActionListener(this);
        pauseButton.addActionListener(this);
        saveBnutton.addActionListener(this);

        downPanel.add(returnButton);
        downPanel.add(pauseButton);
        downPanel.add(saveBnutton);

        returnButton.setVisible(false);
        pauseButton.setVisible(false);
        saveBnutton.setVisible(false);
    }

    public void ableDownButtonsVisibility(){
        returnButton.setVisible(true);
        pauseButton.setVisible(true);
        saveBnutton.setVisible(true);
    }

    public void disableDownButtonsVisibility(){
            returnButton.setVisible(false);
            pauseButton.setVisible(false);
            saveBnutton.setVisible(false);
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==returnButton){

        }
        if (e.getSource()==pauseButton){
            main.pause();
        }
    }
}
