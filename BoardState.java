/**
 * Controls the current state of the board.
 */
public class BoardState {
    private int points;
    private static Differentiate d = new Differentiate();
    private String[] randomExpressions = {
            "3x^2 + cos(x)",
            "6x - sin(x)",
            "6x",
            "cos(x)",
            "2 + sin(x)",
            "2x - cos(x)",
            "12x^2 - sin(x)",
            "5x - cos(x)",
            "6 - tan(x)",
            "x^2 - sin(x)",
            "x^3 - sin(x)",
            "34x^2 - cos(x)",
            "79x^17 - cos(x)",
            "2x^4 + sin(x)",
            "12x - sin(x)",
            "7x^2 + sin(x)",
            "2x^2 - cos(x)"
    };

    /**
     * Increases a player's points by a given amount
     * @param num amount to increase point level by
     */
    public void incrementPoints(int num) {
        points += num;
    }

    /**
     * Decreases a player's points by a given amount
     * @param num amount to decrease point level by
     */
    public void decrementPoints(int num) {
        points -= num;
    }

    /**
     * Returns a player's current point level
     * @return point level
     */
    public int getPoints() {
        return points;
    }

    /**
     * Returns a grid of derivatvies
     * @param f function
     * @param numDerivs number of derivatives
     * @param numOptions number of options
     * @return grid filled with derivatives
     */
    public static String[][] getGrid(String f, int numDerivs, int numOptions) {
        // returns a grid of numDerivs by numOptions
        String function = f;
        String[][] derivativeChoices = new String[numDerivs][numOptions];
        for (int i = numDerivs - 1; i >= 0; i--) {
            String correctDerivative = d.differentiateString(function);
            String[] temp = new String[numOptions];
            for (int j = 0; j < numOptions; j++) {
                temp[j] = randomFunction();
            }

            temp[(int) (Math.random() * numOptions)] = correctDerivative;
            derivativeChoices[i] = temp;
            function = correctDerivative; // set it to the derivative
        }

        return derivativeChoices;
    }

    /**
     * Returns a randomly generated function
     * @return function
     */
    private static String randomFunction() {
        String generatedFunc = "";
        if (Math.random() < 0.8) {
            // 80% of the time, have the polynomial
            int power = (int) (Math.random() * 6) + 1;
            int coefficient = (int) (Math.random() * 20) + 2;
            generatedFunc += coefficient + "x^" + power;
        }

        else {
            generatedFunc += (int) (Math.random() * 6);
        }

        if (Math.random() < 0.9) {
            // 90% of the time, have the sin/cos
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
