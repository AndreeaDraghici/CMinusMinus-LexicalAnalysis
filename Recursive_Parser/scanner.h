# pragma once
# ifndef SCAN
# define SCAN
# include < fstream >
# include < string >
# include < vector >
#include "token.h"
class Scanner {
	std::string fileName;
	std::ifstream inputFile;
	long currPosition;
public:
	Scanner(std::string name);
	Token nextToken();
};
# endif