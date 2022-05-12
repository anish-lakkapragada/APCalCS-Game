import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * https://www.youtube.com/watch?v=4PfDdJ8GFHI
 * 
 */
public class Game extends JFrame implements KeyListener {

    private JLabel functionLabel; // labels the current function
    private JPanel panel;
    private Graphics g;
    private JLabel pointsLabel; // stores the points
    private BoardState boardState;
    private TileManager tm;

    private static final int numRows = 4;
    private static final int numCols = 5;
    private static final int tileWidth = 175;
    private static final int tileHeight = 175;

    public Game() {
        g = getGraphics();

        panel = new JPanel();
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel);
        panel.setLayout(null);

        boardState = new BoardState();
        addKeyListener(this);

        JLabel functionLabel = new JLabel(
                "f(x) = 3x^2 + x^3 - sin(x)");
        functionLabel.setBounds(400, 20, 200, 25);
        // panel.add(functionLabel);
        add(functionLabel);

        tm = new TileManager(3, 3, 30, 30, 100, 100, new String[3][3], this);
        add(tm);
        setVisible(true);
    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {
        return;
    }

    public void keyTyped(KeyEvent e) {
        return;
    }

    public static void main(String[] args) {
        Game game = new Game();
    }

}

/**
 * Need to get the layout completely setup by tomorrow.
 * Design the Game Manager class => {stores points, creates the grid to pass
 * into the TileManager}
 * Hank needs to finish differentiate() method by wednesday night.
 */