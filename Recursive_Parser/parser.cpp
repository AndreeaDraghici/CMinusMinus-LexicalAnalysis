#include "parser.h"
#include "exceptions.h"
#include <string >
#include <iostream >

Parser::Parser(std::string name) :
	scanner(std::make_unique < Scanner >(name)) {
	tokType = scanner->nextToken().getType();
}
void Parser::match(TokenType tt) {
	if (tokType == tt) {
		Token tok = scanner->nextToken();
		tokType = tok.getType();
	}
	else if (tokType == TokenType::END) {
		std::cout << "End ␣of␣ file ␣!!\ n";
		exit(1);
	}
	else {
		throw Exception(" Illegal ␣ token ␣ type ␣!");
	}
}
void Parser::parse() {
	program();
	std::cout << "The ␣ program ␣is␣ syntactically ␣ correct \n";
}
void Parser::program() {
	switch (tokType) {
	case TokenType::INT:
		match(TokenType::INT);
		match(TokenType::MAIN);
		match(TokenType::LPAREN);
		match(TokenType::RPAREN);
		block();
		break;
	default:
		throw Exception("’program ’:␣ Illegal ␣ token ␣!");
	}
}
void Parser::decl_list() {
	switch (tokType) {
	case TokenType::INT:
		decl();
		break;
	case TokenType::END:
		break;
	default:
		throw Exception("’decl_list ’:␣ Illegal ␣ token ␣!");
	}
}

void Parser::decl() {
	switch (tokType) {
	case TokenType::INT:
		decl_type(); 
		ident_list();
		match(TokenType::SEMICOLON);
		break;
	default:
		throw Exception("’decl ’:␣ Illegal ␣ token ␣!");
	}
}
void Parser::decl_type() {
	switch (tokType) {
	case TokenType::INT:
		match(TokenType::INT);
		break;
	default:
		throw Exception("’decl_type ’:␣ Illegal ␣ token ␣!");
	}
}
void Parser::ident_list() {
	switch (tokType) {
	case TokenType::IDENT:
		match(TokenType::IDENT);
		match(TokenType::COMMA);
		match(TokenType::IDENT);
		ident_list();
		break;
	default:
		throw Exception("’ident_list ’:␣ Illegal ␣ token ␣!");
	}
}


void Parser::instr_block() {
	switch (tokType) {
	case TokenType::LBRACE:
		block();
		break;
	case TokenType::RETURN:
	case TokenType::IF:
	case TokenType::IDENT:
	case TokenType::SEMICOLON:
		instr();
		break;
	default:
		throw Exception("’instr_block ’:␣ Illegal ␣ token ␣!");
	}
}
void Parser::block() {
	switch (tokType) {
	case TokenType::LBRACE:
		match(TokenType::LBRACE);
		content();
		match(TokenType::RBRACE);
		break;
	default:
		throw Exception("’block ’:␣ Illegal ␣ token ␣!");
	}
}
void Parser::content() {
	switch (tokType) {
	case TokenType::INT:
		decl_list();
		instr_list();
		break;
	case TokenType::RETURN:
	case TokenType::IF:
	case TokenType::IDENT:
	case TokenType::SEMICOLON:
		instr_list();
		break;
	default:
		throw Exception("’content ’:␣ Illegal ␣ token ␣!");
	}
}void Parser::instr_list() {
	switch (tokType) {
	case TokenType::RETURN:
	case TokenType::IF:
	case TokenType::IDENT:
	case TokenType::SEMICOLON:
		instr();
		break;
	case TokenType::END:
		break;
	default:
		throw Exception("’instr_list ’:␣ Illegal ␣ token ␣!");
	}
}


void Parser::stmtList() {
	switch (tokType) {
	case TokenType::RETURN:
	case TokenType::IF:
	case TokenType::IDENT:
	case TokenType::SEMICOLON:
		stmtList1();
		break;
	case TokenType::END:
		break;
	default:
		throw Exception("’stmtList ’:␣ Illegal ␣ token ␣!");
	}
}

void Parser::stmtList1() {
	switch (tokType) {
	case TokenType::RETURN:
	case TokenType::IF:
	case TokenType::IDENT:
	case TokenType::SEMICOLON:
		stmtList1();
		break;
	case TokenType::END:
		break;
	default:
		throw Exception("’stmtList1 ’:␣ Illegal ␣ token ␣!");
	}
}

void Parser::actualList() {
	switch (tokType) {
	case TokenType::RETURN:
	case TokenType::IF:
	case TokenType::IDENT:
	case TokenType::SEMICOLON:
		expr();
		actualList1();
		break;
	case TokenType::END:
		break;
	default:
		throw Exception("’actual_list ’:␣ Illegal ␣ token ␣!");
	}
}

void Parser::actualList1() {
	switch (tokType) {
	case TokenType::RETURN:
	case TokenType::IF:
	case TokenType::IDENT:
	case TokenType::SEMICOLON:
	    expr();
		actualList1();
		break;
	case TokenType::END:
		break;
	default:
		throw Exception("’actual_list1 ’:␣ Illegal ␣ token ␣!");
	}
}




void Parser::instr() {
	switch (tokType) {
	case TokenType::RETURN:
		instr_return();
		break;
	case TokenType::IF:
		instr_if();
		break;
	case TokenType::IDENT:
		instr_assign();
		break;
	case TokenType::SEMICOLON:
		instr_empty();
		break;
	default:
		throw Exception("’instr ’:␣ Illegal ␣ token ␣!");
	}
}
void Parser::instr_return() {
	switch (tokType) {
	case TokenType::RETURN:
		match(TokenType::RETURN);
		expr();
		match(TokenType::SEMICOLON);
		break;
	default:
		throw Exception("’ instr_return ’:␣ Illegal ␣ token ␣!");
	}
}
void Parser::instr_assign() {
	switch (tokType) {
	case TokenType::IDENT:
		match(TokenType::IDENT);
		match(TokenType::ASSIGN);
		expr();
		match(TokenType::SEMICOLON);
		break;
	default:
		throw Exception("’ instr_assign ’:␣ Illegal ␣ token ␣!");
	}
}
void Parser::instr_empty() {
	switch (tokType) {
	case TokenType::SEMICOLON:
		match(TokenType::SEMICOLON);
		break;
	default:
		throw Exception("’instr_empty ’:␣ Illegal ␣ token ␣!");
	}
}
void Parser::instr_if() {
	switch (tokType) {
	case TokenType::IF:
		match(TokenType::IF);
		match(TokenType::LPAREN);
		cond();
		match(TokenType::RPAREN);
		instr_block();
		match(TokenType::ELSE);
		instr_block();
		break;
	default:
		throw Exception("’instr_if ’:␣ Illegal ␣ token ␣!");
	}
}
void Parser::expr() {
	switch (tokType) {
	case TokenType::LPAREN:
	case TokenType::IDENT:
	case TokenType::INTCONST:
		expr1();
		break;
	default:
		throw Exception("’expr ’:␣ Illegal ␣ token ␣!");
	}
}

void Parser::expr1() {
	switch (tokType) {
	case TokenType::PLUS:
		match(TokenType::PLUS);
		expr1();
		break;
	default:
		break; // not throwing any exception - case <expr1 > -> epsilon
	}
}

void Parser::cond() {
	switch (tokType) {
	case TokenType::IDENT:
	case TokenType::INTCONST:
		rel_operand();
		rel_operator();
		rel_operand();
		break;
	default:
		throw Exception("’cond ’:␣ Illegal ␣ token ␣!");
	}
}
void Parser::rel_operand() {
	switch (tokType) {
	case TokenType::IDENT:
		match(TokenType::IDENT);
		break;
	case TokenType::INTCONST:
		match(TokenType::INTCONST);
		break;
	default:
		throw Exception("’rel_operand ’:␣ Illegal ␣ token ␣!");
	}
}
void Parser::rel_operator() {
	switch (tokType) {
	case TokenType::LT:
		match(TokenType::LT);
		break;
	case TokenType::LTE:
		match(TokenType::LTE);
		break;
	case TokenType::GT:
		match(TokenType::GT);
		break;
	case TokenType::GTE:
		match(TokenType::GTE);
		break;
	case TokenType::EQ:
		match(TokenType::EQ);
		break;
	case TokenType::NEQ:
		match(TokenType::NEQ);
		break;
	default:
		throw Exception("’ rel_operator ’:␣ Illegal ␣ token ␣!");
	}
}