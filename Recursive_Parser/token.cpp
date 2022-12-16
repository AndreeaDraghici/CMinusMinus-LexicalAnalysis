#include "token.h"
#include <iostream>
#include <string>
#include <map>
// map used to write the type of a token
TokenTypeMap tokenTypeMap{ { TokenType::INTCONST , " INTCONST "},
{ TokenType::REALCONST , " REALCONST "} , { TokenType::IDENT , " IDENT "} ,
{ TokenType::INT , "INT "} , { TokenType::VOID , " VOID "},
{ TokenType::MAIN , " MAIN "}, { TokenType::RETURN , " RETURN "} ,
{ TokenType::IF , "IF"}, { TokenType::ELSE , " ELSE "},
{ TokenType::PLUS , " PLUS "}, { TokenType::TIMES , " TIMES "},
{ TokenType::ASSIGN , " ASSIGN "}, { TokenType::LPAREN , " LPAREN "} ,
{ TokenType::RPAREN , " RPAREN "}, { TokenType::LBRACE , " LBRACE "} ,
{ TokenType::RBRACE , " RBRACE "}, { TokenType::COMMA , " COMMA "},
{ TokenType::SEMICOLON , " SEMICOLON "} , { TokenType::EQ , "EQ"},
{ TokenType::NEQ , "NEQ "} , { TokenType::LT , "LT"} ,
{ TokenType::LTE , "LTE "} , { TokenType::GT , "GT"} ,
{ TokenType::GTE , "GTE "} , { TokenType::END , "END "},{TokenType::CIN , "CIN "}
,{TokenType::COUT , "COUT "}
};
Token::Token(const Token& tok) {
	lexeme = tok.lexeme;
	// copy operations
	switch (tok.type) {
	case TokenType::INTCONST:
		intConst = tok.intConst;
		break;
	case TokenType::IDENT:
		new (&ident)(std::string)(tok.ident);
		break;
	default:
		oth = tok.oth;
		break;
	}
	type = tok.type;
}
Token& Token :: operator =(const Token& tok) {
	lexeme = tok.lexeme;
	if (type == TokenType::IDENT && tok.type == TokenType::IDENT) {
		ident = tok.ident;
		return *this;
	}
	// the current token type = IDENT
	if (type == TokenType::IDENT) {
		ident .~basic_string <char >(); // destroy explicitly
	}
	// copy operations
	switch (tok.type) {
	case TokenType::INTCONST:
		intConst = tok.intConst;
		break;
	case TokenType::IDENT:
		new (&ident)(std::string)(tok.ident);
		break;
	default:
		oth = tok.oth;
		break;
	}
	type = tok.type;
	return *this;
}
void Token::print(std::ofstream& out) const {
	out << " Token ␣{\n";
	out << "\ tLexeme ␣=␣" << lexeme << std::endl;
	out << "\ tToken ␣ type ␣=␣" << tokenTypeMap[type] << std::endl;
	switch (type) {
	case TokenType::INTCONST:
		out << "\ tLValue ␣=␣";
		out << intConst << std::endl;
		break;
	case TokenType::IDENT:
		out << "\ tLValue ␣=␣";
		out << ident << std::endl;
		break;
	default:
		break;
	}
	out << "}\n";
}