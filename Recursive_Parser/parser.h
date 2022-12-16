#pragma once
#ifndef PARS
#define PARS

#include <string >
#include <memory >
#include "scanner.h"
#include "toktype.h"
class Parser {
	std::string fileName;
	std::unique_ptr < Scanner > scanner;
	TokenType tokType;
public:
	Parser(std::string name);
	void parse();
private:
	void match(TokenType tt);
	void program();
	void decl_list();
	void decl();
	void content();
	void decl_type();
	void ident_list();
	void instr_block();
	void block();
	void instr_list();
	void instr();
	void instr_return();
	void instr_assign();
	void instr_empty();
	void instr_if();
	void expr();
	void expr1();
	void cond();
	void rel_operand();
	void rel_operator();
	void stmtList();
	void stmtList1();
	void actualList();
	void actualList1();
	void fnCallStmt();
	void fnCallExpr();
	void atom();
	void formalsList();
	void formlsDecl();
	void parameters();
	void subscriptExpr();

};
# endif