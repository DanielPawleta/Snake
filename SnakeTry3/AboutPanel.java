package SnakeTry3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutPanel extends JPanel implements ActionListener {
    private JLabel descriptionLabel;
    private JButton okButton;
    private final Main main;

    //Constructor
    public AboutPanel(Main main) {
        this.main = main;
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        descriptionLabel = new JLabel("Description Panel");
        descriptionLabel.setFont(new Font("Arial",Font.PLAIN,25));
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(descriptionLabel);

        initializeButtons();
    }

    //Methods
    private void initializeButtons(){
        okButton = new JButton("OK");
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        okButton.addActionListener(this);

        this.add(Box.createVerticalGlue());
        this.add(okButton);
        this.add(Box.createRigidArea(new Dimension(0,20)));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) main.switchToButtonPanel();
    }
}
