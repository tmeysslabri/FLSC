%{
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "flsc_types.h"

wptr *cons(char *new, wptr *rest);
wptr *lnbrk(int mod, wptr *rest);
wptr *concat(wptr *first, wptr *second);
void printwords(words *text);

int yyparse();
int yylex();
int yyerror(char *s);
%}

// symboles terminaux

%union {
	char *str;
	wptr *ptr;
}

%token <str> PARL
%token <str> PARR
%token <str> AMP

%token <str> LAMBDA
%token <str> LET
%token <str> LETREC
%token <str> LETSTAR
%token <str> PATCH
%token <str> MODULE

%token <str> IF
%token <str> COND
%token <str> ELSE

%token <str> SYMB
%token <str> NUM

%type <ptr> Expr

%type <ptr> SpecForm

%type <ptr> Func
%type <ptr> Lambda
%type <ptr> Let
%type <ptr> Patch
%type <ptr> Module

%type <ptr> Conditional
%type <ptr> If
%type <ptr> Cond

%type <ptr> Call
%type <ptr> Var
%type <ptr> Num
%type <ptr> Ident

%type <ptr> ExprList1
%type <ptr> ExprList
%type <ptr> IdList1
%type <ptr> IdList
%type <ptr> LetList1
%type <ptr> LetList
%type <ptr> LetTerm
%type <ptr> CondClsList1
%type <ptr> CondClsList
%type <ptr> CondCls
%type <ptr> ElseCls

%%

Top:	Expr	{ printwords(concat($1, cons("\n", NULL))->start); }

Expr:	SpecForm | Call | Var | Num

SpecForm:	Func | Conditional

Func:		Lambda | Let | Patch | Module

Lambda:		PARL LAMBDA PARL IdList1 PARR Expr PARR
		{ $$ = concat(concat(cons("FLSC_Lambda([", $4), cons("],",
			lnbrk(1, $6))),
			lnbrk(-1, cons(")", NULL))); }
		| PARL LAMBDA PARL IdList1 AMP Ident PARR Expr PARR
		{ $$ = concat(concat(concat(cons("FLSC_Lambda([", $4), cons(", ", $6)), cons("],",
			lnbrk(1, $8))), cons(",",
			lnbrk(-1, cons("true)", NULL)))); }

Let:		PARL LET PARL LetList1 PARR Expr PARR
		{ $$ = concat(concat(cons("FLSC_Let([",
			lnbrk(1, $4)),
			lnbrk(0, cons("],",
			lnbrk(0, $6)))),
			lnbrk(-1, cons(")", NULL))); }

		| PARL LETREC PARL LetList1 PARR Expr PARR
		{ $$ = concat(concat(cons("FLSC_LetRec([",
			lnbrk(1, $4)),
			lnbrk(0, cons("],",
			lnbrk(0, $6)))),
			lnbrk(-1, cons(")", NULL))); }

		| PARL LETSTAR PARL LetList1 PARR Expr PARR
		{ $$ = concat(concat(cons("FLSC_LetStar([",
			lnbrk(1, $4)),
			lnbrk(0, cons("],",
			lnbrk(0, $6)))),
			lnbrk(-1, cons(")", NULL))); }

Patch:		PARL PATCH PARL IdList1 PARR Expr Expr PARR
		{ $$ = concat(concat(concat(cons("FLSC_Patch([", $4), cons("],",
			lnbrk(1, $6))), cons(",",
			lnbrk(0, $7))),
			lnbrk(-1, cons(")", NULL))); }
		| PARL PATCH PARL IdList1 AMP Ident PARR Expr Expr PARR
		{ $$ = concat(concat(concat(concat(cons("FLSC_Patch([", $4), cons(", ", $6)), cons("],",
			lnbrk(1, $8))), cons(",",
			lnbrk(0, $9))), cons(",",
			lnbrk(-1, cons("true)", NULL)))); }

Module:		PARL MODULE PARL IdList1 PARR Expr PARR
		{ $$ = concat(concat(cons("FLSC_Module([", $4), cons("],",
			lnbrk(1, $6))),
			lnbrk(-1, cons(")", NULL))); }

Conditional:	If | Cond

If:		PARL IF Expr Expr PARR
		{ $$ = concat(concat(cons("FLSC_If(", lnbrk(1, $3)), cons(",", lnbrk(0, $4))), lnbrk(-1, cons(")", NULL))); }
		| PARL IF Expr Expr Expr PARR
		{ $$ = concat(concat(concat(cons("FLSC_If(",
			lnbrk(1, $3)), cons(",",
			lnbrk(0, $4))), cons(",",
			lnbrk(0, $5))),
			lnbrk(-1, cons(")", NULL))); }

Cond:		PARL COND CondClsList1 PARR
		{ $$ = concat(cons("FLSC_Cond([",
			lnbrk(1, $3)),
			lnbrk(-1, cons("])", NULL))); }
/*		| PARL COND CondClsList1 PARL ELSE Expr PARR PARR
		{ $$ = concat(concat(cons("FLSC_Cond([",
			lnbrk(1, $3)), cons("],",
			lnbrk(0, $6))),
			lnbrk(-1, cons(")", NULL))); }
*/
Call:		PARL Expr ExprList1 PARR
		{ $$ = concat(concat(cons("FLSC_Call(", lnbrk(1, $2)), cons(",[", lnbrk(1, $3))) , lnbrk(-2, cons("])", NULL))); }

Var:		SYMB	{ $$ = cons("FLSC_Var('", cons($1, cons("')", NULL))); }

Num:		NUM	{ $$ = cons("FLSC_Num(", cons($1, cons(")", NULL))); }

Ident:		SYMB	{ /*$$ = cons("FLSC_Name('", cons($1, cons("')", NULL)));*/
			$$ = cons("'", cons($1, cons("'", NULL))); }

ExprList1:	%empty		{ $$ = cons("", NULL); }
		| Expr ExprList	{ $$ = concat($1, $2); }

ExprList:	%empty		{ $$ = cons("", NULL); }
		| Expr ExprList	{ $$ = concat(cons(",", lnbrk(0, $1)), $2); }

IdList1:	%empty		{ $$ = cons("", NULL); }
		| Ident IdList	{ $$ = concat($1, $2); }

IdList:		%empty		{ $$ = cons("", NULL); }
		| Ident IdList	{ $$ = concat(cons(",", $1), $2); }

LetList1:	%empty			{ $$ = cons("", NULL); }
		| LetTerm LetList	{ $$ = concat($1, $2); }

LetList:	%empty			{ $$ = cons("", NULL); }
		| LetTerm LetList	{ $$ = concat(cons(",", lnbrk(0, $1)), $2); }

LetTerm:	PARL Ident Expr PARR	{ $$ = concat(concat(cons("[", $2), cons(",", $3)), cons("]", NULL)); }

CondClsList1:	%empty			{ $$ = cons("", NULL); }
		| CondCls CondClsList	{ $$ = concat($1, $2); }
		| ElseCls		{ $$ = $1; }

CondClsList:	%empty			{ $$ = cons("", NULL); }
		| CondCls CondClsList	{ $$ = concat(cons(",", lnbrk(0, $1)), $2); }
		| ElseCls		{ $$ = cons(",", lnbrk(0, $1)); }

CondCls:	PARL Expr Expr PARR	{ $$ = concat(concat(cons("[", $2), cons(",", $3)), cons("]", NULL)); }

//ElseCls:	PARL ELSE Expr PARR	{ $$ = concat(cons("['_else',", $3), cons("]", NULL)); }
ElseCls:	PARL ELSE Expr PARR	{ $$ = concat(cons("[FLSC_Else(),", $3), cons("]", NULL)); }

%%

wptr *cons(char *new, wptr *rest) {
	words *newelt;
	wptr *newptr;

	newelt = malloc(sizeof(words));
	newelt->word = new;
	newelt->lnbrk = 100;
	if(rest) {
		newelt->next = rest->start;
		rest->start = newelt;
		return rest;
	} else {
		newelt->next = NULL;
		newptr = malloc(sizeof(wptr));
		newptr->start = newelt;
		newptr->end = &(newelt->next);
		return newptr;
	}
}

wptr *lnbrk(int mod, wptr *rest) {
	words *newelt;
	wptr *newptr;

	newelt = malloc(sizeof(words));
	newelt->word = "";
	newelt->lnbrk = mod;
	if(rest) {
		newelt->next = rest->start;
		rest->start = newelt;
		return rest;
	} else {
		newelt->next = NULL;
		newptr = malloc(sizeof(wptr));
		newptr->start = newelt;
		newptr->end = &(newelt->next);
		return newptr;
	}
}

wptr *concat(wptr *first, wptr *second) {
	*(first->end) = second->start;
	first->end = second->end;
	free(second);
	return first;
}

void printwords(words *text) {
	words *cur = text;
	int indtlvl = 0;
	int i;

	while(cur) {
		if(cur->lnbrk != 100) {
			printf("\n");
			indtlvl += cur->lnbrk;
			for(i=0; i<indtlvl; i++) {printf("\t");};
		} else {
			printf("%s", cur->word);
		};
		cur = cur->next;
	};
}

int yyerror(char *s) {
	printf("yyerror : %s\n",s);
	return 0;
}

int main(void) {
	yyparse();
	return 0;
}

