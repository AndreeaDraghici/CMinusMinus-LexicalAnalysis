# pragma once
# ifndef TOK
# define TOK
#include <fstream>
#include "toktype.h"
class Token {
	TokenType type;
	std::string lexeme;
	union {
		int intConst;
		double realConst;
		std::string ident;
		short oth;
	};
public:
	Token(TokenType t, std::string word, int n) :
		type(t), lexeme(word), intConst(n) {}
	Token(TokenType t, std::string word, double x) :
		type(t), lexeme(word), realConst(x) {}
	Token(TokenType t, std::string word, std::string id) :
		type(t), lexeme(word), ident(id) {}
	Token(TokenType t, std::string word) :
		type(t), lexeme(word), oth(1) {}
	// copy constructor is implicit defaulted
	Token(const Token& tok);
	// destructor is implicit defaulted
	~Token() {}
	// assignment operator is implicit defaulted
	Token& operator =(const Token& tok);
	TokenType getType() const { return type; }
	int getInt() const { return intConst; }
	double getReal() const { return realConst; }
	std::string getIdent() const { return ident; }
	void print(std::ofstream& out) const;
};
# endif