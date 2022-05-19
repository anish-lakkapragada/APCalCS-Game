import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Implements a welcome screen to the game.
 */
public class WelcomeScreen implements ActionListener {
    private Game gameFrame;
    private JLabel gameLabel;
    private JLabel numDerivLabel;
    private JSlider numDerivSlider;
    private JButton startButton, quitButton;

    /**
     * Constructs the welcome screen
     * 
     * @param gameFrame Takes in the game frame.
     */
    public WelcomeScreen(Game gF) {

        gameFrame = gF;
        gameFrame.setSize(600, 500);

        gameLabel = new JLabel("Derivatiles Game", SwingConstants.CENTER);
        gameLabel.setBounds(150, 10, 300, 100);
        gameLabel.setFont(new Font("Calibri", Font.BOLD, 30));
        gameFrame.add(gameLabel);

        numDerivLabel = new JLabel("Select Final Derivative Order", SwingConstants.CENTER);
        numDerivLabel.setBounds(150, 110, 300, 50);
        numDerivLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        gameFrame.add(numDerivLabel);

        startButton = new JButton("Start Game!");
        startButton.setBounds(200, 300, 200, 50);
        startButton.setFont(new Font("Calibri", Font.ITALIC, 20));
        startButton.addActionListener(this);
        gameFrame.add(startButton);

        quitButton = new JButton("Get a life");
        quitButton.setBounds(200, 375, 200, 50);
        quitButton.setFont(new Font("Calibri", Font.ITALIC, 20));
        quitButton.addActionListener(this);
        gameFrame.add(quitButton);

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
        if (e.getSource() == quitButton) {
            System.exit(0);
            return;
        }

        int selectedOrders = numDerivSlider.getValue();
        if (selectedOrders == 0) {
            numDerivLabel.setForeground(Color.RED);
            return;
        }

        gameFrame.getContentPane().removeAll();
        gameFrame.startGame(selectedOrders);
    }
}
