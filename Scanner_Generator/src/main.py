import logging

import ply.lex as lex

writer = open("myOutFile.txt", "w")

# reserved keywords
reserved_keywords = {
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
          'OR', 'UNARY', 'LSHIFT', 'RSHIFT'] + list(reserved_keywords.values())


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
t_AND = r'\&+'  # support cases: & or && or more
t_OR = r'\|+'  # support cases: | or || or more
t_LSQBKT = r'\['
t_RSQBKT = r'\]'
t_UNARY = r'\_'
t_LSHIFT = r'<<'
t_RSHIFT = r'>>'


# Regular expression rules with action code
def t_INTCONST(t) :
    r'\d+'
    t.value = int(t.value)
    return t


def t_IDENT(t) :
    r'[a-zA-Z][a-zA-Z0-9]*'
    t.value = reserved_keywords.get(t.value, 'ID')  # Check for reserved words
    return t


# Special rule for updating line numbers (lineno)
def t_newline(t) :
    r'\n+'
    t.lexer.lineno += len(t.value)


# Characters to be ignored (special rule)
t_ignore = ' \t\n'


# Error handling rule (special rule)
def t_error(t) :
    writer.write("INVALID CHARACTER '%s'" % t.value[0] + '\n')
    t.lexer.skip(1)  # For bad characters, we just skip over it


#  # Test it output
def test_lexer() :
    lexer = lex.lex()  # Build the lexer
    file_name = input('ADD THE INPUT FILE NAME: ')

    with open(file_name) as f :
        writer.write("------------ C Minus Minus LEXICAL ANALYZER USING SCANNER GENERATORS ------------\n\n")
        contents = f.read()
        writer.write('------------ PRINT ALL CONTENTS OF INPUT FILE ------------ \n' + contents + '\n\n')

        # Give the lexer some input
        lexer.input(contents)

        writer.write('------------ LEX TOKENS ARE ------------ \n\n')
        # Tokenize
        while True :
            tok = lexer.token()
            if not tok :
                break  # No more input
            writer.write(tok.__str__() + '\n')

    writer.close()


# Build the lexer and try it out
if __name__ == '__main__' :
    try :
        test_lexer()
    except Exception as exception :
        print(f"Filed to running the SCANNER GENERATOR due to: {exception}\n")
