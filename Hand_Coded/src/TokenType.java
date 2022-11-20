package implementation;

public class TokenType {

    // reprezinta lista de cuvinte rezervate
    public static String[] keyword = new String[]{"if", "else", "int",
            "return", "void", "while", "true", "false", "cin", "cout", "bool", "type"};

    //reprezinta lista de caractere/simboluri terminale
    public static char[] delimiter = new char[]{'(', ')', '*', '/',
            '+', '-', '<', '=',
            '>', '!', ';', ',',
            '[', ']', '{', '}', '#', '"', '&','|'};
}
