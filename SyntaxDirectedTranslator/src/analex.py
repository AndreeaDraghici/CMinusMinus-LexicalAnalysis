"""
  Created by: Andreea-Maria Draghici
  November 2022 , CR4 S1A
  C-- Project
"""

import ply.lex as lex  # import the lex from ply library

# represents the reserved keywords for C-- project
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

# list of token names for C-- project. This is always required
tokens = ['INTCONST', 'IDENT', 'PLUS', 'MULTIPLICATION', 'EQ', 'LPAREN', 'RPAREN', 'LBRACE', 'RBRACE', 'COMMA',
          'SEMICOLON', 'EQEQ', 'NEQ', 'LT', 'LTE', 'GT', 'GTE', 'MINUS', 'DIVISION', 'COMMENT', 'AND', 'LSQBKT',
          'RSQBKT', 'OR', 'UNARY', 'LSHIFT', 'RSHIFT', 'DOT'] + list(reserved_keywords.values())

# regular expression rules for above tokens
t_PLUS = r'\+'
t_MULTIPLICATION = r'\*'
t_EQ = r'='
t_LPAREN = r'\('
t_RPAREN = r'\)'
t_LBRACE = r'\{'
t_RBRACE = r'\}'
t_COMMA = r','
t_SEMICOLON = r';'
t_EQEQ = r'=='
t_NEQ = r'!='
t_LT = r'<'
t_GT = r'>'
t_LTE = r'<='
t_GTE = r'>='
t_MINUS = r'\-'
t_DIVISION = r'\/'
t_COMMENT = r'(/\*(.|\n)*?\*/)|(//.*)|(\#)'  # C-- comment , support the cases: /**/ or // or # or /***/
t_AND = r'\&&'
t_OR = r'\|+'
t_LSQBKT = r'\['
t_RSQBKT = r'\]'
t_UNARY = r'\_'
t_LSHIFT = r'<<'
t_RSHIFT = r'>>'
t_DOT = r'\.'


# regular expression rules with action code
# represents the method in which INTCONST will be checked based on the specific regex
def t_INTCONST(t) :
    r"""\d+"""
    t.value = int(t.value)  # check for int constant
    return t  # return token


# represents the method in which IDENTIFICATOR will be checked based on the specific regex
def t_IDENT(t) :
    r"""[a-zA-Z][a-zA-Z0-9]*"""
    t.value = reserved_keywords.get(t.value, 'ID')  # check for reserved words
    return t  # return token


# special rule for updating line numbers (lineno)
def t_newline(t) :
    r"""\n+"""
    t.lexer.lineno += len(t.value)  # check the line length and update the line numbers


# characters to be ignored (special rule)
t_ignore = ' \t\n'


# error handling rule (special rule)
def t_error(t) :
    print("INVALID CHARACTER '%s'" % t.value[0] + '\n')
    t.lexer.skip(1)  # for bad characters, we just skip over it


# test it output
def test_lexer() :
    lexer = lex.lex()  # build the lexer
    file_name = input('ADD THE INPUT FILE NAME: ')

    with open(file_name) as f :  # read the input file

        print(
            "------------ C Minus Minus LEXICAL ANALYZER USING SCANNER GENERATORS ------------\n\n")  # interactive message

        contents = f.read()  # read contents of input file
        print(
            '------------ PRINT ALL CONTENTS OF INPUT FILE ------------ \n' + contents + '\n\n')  # interactive message
        lexer.input(contents)  # give the lexer some input

        print('------------ LEXER TOKENS ARE ------------ \n\n')  # interactive message
        while True :  # get tokens
            tok = lexer.token()  # tokenize
            if not tok :
                break  # no more input , end of file
            print(tok.__str__() + '\n')  # write the tokens into output file

lex.lex()  # build the lexer