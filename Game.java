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
    private int curRow = 0;
    private String[] correctDerivatives;
    private Differentiate d = new Differentiate();

    private static final int numRows = 5;
    private static final int numCols = 5;
    private static final int tileWidth = 200;
    private static final int tileHeight = 50;

    public Game() {

        addKeyListener(this);
        correctDerivatives = new String[numRows];

        // setup GUI
        // panel = new JPanel();
        // setSize(1200, 800);
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // add(panel);
        // panel.setLayout(null);

        setSize(2000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        functionLabel = new JLabel("", SwingConstants.CENTER);
        // functionLabel.setHorizontalAlignment(JLabel.CENTER);
        functionLabel.setBounds(500, 20, 200, 25);
        functionLabel.setFont(new Font("Calibri", Font.BOLD, 14));
        // panel.add(functionLabel);
        add(functionLabel);

        // add the points label
        pointsLabel = new JLabel("Points " + 0);
        pointsLabel.setBounds(1000, 20, 100, 25);
        add(pointsLabel);

        curRow = numRows - 1;
        tm = new TileManager(numRows, numCols, tileWidth, tileHeight, 75, 200, new String[5][5], this);
        add(tm);
        tm.revalidate();

        setVisible(true);

        setupGame();
    }

    private void setupGame() {
        boardState = new BoardState();
        fl = new FunctionsList("functions.txt");
        updateQuestion();
        tm.setLoc(curRow, (int) (Math.random() * numCols), getGraphics()); // select
    }

    private void updateQuestion() {
        if (fl.hasQuestions()) {
            String newQuestion = fl.nextFunction();
            correctDerivatives = d.correctAnswers(newQuestion, numRows);
            String[][] gridLabels = BoardState.getGrid(newQuestion, numRows, numCols);
            tm.setLabels(gridLabels);
            functionLabel.setText(newQuestion);
        }

        else {
            // TODO implement this
        }
    }

    private void evaluatePoints(int r, int c) {
        String answeredDerivative = tm.getFunction(r, c);
        String correctDerivative = correctDerivatives[numRows - r - 1];
        if (correctDerivative.equals(answeredDerivative)) {
            boardState.incrementPoints(3);
            pointsLabel.setText("Points: " + boardState.getPoints());
        }
    }

    public void moveTo(int newRow, int newCol) {
        if (Math.abs(newCol - tm.curCol()) == 0) {
            evaluatePoints(tm.curRow(), tm.curCol());
            tm.setLoc(newRow, newCol, getGraphics());
            return;
        }
        tm.setLoc(newRow, newCol, getGraphics());

    }

    /**
     * up arrow (means select the current tile)
     * D or right arrow means go right (c + 1)
     * A or left arrow means go left (c - 1)
     */
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        System.out.println("called");
        if (tm == null) {
            return;
        }

        switch (keyCode) {

            case 65:
            case KeyEvent.VK_LEFT:
                moveTo(tm.curRow(), tm.curCol() - 1);
                break;

            case 68:
            case KeyEvent.VK_RIGHT:
                moveTo(tm.curRow(), tm.curCol() + 1);
                break;

            case 87:
            case KeyEvent.VK_UP:
                System.out.println("w or up");
                moveTo(tm.curRow() - 1, tm.curCol()); // selects the current column
                break;

            default:
                break;
        }
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