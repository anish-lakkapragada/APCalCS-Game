public class BoardState {
    private int points;
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

    public void incrementPoints(int num) {
        points += num;
    }

    public void decrementPoints(int num) {
        points -= num;
    }

    public int getPoints() {
        return points;
    }

    public static String[][] getGrid(String function, int numDerivs, int numOptions) {
        // returns a grid of numDerivs by numOptions
        String[][] derivativeChoices = new String[numDerivs][numOptions];
        for (int i = numDerivs - 1; i >= 0; i--) {
            String correctDerivative = FunctionsList.differentiate(function);
            String[] temp = new String[numOptions];
            for (int j = 0; j < numOptions; j++) {
                temp[j] = randomFunction();
            }

            temp[(int) (Math.random() * numOptions)] = correctDerivative;
            derivativeChoices[i] = temp;
        }

        return derivativeChoices;
    }

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