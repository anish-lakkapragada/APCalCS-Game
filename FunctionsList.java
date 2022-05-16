import java.io.File;
import java.util.*;
import java.io.*;

/**
 * Reads "base functions" for users to differentiate,
 * taken from the <code>Functions.txt</code> file.
 */
public class FunctionsList {
    // private String fileName;
    public Queue<String> questionsQueue = new LinkedList<String>();

    /**
     * Constructor method.
     * @param fName file with functions to put into the queue
     */
    public FunctionsList(String fName) {
        String token1 = "";
        Scanner scan;

        try {
            scan = new Scanner(new File(fName));
        }

        catch (FileNotFoundException f) {
            System.out.println("File was not found");
            return;
        }

        while (scan.hasNextLine()) {
            token1 = scan.nextLine();
            questionsQueue.add(token1);
        }
        scan.close();
    }

    /**
     * Returns true if <code>questionsQueue</code> is not empty,
     * returns false otherwise.
     * @return true if NOT empty
     */
    public boolean hasQuestions() {
        return !questionsQueue.isEmpty();
    }

    /**
     * Returns the base function
     * 
     * @return base function
     */
    public String nextFunction() {
        return questionsQueue.remove();
    }
}
