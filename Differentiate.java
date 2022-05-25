import java.util.*;
import javax.swing.tree.TreeModel;

/**
 * Performs differentiating calculations.
 */
public class Differentiate {

    private HashMap<String, String> derivatives;

    /**
     * Constructor method.
     */
    public Differentiate() {
        derivatives = new HashMap<String, String>();
        derivatives.put("cos(x)", "- sin(x)");
        derivatives.put("sin(x)", "cos(x)");
        derivatives.put("x", "1");
        derivatives.put("x^2", "2x");
        for (int i = 3; i < 100; i++) {
            derivatives.put("x^" + i, i + "x^" + (i - 1));// x^3, 3x^2
        }
    }

    /**
     * Performs differentiation calculations on a specified function.
     * 
     * @param function A math function given in string form.
     *                 Preconditions: A polynomial +/- sin or cos function.
     *                 Coefficients included for polynomials but not for sine and
     *                 cosine.
     *
     * @return The derivative of the function in string form.
     */
    public String differentiateString(String function) {

        // "3x^2 + cos(x)" => ["3x^2", "+", "cos(x)"]
        // "3x^2 - cos(x)" => ["3x^2", "-", "cos(x)"]
        String[] terms = function.split(" ");
        String answer = "";
        boolean uselessAndOnlyForConstAtBeginningCase = false;
        for (int i = 0; i < terms.length; i++) {

            String termOrSign = terms[i];

            System.out.println("this is termor sign: " + terms[i]);

            // CORNER CASE: ONLY A CONSTANT
            if (terms.length == 1 && terms[i].indexOf("x") < 0) {
                System.out.println("this is terms[i]: " + terms[i]);
                answer = "0";
                break;
            }

            else if (uselessAndOnlyForConstAtBeginningCase) {
                uselessAndOnlyForConstAtBeginningCase = false;
                if ((termOrSign.equals("+") || termOrSign.equals("-"))) {
                    answer += termOrSign + " ";
                }
                continue;
            }

            System.out.println("this is termor sign: " + termOrSign);
            if (!(termOrSign.equals("+") || termOrSign.equals("-"))) {
                if (termOrSign.equals("x") && termOrSign.length() == 1) {
                    answer += "1 ";
                    continue;
                }

                // if const
                if (!termOrSign.contains("x")) {
                    // 4 + x
                    if (answer.length() == 0) {
                        uselessAndOnlyForConstAtBeginningCase = true;
                    }
                    // x + 4 of x + 4 + x
                    else if (answer.length() > 0) {
                        answer = answer.substring(0, answer.length() - 2);
                    }
                    // just 4 => 0
                    // if ()
                    continue;
                }
                // if kx
                if (termOrSign.charAt(termOrSign.length() - 1) == 'x') {
                    answer += termOrSign.substring(0, termOrSign.length() - 1) + " ";
                    continue;
                }
                // if it's a polynomial term
                if (termOrSign.contains("^") || termOrSign.charAt(termOrSign.length() - 1) == 'x') {
                    int index = termOrSign.indexOf("^");
                    String coefficient = termOrSign.substring(0, index - 1);

                    String derivativeTerm = derivatives.get(termOrSign.substring(index - 1, termOrSign.length()));

                    int preCoefficient = 1;

                    if (termOrSign.indexOf("^") - 2 >= 0) {
                        String numCoefficient = termOrSign.substring(0, termOrSign.indexOf("^") - 1);
                        preCoefficient = Integer.parseInt(numCoefficient); // stores the coefficient
                    }

                    int derivCoefficient = 1;
                    if (!derivativeTerm.substring(0, 1).equals("x")) {
                        derivCoefficient = Integer.parseInt(derivativeTerm.substring(0, 1));
                    }

                    String finalCoefficient = (derivCoefficient * preCoefficient) + "";
                    String finalExpression = finalCoefficient + derivativeTerm.substring(1);
                    answer += finalExpression + " ";
                }

                // if it's cos(x) or sin(x)

                // "...+"
                // "...-"
                else if (!(termOrSign.equals("+") || termOrSign.equals("-"))) {
                    String derivTerm = derivatives.get(termOrSign);
                    String fullDerivTerm;

                    // if (derivCoeff != 1) => fullDerivTerm = derivCoeff + derivTerm;

                    if (answer.length() > 0) {
                        char sign = answer.charAt(answer.length() - 2);
                        if (sign == '-' && derivTerm.charAt(0) == '-') {

                            answer = answer.substring(0, answer.length() - 2) + "+ " + derivTerm.substring(2) + " ";
                        } else if (sign == '+' && derivTerm.charAt(0) == '-') {

                            answer = answer.substring(0, answer.length() - 2) + "- " + derivTerm.substring(2) + " ";
                        } else // sin deriv => cos
                        {
                            answer += derivTerm + " ";
                        }
                    } else {
                        answer += derivTerm + " ";
                    }
                }
            }

            else {
                answer += termOrSign + " ";
            }
        }

        return answer;

    }

    /**
     * Function that formats a function (represented in String form).
     * The returned function in String form is ready to be displayed in the
     * JFrame.
     * 
     * @param function function in String form
     * @return function coming back in String form
     */
    public static String formatSubscript(String function, boolean addTag) {
        // 3x^3 + 6x^2
        ArrayList<Integer> allCarrots = new ArrayList<Integer>();
        String newFunction = "";
        for (int i = 0; i < function.length(); i++) {
            if (function.substring(i, i + 1).equals("^")) {
                allCarrots.add(i);
                newFunction += "<sup>";
                continue;
            }
            newFunction += function.substring(i, i + 1);
        }

        if (allCarrots.size() == 0) {
            return function;
        }

        String finalFunction = "";
        int lastIndex = 0;
        int numBefore = 0;
        for (int carrotIndex : allCarrots) {
            carrotIndex += numBefore * 4; // account for changes in carrotIndex.
            finalFunction += newFunction.substring(lastIndex, carrotIndex + 5 + 1) + "</sup>";
            lastIndex = carrotIndex + 5 + 1;
            numBefore++;
        }

        finalFunction += newFunction.substring(lastIndex, newFunction.length());

        if (!addTag) {
            return finalFunction;
        }

        return "<html> " + finalFunction + " </html>";
    }

    /**
     * Returns the 1st to <code>numOrders</code>-th derivatives of
     * <code>function</code> as Strings in an array.
     * 
     * @param function  function to get derivatives
     * @param numOrders number of derivatives to get
     * @return array of derivatives
     */
    public String[] correctAnswers(String function, int numOrders) {
        String[] derivatives = new String[numOrders];
        String temp = function;
        for (int i = 0; i < numOrders; i++) {
            temp = differentiateString(temp);
            derivatives[i] = temp;
        }

        return derivatives;

    }

    public static void main(String[] args) {
        Differentiate diff = new Differentiate();
        System.out.println(diff.differentiateString("15120 - sin(x)"));
    }

}
