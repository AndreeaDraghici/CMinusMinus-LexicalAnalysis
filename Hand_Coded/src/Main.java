package implementation;

import static implementation.Utils.INPUT_FILE_TXT;

public class Main {

    public static void main(String[] args) {

        try {
            Scanner.lexicalAnalysis(INPUT_FILE_TXT);
        } catch (Exception e) {
            System.err.println("Exceptie: " + e.getMessage());
        }
    }
}
