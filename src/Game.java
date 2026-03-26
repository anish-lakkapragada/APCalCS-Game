package src;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.net.InetAddress;

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

    // networking components
    private boolean isTogether = false;
    private boolean networkAddedPoints = false;
    private Socket socket; // take the socket
    private ServerSocket listener; // listener for the socket.
    private BufferedReader bf;
    private static int PORT = 5000;
    private static String SERVER_IP; // todo change this
    private String newFunction; // newFunction entered in by the user

    // swing components
    private JLabel functionLabel; // labels the current function.
    private JButton backButton; // goes back
    private WelcomeScreen welcomeScreen;
    private JLabel pointsLabel; // stores the points
    private JLabel feedbackLabel; // shows correct/incorrect feedback
    private JLabel timerLabel; // shows countdown timer
    private JLabel streakLabel; // shows current streak
    private BoardState boardState;
    private FunctionsList fl;
    private TileManager tm;
    private int curRow = 0;
    private String[] correctDerivatives;
    private Differentiate d = new Differentiate();
    private int numRows;
    private int streak = 0;
    private javax.swing.Timer countdownTimer;
    private int timeRemaining = 15; // seconds per question
    private int questionsAnswered = 0;
    private int questionsCorrect = 0;

    private static final int numCols = 5;
    private static final int tileWidth = 200;
    private static final int tileHeight = 75;

    /**
     * Constructor method.
     */
    public Game() {

        try {
            SERVER_IP = InetAddress.getLocalHost().toString();
        } catch (Exception e) {

        }

        setFocusable(true);
        addKeyListener(this);
        getContentPane().setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Derivatiles");
        setResizable(false);

        welcomeScreen = new WelcomeScreen(this);
    }

    /**
     * Starts up a game with a specified number of derivative orders,
     * and either networking or no networking.
     * 
     * @param numOrders    number of derivatives to find in this game
     * @param isNetworking this specific game is multiplayer if
     *                     <code>isNetworking</code> is true,
     *                     and single-player if it's false.
     */
    public void startGame(int numOrders, boolean isNetworking) {
        setSize(1200, numOrders * tileHeight + 300); // resize here!

        numRows = numOrders;
        correctDerivatives = new String[numRows];
        streak = 0;
        questionsAnswered = 0;
        questionsCorrect = 0;

        functionLabel = new JLabel("", SwingConstants.CENTER);
        functionLabel.setBounds(350, 50, 500, 45);
        functionLabel.setFont(new Font("Calibri", Font.BOLD, 25));
        add(functionLabel);

        // add the points label
        pointsLabel = new JLabel("Points: 0");
        pointsLabel.setFont(new Font("Calibri", Font.ITALIC, 20));
        pointsLabel.setBounds(935, 20, 200, 25);
        add(pointsLabel);

        // add the streak label
        streakLabel = new JLabel("");
        streakLabel.setFont(new Font("Calibri", Font.BOLD, 16));
        streakLabel.setForeground(new Color(255, 140, 0));
        streakLabel.setBounds(935, 50, 200, 25);
        add(streakLabel);

        // add the timer label
        timerLabel = new JLabel("Time: 15s");
        timerLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        timerLabel.setBounds(935, 80, 200, 25);
        add(timerLabel);

        // add feedback label
        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        feedbackLabel.setBounds(350, 10, 500, 30);
        add(feedbackLabel);

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

        if (isNetworking) {
            boardState = new BoardState();
            functionLabel.setText("f(x) = ?");
            (new Thread(() -> {
                try {

                    listener = new ServerSocket(PORT);
                    socket = listener.accept();

                    this.isTogether = true;
                    // ok so I basically need a condition on this
                    while (this.isTogether) {
                        bf = new BufferedReader(new InputStreamReader(socket.getInputStream())); // constantly read in
                        String str = bf.readLine(); // read in the function.
                        networkAddedPoints = false; // restart
                        updateQuestion(str.substring("f(x) = ".length()));
                        try {
                            Thread.sleep(1500); // sleep for 1.5 seeconds
                        } catch (InterruptedException exx) {
                        }
                    }

                } catch (IOException ex) {
                    return;
                }
            })).start();
        }

        else {
            setupGame();
        }

        setVisible(true);

    }

    private void setupGame() {
        boardState = new BoardState();
        fl = new FunctionsList("functions.txt");
        updateQuestion(null);
    }

    private void updateQuestion(String function) {
        curRow = numRows - 1;
        String newQuestion = function;
        if (newQuestion == null && fl != null && fl.hasQuestions()) {
            newQuestion = fl.nextFunction();
        }

        if (newQuestion == null || newQuestion.trim().isEmpty()) {
            showGameOver();
            return;
        }

        newQuestion = newQuestion.trim();
        correctDerivatives = d.correctAnswers(newQuestion, numRows);
        String[][] gridLabels = BoardState.getGrid(newQuestion, numRows, numCols);
        tm.setLabels(gridLabels);
        functionLabel.setText("<html> f(x) = " + Differentiate.formatSubscript(newQuestion, false) + " </html>");

        tm.setLoc(curRow, (int) (Math.random() * numCols), getGraphics());
        resetTimer();
    }

    private void evaluatePoints(int r, int c) {
        String answeredDerivative = tm.getFunction(r, c);
        String correctDerivative = correctDerivatives[numRows - r - 1];
        // Strip HTML tags for comparison
        String cleanAnswer = answeredDerivative.replaceAll("<[^>]*>", "").trim();
        String cleanCorrect = correctDerivative.trim();
        if (cleanCorrect.equals(cleanAnswer)) {
            int bonus = 3 + streak;
            boardState.incrementPoints(bonus);
            streak++;
            questionsCorrect++;
            showFeedback(true, bonus);
        } else {
            boardState.decrementPoints(1);
            streak = 0;
            showFeedback(false, -1);
        }
        questionsAnswered++;
        pointsLabel.setText("Points: " + boardState.getPoints());
        updateStreakLabel();
    }

    private void showFeedback(boolean correct, int pointsDelta) {
        if (correct) {
            feedbackLabel.setForeground(new Color(0, 150, 0));
            String msg = "Correct! +" + pointsDelta + " pts";
            if (streak > 1) {
                msg += " (streak x" + streak + "!)";
            }
            feedbackLabel.setText(msg);
        } else {
            feedbackLabel.setForeground(Color.RED);
            feedbackLabel.setText("Wrong! -1 pt");
        }
        // Clear feedback after 2 seconds
        javax.swing.Timer clearTimer = new javax.swing.Timer(2000, evt -> feedbackLabel.setText(""));
        clearTimer.setRepeats(false);
        clearTimer.start();
    }

    private void updateStreakLabel() {
        if (streak >= 2) {
            streakLabel.setText("Streak: " + streak + " in a row!");
        } else {
            streakLabel.setText("");
        }
    }

    private void resetTimer() {
        timeRemaining = 15;
        timerLabel.setForeground(Color.BLACK);
        timerLabel.setText("Time: 15s");
        if (countdownTimer != null) {
            countdownTimer.stop();
        }
        countdownTimer = new javax.swing.Timer(1000, evt -> {
            timeRemaining--;
            timerLabel.setText("Time: " + timeRemaining + "s");
            if (timeRemaining <= 5) {
                timerLabel.setForeground(Color.RED);
            }
            if (timeRemaining <= 0) {
                countdownTimer.stop();
                boardState.decrementPoints(2);
                streak = 0;
                questionsAnswered++;
                pointsLabel.setText("Points: " + boardState.getPoints());
                updateStreakLabel();
                feedbackLabel.setForeground(Color.RED);
                feedbackLabel.setText("Time's up! -2 pts");
                javax.swing.Timer clearTimer = new javax.swing.Timer(1500, e2 -> feedbackLabel.setText(""));
                clearTimer.setRepeats(false);
                clearTimer.start();
                if (!isTogether) {
                    updateQuestion(null);
                }
            }
        });
        countdownTimer.start();
    }

    private void showGameOver() {
        if (countdownTimer != null) {
            countdownTimer.stop();
        }
        getContentPane().removeAll();
        setSize(600, 500);

        JLabel gameOverLabel = new JLabel("Game Over!", SwingConstants.CENTER);
        gameOverLabel.setBounds(150, 30, 300, 60);
        gameOverLabel.setFont(new Font("Calibri", Font.BOLD, 40));
        add(gameOverLabel);

        JLabel finalScoreLabel = new JLabel("Final Score: " + boardState.getPoints(), SwingConstants.CENTER);
        finalScoreLabel.setBounds(150, 110, 300, 40);
        finalScoreLabel.setFont(new Font("Calibri", Font.BOLD, 28));
        finalScoreLabel.setForeground(new Color(0, 100, 200));
        add(finalScoreLabel);

        String accuracy = questionsAnswered > 0
                ? String.format("%.0f%%", (questionsCorrect * 100.0 / questionsAnswered))
                : "N/A";
        JLabel statsLabel = new JLabel(
                "<html><center>Questions: " + questionsAnswered
                        + "<br>Correct: " + questionsCorrect
                        + "<br>Accuracy: " + accuracy + "</center></html>",
                SwingConstants.CENTER);
        statsLabel.setBounds(150, 170, 300, 100);
        statsLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        add(statsLabel);

        String grade;
        Color gradeColor;
        double pct = questionsAnswered > 0 ? (questionsCorrect * 100.0 / questionsAnswered) : 0;
        if (pct >= 90) { grade = "A+ - AP Ready!"; gradeColor = new Color(0, 150, 0); }
        else if (pct >= 80) { grade = "B - Almost there!"; gradeColor = new Color(0, 100, 200); }
        else if (pct >= 70) { grade = "C - Keep practicing!"; gradeColor = new Color(255, 140, 0); }
        else { grade = "Needs more practice!"; gradeColor = Color.RED; }

        JLabel gradeLabel = new JLabel(grade, SwingConstants.CENTER);
        gradeLabel.setBounds(150, 280, 300, 40);
        gradeLabel.setFont(new Font("Calibri", Font.BOLD, 22));
        gradeLabel.setForeground(gradeColor);
        add(gradeLabel);

        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setBounds(200, 350, 200, 50);
        playAgainButton.setFont(new Font("Calibri", Font.BOLD, 20));
        playAgainButton.addActionListener(evt -> {
            getContentPane().removeAll();
            setSize(1200, 1000);
            welcomeScreen = new WelcomeScreen(Game.this);
        });
        add(playAgainButton);

        revalidate();
        repaint();
        setVisible(true);
    }

    /**
     * Moves the player to a new position on the grid,
     * as specified by <code>newRow</code> and
     * <code>newCol</code>.
     * 
     * @param newRow new row position
     * @param newCol new column postition
     */
    public void moveTo(int newRow, int newCol) {
        if (Math.abs(newCol - tm.curCol()) == 0) {

            if (newRow < 0 && !this.isTogether) {
                evaluatePoints(0, newCol); // evaluate at this level
                updateQuestion(null); // they kinda done now
                return;
            }

            else if (newRow < 0 && this.isTogether && !networkAddedPoints) {
                evaluatePoints(0, tm.curCol());
                networkAddedPoints = true;
                return;
            }

            else if (networkAddedPoints) {
                return;
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
            try {
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                pw.println("done");
                pw.flush();

                listener.close();
                socket.close();
            } catch (Exception ex) {
            }

            this.getContentPane().removeAll();
            this.isTogether = false; // false
            networkAddedPoints = false;
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
        if (tm == null) {
            return;
        }

        if (keyCode >= KeyEvent.VK_1 && keyCode <= KeyEvent.VK_0 + numCols) {
            moveTo(tm.curRow(), (keyCode - KeyEvent.VK_0) - 1);
            return;
        }

        switch (keyCode) {

            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                moveTo(tm.curRow(), tm.curCol() - 1);
                break;

            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                moveTo(tm.curRow(), tm.curCol() + 1);
                break;

            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                moveTo(tm.curRow() - 1, tm.curCol());
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
