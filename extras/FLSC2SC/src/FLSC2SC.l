%{
#include <string.h>
#include "flsc_types.h"
#include "flsc2sc.h"
%}

%option noyywrap

blanks	[ \t\n]+
parl	\(
parr	\)
amp	\&
nil	nil

lambda	lambda
let	let
letrec	letrec
letstar	let\*
patch	patch
module	module

if	if
cond	cond
else	else

symbol	[\+\-\*\/\?]+([a-z_][a-z0-9_\+\-\*\/\?]*)?|[a-z][a-z0-9_\+\-\*\/\?]*
number	\-?[0-9]+(\.[0-9]+)?(e\-?[0-9]+)?(pi)?

%%

{blanks}	{ /* ignore */ }

{parl}		{ return(PARL); }
{parr}		{ return(PARR); }
{amp}		{ return(AMP); }
{nil}		{ return(NIL); }

{lambda}	{ return(LAMBDA); }
{let}		{ return(LET); }
{letrec}	{ return(LETREC); }
{letstar}	{ return(LETSTAR); }
{patch}		{ return(PATCH); }
{module}	{ return(MODULE); }

{if}		{ return(IF); }
{cond}		{ return(COND); }
{else}		{ return(ELSE); }

{symbol}	{ yylval.str = strdup(yytext); return(SYMB); }
{number}	{ yylval.str = strdup(yytext); return(NUM); }

