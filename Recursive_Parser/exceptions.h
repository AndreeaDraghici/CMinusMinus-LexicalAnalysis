# pragma once
# ifndef EXP
# define EXP
# include < string >
class Exception {
	std::string mess;
public:
	Exception(std::string str) : mess(str) {}
	void print();
};
# endif