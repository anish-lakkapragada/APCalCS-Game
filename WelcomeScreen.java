
/**
 * Welcome screen.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomeScreen implements ActionListener {
    private Game gameFrame;
    private JLabel gameLabel;
    private JLabel numDerivLabel;
    private JSlider numDerivSlider;
    private JButton startButton;

    /**
     * Constructs the welcome screen
     * 
     * @param gameFrame Takes in the game frame.
     */
    public WelcomeScreen(Game gF) {

        gameFrame = gF;

        gameLabel = new JLabel("Derivatiles Game", SwingConstants.CENTER);
        gameLabel.setBounds(350, 50, 500, 100);
        gameLabel.setFont(new Font("Calibri", Font.BOLD, 50));
        gameFrame.add(gameLabel);

        numDerivLabel = new JLabel("Select Final Derivative Order", SwingConstants.CENTER);
        numDerivLabel.setBounds(350, 240, 500, 50);
        numDerivLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        gameFrame.add(numDerivLabel);

        startButton = new JButton("Start Game!");
        startButton.setBounds(500, 450, 200, 50);
        startButton.addActionListener(this);
        gameFrame.add(startButton);

        numDerivSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 0);
        numDerivSlider.setMajorTickSpacing(2);
        numDerivSlider.setPaintTicks(true);
        numDerivSlider.setPaintLabels(true);
        numDerivSlider.setBounds(200, 300, 400, 50);
        gameFrame.add(numDerivSlider);

        gameFrame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedOrders = numDerivSlider.getValue();
        if (selectedOrders == 0) {
            numDerivLabel.setForeground(Color.RED);
            return;
        }

        gameFrame.getContentPane().removeAll();
        gameFrame.startGame(selectedOrders);
    }
}