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
    private FunctionsList fl;
    private TileManager tm;

    private static final int numRows = 5;
    private static final int numCols = 5;
    private static final int tileWidth = 200;
    private static final int tileHeight = 50;

    public Game() {

        addKeyListener(this);

        // setup GUI
        panel = new JPanel();
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel);
        panel.setLayout(null);

        functionLabel = new JLabel("", SwingConstants.CENTER);
        // functionLabel.setHorizontalAlignment(JLabel.CENTER);
        functionLabel.setBounds(500, 20, 200, 25);
        functionLabel.setFont(new Font("Calibri", Font.BOLD, 14));
        // panel.add(functionLabel);
        add(functionLabel);

        tm = new TileManager(numRows, numCols, tileWidth, tileHeight, 100, 200, new String[5][5], this);
        add(tm);
        setVisible(true);

        setupGame();
    }

    private void setupGame() {
        boardState = new BoardState();
        fl = new FunctionsList("functions.txt");
        updateQuestion();
    }

    private void updateQuestion() {
        if (fl.hasQuestions()) {
            String newQuestion = fl.nextFunction();
            String[][] gridLabels = BoardState.getGrid(newQuestion, numRows, numCols);
            tm.setLabels(gridLabels);
            functionLabel.setText(newQuestion);
        }

        else {
            // TODO implement this
        }
    }

    private void select(int r, int c) {
        if (!tm.validLoc(r, c)) {
            return;
        }

    }

    public void keyPressed(KeyEvent e) {
        // method to listen for events
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