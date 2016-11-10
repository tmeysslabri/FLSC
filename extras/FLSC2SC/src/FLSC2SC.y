%{
#include <stdio.h>
#define YYSTYPE char*
int yyparse();
int yylex();
int yyerror(char *s);
%}

// symboles terminaux

%token PARL
%token PARR

%token LAMBDA
%token LET
%token PATCH
%token MODULE

%token SYMB
%token NUM

%%

Expr:	Lambda | Let | Patch | Module | Call | Var | Num

Lambda:	PARL LAMBDA PARL IdList PARR Expr PARR

Let:		PARL LET PARL LetList PARR Expr PARR

Patch:		PARL PATCH PARL IdList PARR Expr Expr PARR

Module:		PARL MODULE PARL IdList PARR Expr PARR

Call:		PARL Expr ExprList PARR

Var:		SYMB

Num:		NUM

ExprList:	| Expr ExprList

IdList:		| Ident IdList

LetList:	| LetTerm LetList

Ident:		SYMB

LetTerm:	PARL Ident Expr PARR

%%

int yyerror(char *s) {
	printf("yyerror : %s\n",s);
	return 0;
}

int main(void) {
	yyparse();
	return 0;
}

