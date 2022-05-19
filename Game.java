import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.awt.*;
import java.awt.event.*;

/**
 * The main driver class of the program. This class stores the frames
 * for the GUI, and listens for user keyboard input to create responses
 * in the GUI.
 * 
 * @author Sources: https://www.youtube.com/watch?v=4PfDdJ8GFHI
 * @version 5/18/22 - 11:50 pm
 * 
 */
public class Game extends JFrame implements KeyListener, ActionListener {

    private JLabel functionLabel; // labels the current function.
    private JButton backButton; // goes back
    private WelcomeScreen welcomeScreen;
    private JLabel pointsLabel; // stores the points
    private BoardState boardState;
    private FunctionsList fl;
    private TileManager tm;
    private int curRow = 0;
    private String[] correctDerivatives;
    private Differentiate d = new Differentiate();
    private int numRows;

    private static final int numCols = 5;
    private static final int tileWidth = 200;
    private static final int tileHeight = 75;

    /**
     * Constructor method.
     */
    public Game() {

        correctDerivatives = new String[numRows];
        setFocusable(true);
        addKeyListener(this); // WHY IS THE KEY LISTENER IGNORED

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Derivatiles");
        setResizable(false);

        welcomeScreen = new WelcomeScreen(this);
    }

    /**
     * Starts the game, after all users have been taken care of.
     * @param numOrders number of derivatives, as chosen by the player.
     */
    public void startGame(int numOrders) {

        setSize(1200, numOrders * tileHeight + 300); // resize here!

        this.numRows = numOrders;
        functionLabel = new JLabel("", SwingConstants.CENTER);
        // functionLabel.setHorizontalAlignment(JLabel.CENTER);
        functionLabel.setBounds(350, 50, 500, 45);
        functionLabel.setFont(new Font("Calibri", Font.BOLD, 25));
        // panel.add(functionLabel);
        add(functionLabel);

        // add the points label
        pointsLabel = new JLabel("Points: " + 0);
        pointsLabel.setFont(new Font("Calibri", Font.ITALIC, 20));
        pointsLabel.setBounds(935, 60, 125, 25);
        add(pointsLabel);

        // add the back button
        backButton = new JButton("Go Back");
        backButton.setFont(new Font("Calibri", Font.ITALIC, 15));
        backButton.setBounds(20, 15, 100, 75);
        backButton.addActionListener(this);
        add(backButton);

        // add the numCols options
        for (int colNum = 1; colNum <= numCols; colNum++) {
            JLabel colLabel = new JLabel(colNum + "");
            colLabel.setBounds(175 + tileWidth * (colNum - 1), 160, 25, 25);
            colLabel.setFont(new Font("Calibri", Font.BOLD, 17));
            add(colLabel);
        }

        for (int rowNum = 1; rowNum <= numRows; rowNum++) {
            String label = "<html>f";
            if (rowNum == 1) {
                label += "'";
            } else if (rowNum == 2) {
                label += "''";
            } else {
                label += "<sup>(" + rowNum + ")</sup>";
            }

            label += "(x)</html>"; // create the label here

            JLabel derivLabel = new JLabel(label);
            int theBottom = 125 + tileHeight * numRows;
            derivLabel.setBounds(25, theBottom - (rowNum - 1) * tileHeight, 75, 75);
            derivLabel.setFont(new Font("Calibri", Font.BOLD, 15));
            add(derivLabel);
        }

        curRow = numRows - 1;
        tm = new TileManager(numRows, numCols, tileWidth, tileHeight, 125, 200, new String[numRows][numCols], this);
        tm.paintComponent(getGraphics());
        add(tm);

        setupGame();

        setVisible(true);
    }

    private void setupGame() {
        boardState = new BoardState();
        fl = new FunctionsList("functions.txt");
        updateQuestion();
    }

    private void updateQuestion() {
        curRow = numRows - 1;
        if (fl.hasQuestions()) {
            String newQuestion = fl.nextFunction();
            correctDerivatives = d.correctAnswers(newQuestion, numRows);
            String[][] gridLabels = BoardState.getGrid(newQuestion, numRows, numCols);
            tm.setLabels(gridLabels);
            functionLabel.setText("<html> f(x) = " + Differentiate.formatSubscript(newQuestion, false) + " </html>");
        }

        tm.setLoc(curRow, (int) (Math.random() * numCols), getGraphics()); // select
    }

    private void evaluatePoints(int r, int c) {
        String answeredDerivative = tm.getFunction(r, c);
        String correctDerivative = correctDerivatives[numRows - r - 1];
        if (correctDerivative.equals(answeredDerivative)) {
            boardState.incrementPoints(3);
            pointsLabel.setText("Points: " + boardState.getPoints());
        }

        System.out.println("num rows: " + numRows);
        System.out.println("passed row: " + r);
    }

    /**
     * Moves the player to a new position on the grid,
     * as specified by <code>newRow</code> and
     * <code>newCol</code>.
     * @param newRow new row position
     * @param newCol new column postition
     */
    public void moveTo(int newRow, int newCol) {
        if (Math.abs(newCol - tm.curCol()) == 0) {
            System.out.println("this is the new row: " + newRow);

            if (newRow < 0) {
                updateQuestion(); // they kinda done now
            }

            evaluatePoints(tm.curRow(), tm.curCol());
            tm.setLoc(newRow, newCol, getGraphics());
            return;
        }
        tm.setLoc(newRow, newCol, getGraphics());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            this.getContentPane().removeAll();
            setSize(1200, 1000); // reset the size
            welcomeScreen = new WelcomeScreen(this); // go back to welcome page
        }
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

        if (keyCode >= 49 && keyCode <= 48 + numCols) {
            moveTo(tm.curRow(), (keyCode - 48) - 1);
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
