# pragma once
# ifndef TKTYPE
# define TKTYPE
# include < string >
# include <map >
// enum to store the type of a token
enum class TokenType {
	INTCONST, REALCONST, IDENT, INT, MAIN, RETURN, IF, ELSE,
	PLUS, TIMES, ASSIGN, LPAREN, RPAREN, LBRACE, RBRACE, COMMA,
	SEMICOLON, EQ, NEQ, LT, LTE, GT, GTE, END, CIN, COUT, VOID
};
// map used to associate the token type of a keyword
typedef std::map < std::string, TokenType > KeywordMap;
// map used to write the type of a token
typedef std::map < TokenType, std::string > TokenTypeMap;
# endif