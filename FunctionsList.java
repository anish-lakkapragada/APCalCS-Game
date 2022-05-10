import java.io.File;
import java.util.*;
import java.io.*;

public class FunctionsList {
    // private String fileName;
    public Queue<String> questionsQueue = new LinkedList<String>();

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

        while (scan.hasNext()) {
            token1 = scan.next();
            questionsQueue.add(token1);
        }
        scan.close();
    }

    public static String differentiate(String function) {
        return "";
    }

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
