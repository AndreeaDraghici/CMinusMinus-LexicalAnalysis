import ply.lex as lex

# Keywords
keywords = {
    'int' : 'INT',
    'main' : 'MAIN',
    'if' : 'IF',
    'else' : 'ELSE',
    'return' : 'RETURN',
    'void' : 'VOID',
    'while' : 'WHILE',
    'cin' : 'CIN',
    'cout' : 'COUT',
    'bool' : 'BOOL',
    'true' : 'TRUE',
    'false' : 'FALSE'
}

# List of token names. This is always required
tokens = ['INTCONST', 'IDENT', 'PLUS', 'MULTIPLICATION', 'ASSIGN', 'LPAREN', 'RPAREN', 'LBRACE', 'RBRACE', 'COMMA',
          'SEMICOLON', 'EQ', 'NEQ', 'LT', 'LTE', 'GT', 'GTE', 'MINUS', 'DIVISION', 'COMMENT', 'AND', 'LSQBKT', 'RSQBKT',
          'OR', 'UNARY'] + list(keywords.values())

# Regular expression rules for simple tokens
t_PLUS = r'\+'
t_MULTIPLICATION = r'\*'
t_ASSIGN = r'='
t_LPAREN = r'\('
t_RPAREN = r'\)'
t_LBRACE = r'\{'
t_RBRACE = r'\}'
t_COMMA = r','
t_SEMICOLON = r';'
t_EQ = r'=='
t_NEQ = r'!='
t_LT = r'<'
t_GT = r'>'
t_LTE = r'<='
t_GTE = r'>='
t_MINUS = r'\-'
t_DIVISION = r'\/'
t_COMMENT = r'(/\*(.|\n)*?\*/)|(//.*)|(\#)'  # C-- comment , support the cases: /**/ or // or # or /***/
t_AND = r'\&&'
t_OR = r'\|'
t_LSQBKT = r'\['
t_RSQBKT = r'\]'
t_UNARY = r'\_'


# Regular expression rules with action code
def t_INTCONST(t) :
    r'\d+'
    t.value = int(t.value)
    return t


def t_IDENT(t) :
    r'[a-zA-Z][a-zA-Z0-9]*'
    t.value = keywords.get(t.value, 'IDENT')  # Check for reserved words
    return t


# Special rule for updating line numbers (lineno)
def t_newline(t) :
    r'\n+'
    t.lexer.lineno += len(t.value)


# Characters to be ignored (special rule)
t_ignore = ' \t\n'  # Ignored characters (whitespace)


# Error handling rule (special rule)
def t_error(t) :
    print("Illegal character '%s'" % t.value[0])
    t.lexer.skip(1)  # For bad characters, we just skip over it


# Main function
def test_lexer() :
    lexer = lex.lex()  # Build the lexer
    file_name = input('Add the input file name: ')

    with open(file_name) as f :
        contents = f.read()
        print(contents)

        # Give the lexer some input
        lexer.input(contents)

        # Tokenize
        while True :
            tok = lexer.token()
            if not tok :
                break  # No more input
            print(tok)


if __name__ == '__main__' :
    try :
        test_lexer()
    except Exception as exception :
        print(f"Filed to running the SCANNER GENERATOR due to: {exception}\n")
