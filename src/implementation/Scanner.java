package implementation;

import java.io.*;
import java.lang.String;

import static implementation.Utils.OUTPUT_FILE_TXT;

public class Scanner extends TokenType {
    private static File file;
    private static BufferedWriter bufferedWriter;//write buffer
    private static BufferedReader bufferedReader;//read buffer
    private static final StringBuilder sb = new StringBuilder();

    /**
     * verificam daca este un cuvant cheie
     */
    public static boolean isKeyword(String str) {

        for (String s : TokenType.keyword) {
            if (s.equals(str)) {
                return true;
            }
        }

        return false;
    }

    /**
     * verificam daca este un numar
     */
    public static boolean isNum(char ch) {

        return ch >= '0' && ch <= '9';
    }

    /**
     * verificam daca este o litera
     */
    public static boolean isLetter(char ch) {

        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
    }

    /**
     * verificam daca este un caracter special
     */
    public static boolean isDelimiter(char ch) {


        for (char c : TokenType.delimiter) {
            if (ch == c) {
                return true;
            }
        }
        return false;
    }

    /**
     * citim continutul fisierului
     */
    public static StringBuilder readFile(StringBuilder sb, String fileSource) {

        try {
            FileReader fileReader = new FileReader(fileSource);

            BufferedReader br = new BufferedReader(fileReader);
            String temp;

            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb;
    }

    /**
     * efectuarea analizezi lexicale
     * starile automatului finit
     *
     * @param filePath
     */
    public static void lexicalAnalysis(String filePath) {
        int index = 0;

        try {
            file = new File(OUTPUT_FILE_TXT);
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedReader = new BufferedReader(new FileReader(filePath));

            bufferedWriter.write("*****************************************" + "\r\n" +
                    "C Minus Minus Lexical Analyzer Result:" + "\r\n" +
                    "*****************************************" + "\r\n");

            StringBuilder line = readFile(sb, filePath);//stocam continutul fisierului
            System.out.println(line);

            while (index < line.length()) {  //citim linia

                char ch = line.charAt(index);

                /**
                 * verificam daca primul caracter este o litera
                 */
                if (isLetter(ch)) {

                    StringBuilder sb1 = new StringBuilder();
                    sb1.append(ch);

                    ch = line.charAt(++index);//incrementam indexul si citim urmatorul caracter

                    while (isLetter(ch) || isNum(ch)) {

                        sb1.append(ch);
                        if (index == line.length() - 1) {//citim pana la sfarsitul randului
                            ++index;
                            break;
                        } else
                            ch = line.charAt(++index);
                    }

                    /**
                     * verific faca sirul curent este un cuvant cheie din lista de cuvinte
                     */
                    if (isKeyword(sb1.toString())) {

                        bufferedWriter.write("          reserved word:" + sb1); //afisez cuvantul cheie
                        bufferedWriter.newLine();//trec pe o noua linie
                    }
                    /**
                     * daca sirul nu este un cuvant cheie
                     * inseamna ca este identificator si il afisez
                     * apoi trec pe o linie noua
                     */
                    else {
                        bufferedWriter.write("          ID,name=" + sb1);
                        bufferedWriter.newLine();
                    }
                }


                /**
                 * daca primul caracter este un cuvant special
                 */
                else if (isDelimiter(ch)) {

                    StringBuilder sb1 = new StringBuilder();

                    /**
                     * daca este punct si virgula, virgula sau egal
                     * afisez caracterul si trec pe o noua linie incrementand indexul
                     */
                    if (ch == ';' || ch == ',' || ch == '=') {
                        bufferedWriter.write("       TERMINAL SYMBOL    " + ch);
                        bufferedWriter.newLine();
                        index++;
                    }

                    /**
                     * merg pe acceeasi abordare de mai sus si pentru restul caracterelor speciale/terminale
                     */
                    else if (ch == '(' || ch == ')' || ch == '[' || ch == ']' || ch == '{' || ch == '}') {
                        bufferedWriter.write("        TERMINAL SYMBOL   " + ch);
                        bufferedWriter.newLine();
                        index++;
                    } else if (ch == '+' || ch == '-' || ch == '*') {
                        bufferedWriter.write("       TERMINAL SYMBOL    " + ch);
                        bufferedWriter.newLine();
                        index++;
                    }
                    /**
                     * aici incerc sa verific daca cuvantul special este un cuvant dublu
                     * de ex: ' == '
                     * apoi il scriu
                     */
                    else if (ch == '>' || ch == '<' || ch == '=') {

                        sb1.append(ch);
                        char nextCh = line.charAt(++index);

                        if (nextCh == '=') {
                            sb1.append(nextCh);
                            bufferedWriter.write("        TERMINAL SYMBOL   " + sb1);
                            bufferedWriter.newLine();
                            index++;
                        } else {
                            bufferedWriter.write("        TERMINAL SYMBOL   " + ch);
                            bufferedWriter.newLine();
                        }
                    } else if (ch == '/') {
                        sb1.append(ch);

                        if (index == line.length() - 1) {
                            break;
                        }

                        ch = line.charAt(++index);
                        /**
                         * verific daca este un comentariu de forma /*
                         */
                        if (ch == '*' || ch == '#') {
                            while (true) {
                                ch = line.charAt(++index);
                                if (ch == '*') {// am un comentariu pe mai multe randuri si il inchei */
                                    ch = line.charAt(++index);
                                    if (ch == '/') {
                                        ch = line.charAt(++index);
                                        break;
                                    }
                                }
                            }
                        } else {
                            bufferedWriter.write("        TERMINAL SYMBOL  " + sb1);
                            bufferedWriter.newLine();
                        }
                    }
                }
                /**
                 * determin daca caracterul este un numar
                 */
                else if (isNum(ch)) {
                    StringBuilder sb1 = new StringBuilder();
                    sb1.append(ch);
                    ch = line.charAt(++index);

                    if (isNum(ch)) {
                        while (isNum(ch)) {
                            sb1.append(ch);
                            ch = line.charAt(++index);
                        }
                        bufferedWriter.write("          NUM:" + sb1);
                        bufferedWriter.newLine();
                    } else {
                        bufferedWriter.write("          NUM:" + sb1);
                        bufferedWriter.newLine();
                    }

                    if (isLetter(ch)) {
                        System.out.println("Eroare: Simbol invalid!");
                    }
                } else
                    index++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
