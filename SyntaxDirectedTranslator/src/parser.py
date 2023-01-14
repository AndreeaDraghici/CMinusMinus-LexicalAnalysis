import ply.yacc as yacc

from src import analex


class SymbolTable :
    def __init__(self, out_file_name) :
        self.symb_table = {}
        self.out_file_name = out_file_name

    def add(self, key, value) :
        self.symb_table[key] = value

    def print(self) :
        with open(self.out_file_name, 'w') as f :
            f.write('Symbol_Table: \n\n')
            for key, value in self.symb_table.items() :
                f.write(str(key) + ': ' + str(value) + '\n')


decl_sf = 0
decl_dt = ""

tokens = analex.tokens
start = 'program'


# define the grammar rules
def p_program(p) :
    '''program : program varDecl
               | program fnDecl
               | empty'''


def p_varDecl(p) :
    '''varDecl : type id ';'
               | type id '[' INTLITERAL ']' ';' '''


def p_type(p) :
    '''type : INT
            | BOOL
            | VOID'''


def p_fnDecl(p) :
    '''fnDecl : type id parameters block'''


def p_parameters(p) :
    '''parameters : '(' ')'
                  | '(' formalsList ')' '''


def p_formalsList(p) :
    '''formalsList : formalDecl
                   | formalsList ',' formalDecl'''


def p_formalDecl(p) :
    '''formalDecl : type id'''


def p_block(p) :
    '''block : '{' declList stmtList '}' '''


def p_declList(p) :
    '''declList : declList varDecl
                | empty'''


def p_stmtList(p) :
    '''stmtList : stmtList stmt
                | empty'''


def p_stmt(p) :
    '''stmt : CIN READ id ';'
            | CIN READ id '[' exp ']' ';'
            | COUT WRITE exp ';'
            | subscriptExpr '=' exp ';'
            | id '=' exp ';'
            | IF '(' exp ')' block
            | IF '(' exp ')' block ELSE block
            | WHILE '(' exp ')' block
            | RETURN exp ';'
            | RETURN ';'
            | fnCallStmt ';' '''


def p_exp(p) :
    '''exp : exp '+' exp
           | exp '-' exp
           | exp '*' exp
           | exp '/' exp
           | '!' exp
           | exp ANDAND exp
           | exp OROR exp
           | exp EQEQ exp
           | exp NOTEQ exp
           | exp '<' exp
           | exp '>' exp
           | exp LESSEQ exp
           | exp GREATEREQ exp
           | '-' atom
           | atom'''


def p_atom(p) :
    '''atom : INTLITERAL
            | STRINGLITERAL
            | TRUE
            | FALSE
            | '(' exp ')'
            | fnCallExpr
            | subscriptExpr
            | id'''


def p_fnCallExpr(p) :
    '''fnCallExpr : id '(' ')'
                  | id '(' actualList ')' '''


def p_fnCallStmt(p) :
    '''fnCallStmt : id '(' ')'
                  | id '(' actualList ')' '''


def p_actualLis(p) :
    ''' actualList : exp
                  | actualList ',' exp '''


def p_subscriptExpr(p) :
    ''' subscriptExpr : id '[' exp ']' '''


def p_id(p) :
    ''' id : ID '''


def p_error(p) :
    # get formatted representation of stack
    stack_state_str = '␣'.join([symbol.type for symbol in parser.symstack][1 :])
    print('Syntax ␣ error ␣ in ␣ input !␣ Parser ␣ State: {} ␣{}␣.␣{} '
          .format(parser.state,
                  stack_state_str,
                  p))


if __name__ == '__main__ ' :
    # Build the parser ( with debugging file for the grammar + push - down automaton
    parser = yacc.yacc(debugfile='debug_stack.log')
    # Input file
    input_file_name = input('Input ␣ file ␣ name:␣')
    output_file_name = input('Output ␣ file ␣ name:␣')

    # cretae de dictionary object

    s_table = SymbolTable(output_file_name)
    with open(input_file_name) as f :
        # Read the content
        contents = f.read()
        # print ( contents )
        # Give the content to the parser
        parser.parse(contents, debug=0)
        # write the dictionary
        s_table.print()
