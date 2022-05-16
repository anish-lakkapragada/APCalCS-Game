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
     * @param function a math function given in string form.
     *                 Preconditions: A polynomial +/- sin or cos function.
     *                 Coefficients included for polynomials but not for sin and
     *                 cos.
     *
     * @return the derivative of the function in string form.
     */

    public String differentiateString(String function) {

        // "3x^2 + cos(x)" => ["3x^2", "+", "cos(x)"]
        // "3x^2 - cos(x)" => ["3x^2", "-", "cos(x)"]
        String[] terms = function.split(" ");
        String answer = "";
        boolean uselessAndOnlyForConstAtBeginningCase = false;
        for (int i = 0; i < terms.length; i++) {
            // CORNER CASE: ONLY A CONSTANT
            if (terms.length == 1 && terms[i].indexOf("x") < 0) {
                answer = "0";
                break; 
            }
            
            if (uselessAndOnlyForConstAtBeginningCase) {
                uselessAndOnlyForConstAtBeginningCase = false;
                continue;
            }
            String termOrSign = terms[i];
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

                    // System.out.println(termOrSign.substring(index - 1, index + 2));

                    String derivativeTerm = derivatives.get(termOrSign.substring(index - 1, termOrSign.length()));

                    // System.out.print("Derivative term: " + derivativeTerm);

                    int preCoefficient = 1;

                    // 5x^3 => derivative term = 3x^2 and coefficiient is 5
                    // 3x^2 => currentCoefficient = 3
                    if (termOrSign.indexOf("^") - 2 >= 0) {
                        String numCoefficient = termOrSign.substring(0, termOrSign.indexOf("^") - 1);
                        preCoefficient = Integer.parseInt(numCoefficient); // stores the coefficient
                    }

                    // System.out.println("this is the pre coefficient : " + preCoefficient);
                    // System.out.println("this is the derivative term: " + derivativeTerm);
                    int derivCoefficient = 1;
                    if (!derivativeTerm.substring(0, 1).equals("x")) {
                        derivCoefficient = Integer.parseInt(derivativeTerm.substring(0, 1));
                    }

                    String finalCoefficient = (derivCoefficient * preCoefficient) + "";
                    String finalExpression = finalCoefficient + derivativeTerm.substring(1);
                    // System.out.println(finalCoefficient);
                    // System.out.println(finalExpression);
                    answer += finalExpression + " ";

                    // System.out.println("weee " + answer);
                }

                // if it's cos(x) or sin(x)

                // "...+"
                // "...-"
                else {
                    String derivTerm = derivatives.get(termOrSign);
                    String fullDerivTerm;
                    /*
                     * if (derivCoeff != 1)
                     * fullDerivTerm = derivCoeff + derivTerm;
                     */
                    if (answer.length() > 0) {
                        // System.out.println("AAANNNSSS: " + answer + "/");
                        // System.out.println("correctline?");
                        char sign = answer.charAt(answer.length() - 2);
                        // System.out.println("correctline?");
                        // System.out.println("sign: " + sign);
                        if (sign == '-' && derivTerm.charAt(0) == '-') {

                            answer = answer.substring(0, answer.length() - 2) + "+ " + derivTerm.substring(2) + " ";
                        } else if (sign == '+' && derivTerm.charAt(0) == '-') {

                            answer = answer.substring(0, answer.length() - 2) + "- " + derivTerm.substring(2) + " ";
                        } else // sin deriv => cos
                        {
                            answer += derivTerm + " ";
                        }
                    } else {
                        // System.out.println("sinnnn to cosss?: " + derivTerm);
                        answer += derivTerm + " ";

                    }
                    // System.out.println("yo" + answer);
                }
            }

            else {

                answer += termOrSign + " ";
                // System.out.println("dadada " + answer);
            }

        }

        return answer;

    }

    /**
     * 
     * @param function
     * @param numOrders
     * @return
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
        // String answer = diff.differentiateString("x + sin(x)");
        // System.out.println("ans: " + answer);

        FunctionsList fl = new FunctionsList("functions.txt");
        while (fl.hasQuestions()) {
            int i =0;
            String function = fl.nextFunction();  
            while (i < 10) {
                function = diff.differentiateString(function);
                System.out.println(function); 
                i++; 
            }
        }
        
        //System.out.println(diff.differentiateString("69"));
    }

}
