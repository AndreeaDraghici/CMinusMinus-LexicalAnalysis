package implementation;

public class Main {
    public static void main(String[] args) {

        try {
            Scanner.lexicalAnalysis("src/resources/inputFile.txt");
        } catch (Exception e) {
            System.out.println("Exceptie: " + e.getMessage());
        }
    }
}
