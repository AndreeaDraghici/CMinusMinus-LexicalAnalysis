package implementation;

import java.io.*;

import static implementation.Utils.OUTPUT_FILE_TXT;

public class Scanner extends TokenType {
    private static BufferedWriter bufferedWriter;//write buffer
    private static BufferedReader bufferedReader;//read buffer
    private static final StringBuilder STRING_BUILDER = new StringBuilder(); //builder

    /**
     * verificam daca este un cuvant cheie
     */
    public static boolean isKeyword(String str) {

        for (String string : TokenType.keyword) {
            if (string.equals(str)) {
                return true;
            }
        }

        return false;
    }

    /**
     * verificam daca este un numar
     */
    public static boolean isNum(char character) {

        return character >= '0' && character <= '9';
    }

    /**
     * verificam daca este o litera
     */
    public static boolean isLetter(char character) {

        return (character >= 'a' && character <= 'z') || (character >= 'A' && character <= 'Z');
    }

    /**
     * verificam daca este un caracter special
     */
    public static boolean isDelimiter(char character) {


        for (char symbol : TokenType.delimiter) {
            if (character == symbol) {
                return true;
            }
        }
        return false;
    }

    /**
     * citim continutul fisierului de input
     */
    public static StringBuilder readFile(StringBuilder stringBuilder, String fileSource) {

        try {
            FileReader fileReader = new FileReader(fileSource);

            BufferedReader reader = new BufferedReader(fileReader);
            String temp;

            while ((temp = reader.readLine()) != null) {
                stringBuilder.append(temp);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder;
    }

    /**
     * efectuarea analizezi lexicale
     * starile automatului finit
     *
     */
    public static void lexicalAnalysis(String inputFile) {
        int index = 0;

        try {
            File outFile = new File(OUTPUT_FILE_TXT);
            bufferedWriter = new BufferedWriter(new FileWriter(outFile));
            bufferedReader = new BufferedReader(new FileReader(inputFile));

            bufferedWriter.write("*****************************************" + "\r\n" +
                    "C Minus Minus Lexical Analyzer Result:" + "\r\n" +
                    "*****************************************" + "\r\n\n");

            StringBuilder lineContent = readFile(STRING_BUILDER, inputFile); //stocam continutul fisierului
            System.out.println(lineContent);

            while (index < lineContent.length()) {  //citim linia

                char character = lineContent.charAt(index);

                /**
                 * verificam daca primul caracter este o litera
                 */
                if (isLetter(character)) {

                    StringBuilder builder = new StringBuilder();
                    builder.append(character); //adauga stringul

                    character = lineContent.charAt(++index);//incrementam indexul si citim urmatorul caracter, returnând valoarea liniei la indexul specificat

                    while (isLetter(character) || isNum(character)) {

                        builder.append(character); //adauga stringul
                        if (index == lineContent.length() - 1) {//citim pana la sfarsitul randului
                            ++index;
                            break;
                        } else
                            character = lineContent.charAt(++index);//incrementam indexul si citim urmatorul caracter, returnând valoarea liniei la indexul specificat
                    }

                    /**
                     * verific faca sirul curent este un cuvant cheie din lista de cuvinte
                     */
                    if (isKeyword(builder.toString())) {

                        bufferedWriter.write("          RESERVED WORD: " + builder); //afisez cuvantul cheie
                        bufferedWriter.newLine();//trec pe o noua linie
                    }
                    /**
                     * daca sirul nu este un cuvant cheie
                     * inseamna ca este identificator si il afisez
                     * apoi trec pe o linie noua
                     */
                    else {
                        bufferedWriter.write("          IDENTIFIER (ID): " + builder); //afisez identificatorul
                        bufferedWriter.newLine();  //trec pe o noua linie
                    }
                }


                /**
                 * daca primul caracter este un cuvant special
                 */
                else if (isDelimiter(character)) {

                    StringBuilder builder = new StringBuilder();

                    /**
                     * daca este punct si virgula, virgula sau egal
                     * afisez caracterul si trec pe o noua linie incrementand indexul
                     */
                    if (character == ';' || character == ',' || character == '=') {
                        bufferedWriter.write("      TERMINAL SYMBOL: " + character);//afisez simbulul terminal
                        bufferedWriter.newLine();//trec pe o noua linie
                        index++;//incrementez indexul
                    }

                    /**
                     * merg pe acceeasi abordare de mai sus si pentru restul caracterelor speciale/terminale
                     */
                    else if (character == '(' || character == ')' || character == '[' || character == ']' || character == '{' || character == '}') {
                        bufferedWriter.write("        TERMINAL SYMBOL: " + character);//afisez caracterul terminal
                        bufferedWriter.newLine();//trec pe o noua linie si incrementez indexul
                        index++;
                    } else if (character == '+' || character == '-' || character == '*' || character == '"' || character == '&' || character == '\n') {
                        bufferedWriter.write("       TERMINAL SYMBOL: " + character);//aceeasi abordare ca mai sus
                        bufferedWriter.newLine();//trec pe o noua linie si incrementez indexul
                        index++;
                    }
                    /**
                     * aici incerc sa verific daca cuvantul special este un cuvant dublu
                     * de ex: ' == '
                     * apoi il afisez
                     */
                    else if (character == '>' || character == '<' || character == '=' || character == '&' || character == '|') {

                        builder.append(character);//adauga stringul
                        char nextCharacter = lineContent.charAt(++index); //incrementam indexul si citim urmatorul caracter, returnând valoarea liniei la indexul specificat

                        if (nextCharacter == '=') { //determin daca simbolul este un simbol dublu
                            builder.append(nextCharacter);
                            bufferedWriter.write("        TERMINAL SYMBOL: " + builder);//il afisez
                            bufferedWriter.newLine();//trec pe o noua linie
                            index++;//incrementez indexul
                        } else {
                            bufferedWriter.write("        TERMINAL SYMBOL: " + character);
                            bufferedWriter.newLine();
                        }
                    } else if (character == '/' || character == '#') { // determin daca simbolul este // pentru comentariu sau #
                        builder.append(character);//adauga stringul

                        if (index == lineContent.length() - 1) {//citim pana la sfarsitul randului
                            break;
                        }

                        character = lineContent.charAt(++index);
                        /**
                         * verific daca este un comentariu de forma /*
                         */
                        if (character == '*') {
                            while (true) {
                                character = lineContent.charAt(++index);
                                if (character == '*') {// am un comentariu pe mai multe randuri si il inchei */
                                    character = lineContent.charAt(++index); //incrementam indexul si citim urmatorul caracter, returnând valoarea liniei la indexul specificat
                                    if (character == '/') {
                                        character = lineContent.charAt(++index); //incrementam indexul si citim urmatorul caracter, returnând valoarea liniei la indexul specificat
                                        break;
                                    }
                                }
                            }
                        } else {
                            bufferedWriter.write("        TERMINAL SYMBOL: " + builder); //afisez simbolul terminal
                            bufferedWriter.newLine();//trec pe o noua linie
                        }

                    }
                }
                /**
                 * determin daca caracterul este un numar
                 */
                else if (isNum(character)) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(character);//adauga stringul
                    character = lineContent.charAt(++index);//incrementam indexul si citim urmatorul caracter, returnând valoarea liniei la indexul specificat

                    if (isNum(character)) {
                        while (isNum(character)) {
                            builder.append(character);//adauga stringul
                            character = lineContent.charAt(++index);//incrementam indexul si citim urmatorul caracter, returnând valoarea liniei la indexul specificat
                        }
                        bufferedWriter.write("          NUMERIC: " + builder);//afisez daca caracterul este unul numeric
                        bufferedWriter.newLine();//trec pe o noua linie
                    } else {
                        bufferedWriter.write("          NUMERIC: " + builder);
                        bufferedWriter.newLine();
                    }

                    if (isLetter(character)) {//in cazul in care simbolul nu este valid, arunc un mesaj de genul 'eroare, simbol invalid'
                        System.err.println("Eroare: Simbol invalid!");
                    }
                } else
                    index++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {//inchid fisierele
            try {
                bufferedWriter.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
