import java.util.*;

/**
 * Controls the current state of the board.
 */
public class BoardState {
    private int points;
    private static Differentiate d = new Differentiate();

    /**
     * Increases a player's points by a given amount
     * 
     * @param num amount to increase point level by
     */
    public void incrementPoints(int num) {
        points += num;
    }

    /**
     * Decreases a player's points by a given amount
     * 
     * @param num amount to decrease point level by
     */
    public void decrementPoints(int num) {
        points -= num;
    }

    /**
     * Returns a player's current point level
     * 
     * @return point level
     */
    public int getPoints() {
        return points;
    }

    /**
     * Returns a grid of derivatvies
     * 
     * @param f          function
     * @param numDerivs  number of derivatives
     * @param numOptions number of options
     * @return grid filled with derivatives
     */
    public static String[][] getGrid(String f, int numDerivs, int numOptions) {
        // returns a grid of numDerivs by numOptions
        String function = f;
        String[][] derivativeChoices = new String[numDerivs][numOptions];
        Set<String> tempChoices;
        for (int i = numDerivs - 1; i >= 0; i--) {
            String correctDerivative = d.differentiateString(function);
            tempChoices = new HashSet<String>();

            int correctIndex = (int) (Math.random() * numOptions);
            while (tempChoices.size() < numOptions) {
                if (tempChoices.size() == correctIndex) {
                    tempChoices.add(correctDerivative);
                    continue;
                }

                if (correctDerivative == "0") {
                    tempChoices.add(Integer.toString((int) (Math.random() * 9) + 1));
                    continue;
                }

                int power = 0;
                int index = correctDerivative.indexOf("^");
                boolean hasPower = index >= 0;
                if (hasPower) {
                    power = Integer.parseInt(correctDerivative.substring(index + 1,
                            index + 2));

                }

                tempChoices.add(randomFunction(true, power, true));

            }

            System.out.println("this is temp choices:" + tempChoices);

            List<String> choicesShuffle = new ArrayList<String>(tempChoices);
            Collections.shuffle(choicesShuffle);
            derivativeChoices[i] = choicesShuffle.toArray(derivativeChoices[i]);

            function = correctDerivative; // set it to the derivative
        }

        return derivativeChoices;
    }

    /**
     * Returns a random function as an answer choice in the grid,
     * based on whether the correct answer (derivative for this order) has an
     * exponent and has sin or cos trig functions.
     * 
     * @param hasPower correct function has an exponent
     * @param power    the power of the correct function (the answer/derivative for
     *                 this row)
     * @param hasTrig  correct function has trigonometry
     * @return
     */
    private static String randomFunction(boolean hasPower, int power, boolean hasTrig) {
        String generatedFunc = "";
        if (hasPower) {
            double z = Math.random();
            if (z < 0.8 && power >= 2) {
                // 80% of the time, have the polynomial
                // int power = (int) (Math.random() * 6) + 2;
                int coefficient = (int) (Math.random() * 10) + 2;
                generatedFunc += coefficient + "x^" + power;
            }

            else if (z < 0.9 && power < 2) {
                generatedFunc = ((int) (Math.random() * 10) + 2) + "x";
            }

            else {
                generatedFunc += (int) (Math.random() * 6);
            }
        }

        if (hasTrig) {
            if (Math.random() < 0.5) {
                generatedFunc += " - ";
            } else {
                generatedFunc += " + ";
            }

            if (Math.random() > 0.5) {
                generatedFunc += "cos(x)";
            } else {
                generatedFunc += "sin(x)";
            }
        }

        return generatedFunc;
    }
}
