# include <iostream>
#include <string>
#include "scanner.h"
#include "token.h"
#include "exceptions.h"
#include "parser.h"

std::ofstream outputFile;
int main() {
	std::string fileName;
	std::string outFileName;
	TokenType type;
	std::cout << " Input file  name : ";
	std::cin >> fileName;
	std::cout << " Output file  name : ";
	std::cin >> outFileName;
	outputFile.open(outFileName, std::ofstream::out | std::ofstream::app);

	try {
		Parser parser(fileName);
		parser.parse();
	}
	catch (Exception e) {
		e.print();
		return 1;
	}
	return 0;
	return 0;
}