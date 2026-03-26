package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;
import java.io.*;

/**
 * Implements a welcome screen to the game.
 * 
 */
public class WelcomeScreen implements ActionListener {
    private Game gameFrame;
    private JLabel gameLabel;
    private JLabel numDerivLabel;
    private JSlider numDerivSlider;
    private JButton startButton, quitButton, togetherButton;

    /**
     * Constructs the welcome screen
     * 
     * @param gF Takes in the game frame.
     */
    public WelcomeScreen(Game gF) {

        gameFrame = gF;
        gameFrame.setSize(600, 600);

        gameLabel = new JLabel("Derivatiles Game", SwingConstants.CENTER);
        gameLabel.setBounds(150, 10, 300, 60);
        gameLabel.setFont(new Font("Calibri", Font.BOLD, 30));
        gameFrame.add(gameLabel);

        // Instructions panel
        JLabel instructionsLabel = new JLabel(
                "<html><center><b>How to Play</b><br>"
                        + "Pick the correct derivative for each row!<br>"
                        + "<b>A/Left</b> & <b>D/Right</b> to move, <b>W/Up</b> to select<br>"
                        + "Or press <b>1-5</b> to jump to a column<br><br>"
                        + "+3 pts for correct (+streak bonus)<br>"
                        + "-1 pt for wrong, -2 pts if time runs out<br>"
                        + "15 seconds per question!</center></html>",
                SwingConstants.CENTER);
        instructionsLabel.setBounds(100, 65, 400, 140);
        instructionsLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
        gameFrame.add(instructionsLabel);

        numDerivLabel = new JLabel("Select Final Derivative Order", SwingConstants.CENTER);
        numDerivLabel.setBounds(150, 210, 300, 50);
        numDerivLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        gameFrame.add(numDerivLabel);

        startButton = new JButton("Start Game!");
        startButton.setBounds(200, 375, 200, 50);
        startButton.setFont(new Font("Calibri", Font.ITALIC, 20));
        startButton.addActionListener(this);
        gameFrame.add(startButton);

        togetherButton = new JButton("Play with another person");
        togetherButton.setBounds(200, 435, 200, 50);
        togetherButton.setFont(new Font("Calibri", Font.ITALIC, 15));
        togetherButton.addActionListener(this);
        gameFrame.add(togetherButton);

        quitButton = new JButton("Quit");
        quitButton.setBounds(200, 495, 200, 50);
        quitButton.setFont(new Font("Calibri", Font.ITALIC, 20));
        quitButton.addActionListener(this);
        gameFrame.add(quitButton);

        numDerivSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
        numDerivSlider.setMajorTickSpacing(1);
        numDerivSlider.setPaintTicks(true);
        numDerivSlider.setPaintLabels(true);
        numDerivSlider.setBounds(150, 260, 300, 50);
        gameFrame.add(numDerivSlider);

        gameFrame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == quitButton) {
            System.exit(0);
            return;
        }

        int selectedOrders = numDerivSlider.getValue();

        if (e.getSource() == togetherButton) {
            int value = numDerivSlider.getValue();
            // remove all the components of the JFrame

            gameFrame.getContentPane().removeAll();
            gameFrame.getContentPane().revalidate();
            gameFrame.getContentPane().repaint();

            gameFrame.startGame(selectedOrders, true);

            return;
        }

        gameFrame.getContentPane().removeAll();
        gameFrame.startGame(selectedOrders, false);
    }
}
