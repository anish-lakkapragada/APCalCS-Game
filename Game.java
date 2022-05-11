import javax.swing.*;
import java.awt.*;

/**
 * https://www.youtube.com/watch?v=4PfDdJ8GFHI
 * 
 */
public class Game extends JFrame {

    private JLabel functionLabel; // labels the current function
    private JPanel panel;
    private Graphics g;

    private static final int numRows = 4;
    private static final int numCols = 5;
    private static final int tileWidth = 175;
    private static final int tileHeight = 175;

    public Game() {
        g = getGraphics();

        setLayout(new BorderLayout(10, 5));
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setup();
    }

    public void setup() {
        functionLabel = new JLabel(
                "f(x) = 3x^2 + x^3 - sin(x)");
        functionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(functionLabel);

    }

    public static void main(String[] args) {
        Game game = new Game();
        game.setVisible(true);
    }
}

/**
 * Need to get the layout completely setup by tomorrow.
 * Design the Game Manager class => {stores points, creates the grid to pass
 * into the TileManager}
 * Hank needs to finish differentiate() method by wednesday night.
 */