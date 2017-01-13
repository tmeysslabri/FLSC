/* A Bison parser, made by GNU Bison 3.0.2.  */

/* Bison implementation for Yacc-like parsers in C

   Copyright (C) 1984, 1989-1990, 2000-2013 Free Software Foundation, Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* C LALR(1) parser skeleton written by Richard Stallman, by
   simplifying the original so-called "semantic" parser.  */

/* All symbols defined below should begin with yy or YY, to avoid
   infringing on user name space.  This should be done even for local
   variables, as they might otherwise be expanded by user macros.
   There are some unavoidable exceptions within include files to
   define necessary library symbols; they are noted "INFRINGES ON
   USER NAME SPACE" below.  */

/* Identify Bison output.  */
#define YYBISON 1

/* Bison version.  */
#define YYBISON_VERSION "3.0.2"

/* Skeleton name.  */
#define YYSKELETON_NAME "yacc.c"

/* Pure parsers.  */
#define YYPURE 0

/* Push parsers.  */
#define YYPUSH 0

/* Pull parsers.  */
#define YYPULL 1




/* Copy the first part of user declarations.  */
#line 1 "FLSC2SC.y" /* yacc.c:339  */

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
int yyerror(const char *s);

#line 82 "flsc2sc.c" /* yacc.c:339  */

# ifndef YY_NULLPTR
#  if defined __cplusplus && 201103L <= __cplusplus
#   define YY_NULLPTR nullptr
#  else
#   define YY_NULLPTR 0
#  endif
# endif

/* Enabling verbose error messages.  */
#ifdef YYERROR_VERBOSE
# undef YYERROR_VERBOSE
# define YYERROR_VERBOSE 1
#else
# define YYERROR_VERBOSE 1
#endif

/* In a future release of Bison, this section will be replaced
   by #include "flsc2sc.h".  */
#ifndef YY_YY_FLSC2SC_H_INCLUDED
# define YY_YY_FLSC2SC_H_INCLUDED
/* Debug traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif
#if YYDEBUG
extern int yydebug;
#endif

/* Token type.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
  enum yytokentype
  {
    ERROR = 258,
    PARL = 259,
    PARR = 260,
    AMP = 261,
    NUNQ = 262,
    BRL = 263,
    BRR = 264,
    NIL = 265,
    DEFINE = 266,
    REQUIRE = 267,
    LAMBDA = 268,
    LET = 269,
    LETREC = 270,
    LETSTAR = 271,
    NOWARP = 272,
    PATCH = 273,
    MODULE = 274,
    IF = 275,
    COND = 276,
    ELSE = 277,
    SYMB = 278,
    NUM = 279,
    STRING = 280
  };
#endif

/* Value type.  */
#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef union YYSTYPE YYSTYPE;
union YYSTYPE
{
#line 24 "FLSC2SC.y" /* yacc.c:355  */

	char *str;
	wptr *ptr;

#line 153 "flsc2sc.c" /* yacc.c:355  */
};
# define YYSTYPE_IS_TRIVIAL 1
# define YYSTYPE_IS_DECLARED 1
#endif


extern YYSTYPE yylval;

int yyparse (void);

#endif /* !YY_YY_FLSC2SC_H_INCLUDED  */

/* Copy the second part of user declarations.  */

#line 168 "flsc2sc.c" /* yacc.c:358  */

#ifdef short
# undef short
#endif

#ifdef YYTYPE_UINT8
typedef YYTYPE_UINT8 yytype_uint8;
#else
typedef unsigned char yytype_uint8;
#endif

#ifdef YYTYPE_INT8
typedef YYTYPE_INT8 yytype_int8;
#else
typedef signed char yytype_int8;
#endif

#ifdef YYTYPE_UINT16
typedef YYTYPE_UINT16 yytype_uint16;
#else
typedef unsigned short int yytype_uint16;
#endif

#ifdef YYTYPE_INT16
typedef YYTYPE_INT16 yytype_int16;
#else
typedef short int yytype_int16;
#endif

#ifndef YYSIZE_T
# ifdef __SIZE_TYPE__
#  define YYSIZE_T __SIZE_TYPE__
# elif defined size_t
#  define YYSIZE_T size_t
# elif ! defined YYSIZE_T
#  include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  define YYSIZE_T size_t
# else
#  define YYSIZE_T unsigned int
# endif
#endif

#define YYSIZE_MAXIMUM ((YYSIZE_T) -1)

#ifndef YY_
# if defined YYENABLE_NLS && YYENABLE_NLS
#  if ENABLE_NLS
#   include <libintl.h> /* INFRINGES ON USER NAME SPACE */
#   define YY_(Msgid) dgettext ("bison-runtime", Msgid)
#  endif
# endif
# ifndef YY_
#  define YY_(Msgid) Msgid
# endif
#endif

#ifndef YY_ATTRIBUTE
# if (defined __GNUC__                                               \
      && (2 < __GNUC__ || (__GNUC__ == 2 && 96 <= __GNUC_MINOR__)))  \
     || defined __SUNPRO_C && 0x5110 <= __SUNPRO_C
#  define YY_ATTRIBUTE(Spec) __attribute__(Spec)
# else
#  define YY_ATTRIBUTE(Spec) /* empty */
# endif
#endif

#ifndef YY_ATTRIBUTE_PURE
# define YY_ATTRIBUTE_PURE   YY_ATTRIBUTE ((__pure__))
#endif

#ifndef YY_ATTRIBUTE_UNUSED
# define YY_ATTRIBUTE_UNUSED YY_ATTRIBUTE ((__unused__))
#endif

#if !defined _Noreturn \
     && (!defined __STDC_VERSION__ || __STDC_VERSION__ < 201112)
# if defined _MSC_VER && 1200 <= _MSC_VER
#  define _Noreturn __declspec (noreturn)
# else
#  define _Noreturn YY_ATTRIBUTE ((__noreturn__))
# endif
#endif

/* Suppress unused-variable warnings by "using" E.  */
#if ! defined lint || defined __GNUC__
# define YYUSE(E) ((void) (E))
#else
# define YYUSE(E) /* empty */
#endif

#if defined __GNUC__ && 407 <= __GNUC__ * 100 + __GNUC_MINOR__
/* Suppress an incorrect diagnostic about yylval being uninitialized.  */
# define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN \
    _Pragma ("GCC diagnostic push") \
    _Pragma ("GCC diagnostic ignored \"-Wuninitialized\"")\
    _Pragma ("GCC diagnostic ignored \"-Wmaybe-uninitialized\"")
# define YY_IGNORE_MAYBE_UNINITIALIZED_END \
    _Pragma ("GCC diagnostic pop")
#else
# define YY_INITIAL_VALUE(Value) Value
#endif
#ifndef YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
# define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
# define YY_IGNORE_MAYBE_UNINITIALIZED_END
#endif
#ifndef YY_INITIAL_VALUE
# define YY_INITIAL_VALUE(Value) /* Nothing. */
#endif


#if 1

/* The parser invokes alloca or malloc; define the necessary symbols.  */

# ifdef YYSTACK_ALLOC
   /* Pacify GCC's 'empty if-body' warning.  */
#  define YYSTACK_FREE(Ptr) do { /* empty */; } while (0)
#  ifndef YYSTACK_ALLOC_MAXIMUM
    /* The OS might guarantee only one guard page at the bottom of the stack,
       and a page size can be as small as 4096 bytes.  So we cannot safely
       invoke alloca (N) if N exceeds 4096.  Use a slightly smaller number
       to allow for a few compiler-allocated temporary stack slots.  */
#   define YYSTACK_ALLOC_MAXIMUM 4032 /* reasonable circa 2006 */
#  endif
# else
#  define YYSTACK_ALLOC YYMALLOC
#  define YYSTACK_FREE YYFREE
#  ifndef YYSTACK_ALLOC_MAXIMUM
#   define YYSTACK_ALLOC_MAXIMUM YYSIZE_MAXIMUM
#  endif
#  if (defined __cplusplus && ! defined EXIT_SUCCESS \
       && ! ((defined YYMALLOC || defined malloc) \
             && (defined YYFREE || defined free)))
#   include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#   ifndef EXIT_SUCCESS
#    define EXIT_SUCCESS 0
#   endif
#  endif
#  ifndef YYMALLOC
#   define YYMALLOC malloc
#   if ! defined malloc && ! defined EXIT_SUCCESS
void *malloc (YYSIZE_T); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
#  ifndef YYFREE
#   define YYFREE free
#   if ! defined free && ! defined EXIT_SUCCESS
void free (void *); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
# endif
# define YYCOPY_NEEDED 1
#endif


#if (! defined yyoverflow \
     && (! defined __cplusplus \
         || (defined YYSTYPE_IS_TRIVIAL && YYSTYPE_IS_TRIVIAL)))

/* A type that is properly aligned for any stack member.  */
union yyalloc
{
  yytype_int16 yyss_alloc;
  YYSTYPE yyvs_alloc;
};

/* The size of the maximum gap between one aligned stack and the next.  */
# define YYSTACK_GAP_MAXIMUM (sizeof (union yyalloc) - 1)

/* The size of an array large to enough to hold all stacks, each with
   N elements.  */
# define YYSTACK_BYTES(N) \
     ((N) * (sizeof (yytype_int16) + sizeof (YYSTYPE)) \
      + YYSTACK_GAP_MAXIMUM)

# define YYCOPY_NEEDED 1

/* Relocate STACK from its old location to the new one.  The
   local variables YYSIZE and YYSTACKSIZE give the old and new number of
   elements in the stack, and YYPTR gives the new location of the
   stack.  Advance YYPTR to a properly aligned location for the next
   stack.  */
# define YYSTACK_RELOCATE(Stack_alloc, Stack)                           \
    do                                                                  \
      {                                                                 \
        YYSIZE_T yynewbytes;                                            \
        YYCOPY (&yyptr->Stack_alloc, Stack, yysize);                    \
        Stack = &yyptr->Stack_alloc;                                    \
        yynewbytes = yystacksize * sizeof (*Stack) + YYSTACK_GAP_MAXIMUM; \
        yyptr += yynewbytes / sizeof (*yyptr);                          \
      }                                                                 \
    while (0)

#endif

#if defined YYCOPY_NEEDED && YYCOPY_NEEDED
/* Copy COUNT objects from SRC to DST.  The source and destination do
   not overlap.  */
# ifndef YYCOPY
#  if defined __GNUC__ && 1 < __GNUC__
#   define YYCOPY(Dst, Src, Count) \
      __builtin_memcpy (Dst, Src, (Count) * sizeof (*(Src)))
#  else
#   define YYCOPY(Dst, Src, Count)              \
      do                                        \
        {                                       \
          YYSIZE_T yyi;                         \
          for (yyi = 0; yyi < (Count); yyi++)   \
            (Dst)[yyi] = (Src)[yyi];            \
        }                                       \
      while (0)
#  endif
# endif
#endif /* !YYCOPY_NEEDED */

/* YYFINAL -- State number of the termination state.  */
#define YYFINAL  39
/* YYLAST -- Last index in YYTABLE.  */
#define YYLAST   163

/* YYNTOKENS -- Number of terminals.  */
#define YYNTOKENS  26
/* YYNNTS -- Number of nonterminals.  */
#define YYNNTS  31
/* YYNRULES -- Number of rules.  */
#define YYNRULES  62
/* YYNSTATES -- Number of states.  */
#define YYNSTATES  117

/* YYTRANSLATE[YYX] -- Symbol number corresponding to YYX as returned
   by yylex, with out-of-bounds checking.  */
#define YYUNDEFTOK  2
#define YYMAXUTOK   280

#define YYTRANSLATE(YYX)                                                \
  ((unsigned int) (YYX) <= YYMAXUTOK ? yytranslate[YYX] : YYUNDEFTOK)

/* YYTRANSLATE[TOKEN-NUM] -- Symbol number corresponding to TOKEN-NUM
   as returned by yylex, without out-of-bounds checking.  */
static const yytype_uint8 yytranslate[] =
{
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25
};

#if YYDEBUG
  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
static const yytype_uint8 yyrline[] =
{
       0,   100,   100,   102,   103,   108,   109,   110,   117,   117,
     117,   117,   117,   118,   120,   120,   122,   122,   122,   124,
     128,   132,   136,   141,   145,   150,   157,   157,   159,   164,
     171,   176,   182,   184,   186,   187,   189,   190,   191,   192,
     194,   195,   197,   202,   203,   205,   206,   209,   211,   212,
     214,   215,   217,   218,   220,   222,   223,   224,   226,   227,
     228,   230,   232
};
#endif

#if YYDEBUG || YYERROR_VERBOSE || 1
/* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
   First, the terminals, then, starting at YYNTOKENS, nonterminals.  */
static const char *const yytname[] =
{
  "$end", "error", "$undefined", "ERROR", "PARL", "PARR", "AMP", "NUNQ",
  "BRL", "BRR", "NIL", "DEFINE", "REQUIRE", "LAMBDA", "LET", "LETREC",
  "LETSTAR", "NOWARP", "PATCH", "MODULE", "IF", "COND", "ELSE", "SYMB",
  "NUM", "STRING", "$accept", "Top", "Program", "Defines", "Expr",
  "SpecForm", "Func", "StdFunc", "Module", "Let", "Conditional", "If",
  "Cond", "Call", "Var", "Num", "Ident", "LetOp", "FuncOp", "List",
  "ExprList1", "ExprList", "IdList1", "IdList", "LetList1", "LetList",
  "LetTerm", "CondClsList1", "CondClsList", "CondCls", "ElseCls", YY_NULLPTR
};
#endif

# ifdef YYPRINT
/* YYTOKNUM[NUM] -- (External) token number corresponding to the
   (internal) symbol number NUM (which must be that of a token).  */
static const yytype_uint16 yytoknum[] =
{
       0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,   271,   272,   273,   274,
     275,   276,   277,   278,   279,   280
};
# endif

#define YYPACT_NINF -64

#define yypact_value_is_default(Yystate) \
  (!!((Yystate) == (-64)))

#define YYTABLE_NINF -50

#define yytable_value_is_error(Yytable_value) \
  0

  /* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
     STATE-NUM.  */
static const yytype_int16 yypact[] =
{
      67,   103,    74,   -64,   -64,   -64,     5,   -64,   -64,   -64,
     -64,   -64,   -64,   -64,   -64,   -64,   -64,   -64,   -64,   -64,
     -64,   -64,   139,     3,   -16,   -64,   -64,   -64,   -64,   -64,
     -64,     8,    74,    14,    74,    16,    22,    74,    19,   -64,
      -4,    24,     3,    25,    27,    74,     0,    30,    14,   -64,
      32,     3,     9,    74,   -64,   -64,    15,   -64,    74,    79,
     -64,     3,    67,    74,    34,    -4,    45,    74,    74,   -64,
     -64,    14,   -64,   -64,    35,    74,    -4,    11,   -64,   -64,
      36,   121,   -64,   -64,   -64,    37,    74,     4,   -64,    38,
      41,    42,   -64,    74,    43,    46,    74,    -4,   -64,   -64,
      49,   -64,   -64,   -64,    51,   -64,    74,    53,    54,   -64,
     -64,    55,   -64,    74,   -64,    56,   -64
};

  /* YYDEFACT[STATE-NUM] -- Default reduction number in state STATE-NUM.
     Performed when YYTABLE does not specify something else to do.  Zero
     means the default is an error.  */
static const yytype_uint8 yydefact[] =
{
       6,     0,    43,    13,    32,    33,     0,     2,     3,     5,
       8,    14,    16,    18,    17,    15,    26,    27,     9,    10,
      11,    12,     0,    50,     0,    40,    36,    37,    38,    39,
      41,     0,     0,    55,    43,     0,     0,    45,     0,     1,
       0,     0,    52,     0,    48,     0,     0,     0,    58,    57,
       0,    50,    48,    45,    44,    42,     0,    34,     0,     6,
      51,    52,     6,     0,     0,     0,     0,     0,     0,    30,
      56,    58,    60,    31,     0,     0,     0,     0,    46,    35,
       0,     0,     7,    53,     4,     0,     0,    47,    28,     0,
       0,     0,    59,     0,     0,     0,     0,     0,    54,    24,
       0,    29,    62,    61,     0,    21,     0,     0,     0,    23,
      25,     0,    19,     0,    22,     0,    20
};

  /* YYPGOTO[NTERM-NUM].  */
static const yytype_int8 yypgoto[] =
{
     -64,   -64,     1,    13,    -1,   -64,   -64,   -64,   -64,   -64,
     -64,   -64,   -64,   -64,   -64,   -64,   -63,   -64,   -64,   -64,
      39,    17,    12,   -64,    28,    20,   -36,   -64,    23,    60,
      63
};

  /* YYDEFGOTO[NTERM-NUM].  */
static const yytype_int8 yydefgoto[] =
{
      -1,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    58,    35,    36,    21,
      38,    54,    64,    65,    41,    60,    42,    47,    70,    71,
      72
};

  /* YYTABLE[YYPACT[STATE-NUM]] -- What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule whose
     number is the opposite.  If YYTABLE_NINF, syntax error.  */
static const yytype_int8 yytable[] =
{
      34,    37,    87,    56,    22,    39,    61,    40,     2,    43,
       3,   -49,    44,    95,    75,    76,    96,    97,    46,    57,
      51,    34,    67,     4,     5,    61,    52,   -49,    55,    59,
      62,    45,    63,    37,   108,    69,    53,    73,    79,    86,
      93,    98,    99,   101,    66,    68,   102,   103,   105,    22,
      88,   106,    53,     2,   109,     3,   110,    80,   112,   113,
     114,   116,    85,    84,    77,    89,    90,    91,     4,     5,
      78,     1,    82,    50,    94,     2,     0,     3,    22,    74,
      34,    83,     2,    81,     3,   100,     0,     2,     0,     3,
       4,     5,   104,    48,    92,   107,    49,     4,     5,     0,
       0,     0,     4,     5,     0,   111,     0,    22,     0,     0,
       0,     2,   115,     3,    23,    24,    25,    26,    27,    28,
      29,    30,    31,    32,    33,    22,     4,     5,     0,     2,
       0,     3,    23,     0,    25,    26,    27,    28,    29,    30,
      31,    32,    33,    22,     4,     5,     0,     2,     0,     3,
       0,     0,    25,    26,    27,    28,    29,    30,    31,    32,
      33,     0,     4,     5
};

static const yytype_int8 yycheck[] =
{
       1,     2,    65,     7,     4,     0,    42,     4,     8,    25,
      10,     7,     4,    76,     5,     6,     5,     6,     4,    23,
       4,    22,    22,    23,    24,    61,     4,    23,     9,     5,
       5,    32,     5,    34,    97,     5,    37,     5,    23,     5,
       5,     5,     5,     5,    45,    46,     5,     5,     5,     4,
       5,     5,    53,     8,     5,    10,     5,    58,     5,     5,
       5,     5,    63,    62,    52,    66,    67,    68,    23,    24,
      53,     4,    59,    34,    75,     8,    -1,    10,     4,    51,
      81,    61,     8,     4,    10,    86,    -1,     8,    -1,    10,
      23,    24,    93,    33,    71,    96,    33,    23,    24,    -1,
      -1,    -1,    23,    24,    -1,   106,    -1,     4,    -1,    -1,
      -1,     8,   113,    10,    11,    12,    13,    14,    15,    16,
      17,    18,    19,    20,    21,     4,    23,    24,    -1,     8,
      -1,    10,    11,    -1,    13,    14,    15,    16,    17,    18,
      19,    20,    21,     4,    23,    24,    -1,     8,    -1,    10,
      -1,    -1,    13,    14,    15,    16,    17,    18,    19,    20,
      21,    -1,    23,    24
};

  /* YYSTOS[STATE-NUM] -- The (internal number of the) accessing
     symbol of state STATE-NUM.  */
static const yytype_uint8 yystos[] =
{
       0,     4,     8,    10,    23,    24,    27,    28,    29,    30,
      31,    32,    33,    34,    35,    36,    37,    38,    39,    40,
      41,    45,     4,    11,    12,    13,    14,    15,    16,    17,
      18,    19,    20,    21,    30,    43,    44,    30,    46,     0,
       4,    50,    52,    25,     4,    30,     4,    53,    55,    56,
      46,     4,     4,    30,    47,     9,     7,    23,    42,     5,
      51,    52,     5,     5,    48,    49,    30,    22,    30,     5,
      54,    55,    56,     5,    50,     5,     6,    48,    47,    23,
      30,     4,    29,    51,    28,    30,     5,    42,     5,    30,
      30,    30,    54,     5,    30,    42,     5,     6,     5,     5,
      30,     5,     5,     5,    30,     5,     5,    30,    42,     5,
       5,    30,     5,     5,     5,    30,     5
};

  /* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
static const yytype_uint8 yyr1[] =
{
       0,    26,    27,    28,    28,    29,    29,    29,    30,    30,
      30,    30,    30,    30,    31,    31,    32,    32,    32,    33,
      33,    33,    33,    34,    34,    35,    36,    36,    37,    37,
      38,    39,    40,    41,    42,    42,    43,    43,    43,    43,
      44,    44,    45,    46,    46,    47,    47,    48,    49,    49,
      50,    50,    51,    51,    52,    53,    53,    53,    54,    54,
      54,    55,    56
};

  /* YYR2[YYN] -- Number of symbols on the right hand side of rule YYN.  */
static const yytype_uint8 yyr2[] =
{
       0,     2,     1,     1,     5,     1,     0,     5,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     7,
       9,     6,     8,     7,     6,     7,     1,     1,     5,     6,
       4,     4,     1,     1,     1,     2,     1,     1,     1,     1,
       1,     1,     3,     0,     2,     0,     2,     2,     0,     2,
       0,     2,     0,     2,     4,     0,     2,     1,     0,     2,
       1,     4,     4
};


#define yyerrok         (yyerrstatus = 0)
#define yyclearin       (yychar = YYEMPTY)
#define YYEMPTY         (-2)
#define YYEOF           0

#define YYACCEPT        goto yyacceptlab
#define YYABORT         goto yyabortlab
#define YYERROR         goto yyerrorlab


#define YYRECOVERING()  (!!yyerrstatus)

#define YYBACKUP(Token, Value)                                  \
do                                                              \
  if (yychar == YYEMPTY)                                        \
    {                                                           \
      yychar = (Token);                                         \
      yylval = (Value);                                         \
      YYPOPSTACK (yylen);                                       \
      yystate = *yyssp;                                         \
      YY_LAC_DISCARD ("YYBACKUP");                              \
      goto yybackup;                                            \
    }                                                           \
  else                                                          \
    {                                                           \
      yyerror (YY_("syntax error: cannot back up")); \
      YYERROR;                                                  \
    }                                                           \
while (0)

/* Error token number */
#define YYTERROR        1
#define YYERRCODE       256



/* Enable debugging if requested.  */
#if YYDEBUG

# ifndef YYFPRINTF
#  include <stdio.h> /* INFRINGES ON USER NAME SPACE */
#  define YYFPRINTF fprintf
# endif

# define YYDPRINTF(Args)                        \
do {                                            \
  if (yydebug)                                  \
    YYFPRINTF Args;                             \
} while (0)

/* This macro is provided for backward compatibility. */
#ifndef YY_LOCATION_PRINT
# define YY_LOCATION_PRINT(File, Loc) ((void) 0)
#endif


# define YY_SYMBOL_PRINT(Title, Type, Value, Location)                    \
do {                                                                      \
  if (yydebug)                                                            \
    {                                                                     \
      YYFPRINTF (stderr, "%s ", Title);                                   \
      yy_symbol_print (stderr,                                            \
                  Type, Value); \
      YYFPRINTF (stderr, "\n");                                           \
    }                                                                     \
} while (0)


/*----------------------------------------.
| Print this symbol's value on YYOUTPUT.  |
`----------------------------------------*/

static void
yy_symbol_value_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep)
{
  FILE *yyo = yyoutput;
  YYUSE (yyo);
  if (!yyvaluep)
    return;
# ifdef YYPRINT
  if (yytype < YYNTOKENS)
    YYPRINT (yyoutput, yytoknum[yytype], *yyvaluep);
# endif
  YYUSE (yytype);
}


/*--------------------------------.
| Print this symbol on YYOUTPUT.  |
`--------------------------------*/

static void
yy_symbol_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep)
{
  YYFPRINTF (yyoutput, "%s %s (",
             yytype < YYNTOKENS ? "token" : "nterm", yytname[yytype]);

  yy_symbol_value_print (yyoutput, yytype, yyvaluep);
  YYFPRINTF (yyoutput, ")");
}

/*------------------------------------------------------------------.
| yy_stack_print -- Print the state stack from its BOTTOM up to its |
| TOP (included).                                                   |
`------------------------------------------------------------------*/

static void
yy_stack_print (yytype_int16 *yybottom, yytype_int16 *yytop)
{
  YYFPRINTF (stderr, "Stack now");
  for (; yybottom <= yytop; yybottom++)
    {
      int yybot = *yybottom;
      YYFPRINTF (stderr, " %d", yybot);
    }
  YYFPRINTF (stderr, "\n");
}

# define YY_STACK_PRINT(Bottom, Top)                            \
do {                                                            \
  if (yydebug)                                                  \
    yy_stack_print ((Bottom), (Top));                           \
} while (0)


/*------------------------------------------------.
| Report that the YYRULE is going to be reduced.  |
`------------------------------------------------*/

static void
yy_reduce_print (yytype_int16 *yyssp, YYSTYPE *yyvsp, int yyrule)
{
  unsigned long int yylno = yyrline[yyrule];
  int yynrhs = yyr2[yyrule];
  int yyi;
  YYFPRINTF (stderr, "Reducing stack by rule %d (line %lu):\n",
             yyrule - 1, yylno);
  /* The symbols being reduced.  */
  for (yyi = 0; yyi < yynrhs; yyi++)
    {
      YYFPRINTF (stderr, "   $%d = ", yyi + 1);
      yy_symbol_print (stderr,
                       yystos[yyssp[yyi + 1 - yynrhs]],
                       &(yyvsp[(yyi + 1) - (yynrhs)])
                                              );
      YYFPRINTF (stderr, "\n");
    }
}

# define YY_REDUCE_PRINT(Rule)          \
do {                                    \
  if (yydebug)                          \
    yy_reduce_print (yyssp, yyvsp, Rule); \
} while (0)

/* Nonzero means print parse trace.  It is left uninitialized so that
   multiple parsers can coexist.  */
int yydebug;
#else /* !YYDEBUG */
# define YYDPRINTF(Args)
# define YY_SYMBOL_PRINT(Title, Type, Value, Location)
# define YY_STACK_PRINT(Bottom, Top)
# define YY_REDUCE_PRINT(Rule)
#endif /* !YYDEBUG */


/* YYINITDEPTH -- initial size of the parser's stacks.  */
#ifndef YYINITDEPTH
# define YYINITDEPTH 200
#endif

/* YYMAXDEPTH -- maximum size the stacks can grow to (effective only
   if the built-in stack extension method is used).

   Do not make this value too large; the results are undefined if
   YYSTACK_ALLOC_MAXIMUM < YYSTACK_BYTES (YYMAXDEPTH)
   evaluated with infinite-precision integer arithmetic.  */

#ifndef YYMAXDEPTH
# define YYMAXDEPTH 10000
#endif

/* Given a state stack such that *YYBOTTOM is its bottom, such that
   *YYTOP is either its top or is YYTOP_EMPTY to indicate an empty
   stack, and such that *YYCAPACITY is the maximum number of elements it
   can hold without a reallocation, make sure there is enough room to
   store YYADD more elements.  If not, allocate a new stack using
   YYSTACK_ALLOC, copy the existing elements, and adjust *YYBOTTOM,
   *YYTOP, and *YYCAPACITY to reflect the new capacity and memory
   location.  If *YYBOTTOM != YYBOTTOM_NO_FREE, then free the old stack
   using YYSTACK_FREE.  Return 0 if successful or if no reallocation is
   required.  Return 1 if memory is exhausted.  */
static int
yy_lac_stack_realloc (YYSIZE_T *yycapacity, YYSIZE_T yyadd,
#if YYDEBUG
                      char const *yydebug_prefix,
                      char const *yydebug_suffix,
#endif
                      yytype_int16 **yybottom,
                      yytype_int16 *yybottom_no_free,
                      yytype_int16 **yytop, yytype_int16 *yytop_empty)
{
  YYSIZE_T yysize_old =
    *yytop == yytop_empty ? 0 : *yytop - *yybottom + 1;
  YYSIZE_T yysize_new = yysize_old + yyadd;
  if (*yycapacity < yysize_new)
    {
      YYSIZE_T yyalloc = 2 * yysize_new;
      yytype_int16 *yybottom_new;
      /* Use YYMAXDEPTH for maximum stack size given that the stack
         should never need to grow larger than the main state stack
         needs to grow without LAC.  */
      if (YYMAXDEPTH < yysize_new)
        {
          YYDPRINTF ((stderr, "%smax size exceeded%s", yydebug_prefix,
                      yydebug_suffix));
          return 1;
        }
      if (YYMAXDEPTH < yyalloc)
        yyalloc = YYMAXDEPTH;
      yybottom_new =
        (yytype_int16*) YYSTACK_ALLOC (yyalloc * sizeof *yybottom_new);
      if (!yybottom_new)
        {
          YYDPRINTF ((stderr, "%srealloc failed%s", yydebug_prefix,
                      yydebug_suffix));
          return 1;
        }
      if (*yytop != yytop_empty)
        {
          YYCOPY (yybottom_new, *yybottom, yysize_old);
          *yytop = yybottom_new + (yysize_old - 1);
        }
      if (*yybottom != yybottom_no_free)
        YYSTACK_FREE (*yybottom);
      *yybottom = yybottom_new;
      *yycapacity = yyalloc;
    }
  return 0;
}

/* Establish the initial context for the current lookahead if no initial
   context is currently established.

   We define a context as a snapshot of the parser stacks.  We define
   the initial context for a lookahead as the context in which the
   parser initially examines that lookahead in order to select a
   syntactic action.  Thus, if the lookahead eventually proves
   syntactically unacceptable (possibly in a later context reached via a
   series of reductions), the initial context can be used to determine
   the exact set of tokens that would be syntactically acceptable in the
   lookahead's place.  Moreover, it is the context after which any
   further semantic actions would be erroneous because they would be
   determined by a syntactically unacceptable token.

   YY_LAC_ESTABLISH should be invoked when a reduction is about to be
   performed in an inconsistent state (which, for the purposes of LAC,
   includes consistent states that don't know they're consistent because
   their default reductions have been disabled).  Iff there is a
   lookahead token, it should also be invoked before reporting a syntax
   error.  This latter case is for the sake of the debugging output.

   For parse.lac=full, the implementation of YY_LAC_ESTABLISH is as
   follows.  If no initial context is currently established for the
   current lookahead, then check if that lookahead can eventually be
   shifted if syntactic actions continue from the current context.
   Report a syntax error if it cannot.  */
#define YY_LAC_ESTABLISH                                         \
do {                                                             \
  if (!yy_lac_established)                                       \
    {                                                            \
      YYDPRINTF ((stderr,                                        \
                  "LAC: initial context established for %s\n",   \
                  yytname[yytoken]));                            \
      yy_lac_established = 1;                                    \
      {                                                          \
        int yy_lac_status =                                      \
          yy_lac (yyesa, &yyes, &yyes_capacity, yyssp, yytoken); \
        if (yy_lac_status == 2)                                  \
          goto yyexhaustedlab;                                   \
        if (yy_lac_status == 1)                                  \
          goto yyerrlab;                                         \
      }                                                          \
    }                                                            \
} while (0)

/* Discard any previous initial lookahead context because of Event,
   which may be a lookahead change or an invalidation of the currently
   established initial context for the current lookahead.

   The most common example of a lookahead change is a shift.  An example
   of both cases is syntax error recovery.  That is, a syntax error
   occurs when the lookahead is syntactically erroneous for the
   currently established initial context, so error recovery manipulates
   the parser stacks to try to find a new initial context in which the
   current lookahead is syntactically acceptable.  If it fails to find
   such a context, it discards the lookahead.  */
#if YYDEBUG
# define YY_LAC_DISCARD(Event)                                           \
do {                                                                     \
  if (yy_lac_established)                                                \
    {                                                                    \
      if (yydebug)                                                       \
        YYFPRINTF (stderr, "LAC: initial context discarded due to "      \
                   Event "\n");                                          \
      yy_lac_established = 0;                                            \
    }                                                                    \
} while (0)
#else
# define YY_LAC_DISCARD(Event) yy_lac_established = 0
#endif

/* Given the stack whose top is *YYSSP, return 0 iff YYTOKEN can
   eventually (after perhaps some reductions) be shifted, return 1 if
   not, or return 2 if memory is exhausted.  As preconditions and
   postconditions: *YYES_CAPACITY is the allocated size of the array to
   which *YYES points, and either *YYES = YYESA or *YYES points to an
   array allocated with YYSTACK_ALLOC.  yy_lac may overwrite the
   contents of either array, alter *YYES and *YYES_CAPACITY, and free
   any old *YYES other than YYESA.  */
static int
yy_lac (yytype_int16 *yyesa, yytype_int16 **yyes,
        YYSIZE_T *yyes_capacity, yytype_int16 *yyssp, int yytoken)
{
  yytype_int16 *yyes_prev = yyssp;
  yytype_int16 *yyesp = yyes_prev;
  YYDPRINTF ((stderr, "LAC: checking lookahead %s:", yytname[yytoken]));
  if (yytoken == YYUNDEFTOK)
    {
      YYDPRINTF ((stderr, " Always Err\n"));
      return 1;
    }
  while (1)
    {
      int yyrule = yypact[*yyesp];
      if (yypact_value_is_default (yyrule)
          || (yyrule += yytoken) < 0 || YYLAST < yyrule
          || yycheck[yyrule] != yytoken)
        {
          yyrule = yydefact[*yyesp];
          if (yyrule == 0)
            {
              YYDPRINTF ((stderr, " Err\n"));
              return 1;
            }
        }
      else
        {
          yyrule = yytable[yyrule];
          if (yytable_value_is_error (yyrule))
            {
              YYDPRINTF ((stderr, " Err\n"));
              return 1;
            }
          if (0 < yyrule)
            {
              YYDPRINTF ((stderr, " S%d\n", yyrule));
              return 0;
            }
          yyrule = -yyrule;
        }
      {
        YYSIZE_T yylen = yyr2[yyrule];
        YYDPRINTF ((stderr, " R%d", yyrule - 1));
        if (yyesp != yyes_prev)
          {
            YYSIZE_T yysize = yyesp - *yyes + 1;
            if (yylen < yysize)
              {
                yyesp -= yylen;
                yylen = 0;
              }
            else
              {
                yylen -= yysize;
                yyesp = yyes_prev;
              }
          }
        if (yylen)
          yyesp = yyes_prev -= yylen;
      }
      {
        int yystate;
        {
          int yylhs = yyr1[yyrule] - YYNTOKENS;
          yystate = yypgoto[yylhs] + *yyesp;
          if (yystate < 0 || YYLAST < yystate
              || yycheck[yystate] != *yyesp)
            yystate = yydefgoto[yylhs];
          else
            yystate = yytable[yystate];
        }
        if (yyesp == yyes_prev)
          {
            yyesp = *yyes;
            *yyesp = yystate;
          }
        else
          {
            if (yy_lac_stack_realloc (yyes_capacity, 1,
#if YYDEBUG
                                      " (", ")",
#endif
                                      yyes, yyesa, &yyesp, yyes_prev))
              {
                YYDPRINTF ((stderr, "\n"));
                return 2;
              }
            *++yyesp = yystate;
          }
        YYDPRINTF ((stderr, " G%d", yystate));
      }
    }
}


#if YYERROR_VERBOSE

# ifndef yystrlen
#  if defined __GLIBC__ && defined _STRING_H
#   define yystrlen strlen
#  else
/* Return the length of YYSTR.  */
static YYSIZE_T
yystrlen (const char *yystr)
{
  YYSIZE_T yylen;
  for (yylen = 0; yystr[yylen]; yylen++)
    continue;
  return yylen;
}
#  endif
# endif

# ifndef yystpcpy
#  if defined __GLIBC__ && defined _STRING_H && defined _GNU_SOURCE
#   define yystpcpy stpcpy
#  else
/* Copy YYSRC to YYDEST, returning the address of the terminating '\0' in
   YYDEST.  */
static char *
yystpcpy (char *yydest, const char *yysrc)
{
  char *yyd = yydest;
  const char *yys = yysrc;

  while ((*yyd++ = *yys++) != '\0')
    continue;

  return yyd - 1;
}
#  endif
# endif

# ifndef yytnamerr
/* Copy to YYRES the contents of YYSTR after stripping away unnecessary
   quotes and backslashes, so that it's suitable for yyerror.  The
   heuristic is that double-quoting is unnecessary unless the string
   contains an apostrophe, a comma, or backslash (other than
   backslash-backslash).  YYSTR is taken from yytname.  If YYRES is
   null, do not copy; instead, return the length of what the result
   would have been.  */
static YYSIZE_T
yytnamerr (char *yyres, const char *yystr)
{
  if (*yystr == '"')
    {
      YYSIZE_T yyn = 0;
      char const *yyp = yystr;

      for (;;)
        switch (*++yyp)
          {
          case '\'':
          case ',':
            goto do_not_strip_quotes;

          case '\\':
            if (*++yyp != '\\')
              goto do_not_strip_quotes;
            /* Fall through.  */
          default:
            if (yyres)
              yyres[yyn] = *yyp;
            yyn++;
            break;

          case '"':
            if (yyres)
              yyres[yyn] = '\0';
            return yyn;
          }
    do_not_strip_quotes: ;
    }

  if (! yyres)
    return yystrlen (yystr);

  return yystpcpy (yyres, yystr) - yyres;
}
# endif

/* Copy into *YYMSG, which is of size *YYMSG_ALLOC, an error message
   about the unexpected token YYTOKEN for the state stack whose top is
   YYSSP.  In order to see if a particular token T is a
   valid looakhead, invoke yy_lac (YYESA, YYES, YYES_CAPACITY, YYSSP, T).

   Return 0 if *YYMSG was successfully written.  Return 1 if *YYMSG is
   not large enough to hold the message.  In that case, also set
   *YYMSG_ALLOC to the required number of bytes.  Return 2 if the
   required number of bytes is too large to store or if
   yy_lac returned 2.  */
static int
yysyntax_error (YYSIZE_T *yymsg_alloc, char **yymsg,
                yytype_int16 *yyesa, yytype_int16 **yyes,
                YYSIZE_T *yyes_capacity, yytype_int16 *yyssp, int yytoken)
{
  YYSIZE_T yysize0 = yytnamerr (YY_NULLPTR, yytname[yytoken]);
  YYSIZE_T yysize = yysize0;
  enum { YYERROR_VERBOSE_ARGS_MAXIMUM = 5 };
  /* Internationalized format string. */
  const char *yyformat = YY_NULLPTR;
  /* Arguments of yyformat. */
  char const *yyarg[YYERROR_VERBOSE_ARGS_MAXIMUM];
  /* Number of reported tokens (one for the "unexpected", one per
     "expected"). */
  int yycount = 0;

  /* There are many possibilities here to consider:
     - If this state is a consistent state with a default action, then
       the only way this function was invoked is if the default action
       is an error action.  In that case, don't check for expected
       tokens because there are none.
     - The only way there can be no lookahead present (in yychar) is if
       this state is a consistent state with a default action.  Thus,
       detecting the absence of a lookahead is sufficient to determine
       that there is no unexpected or expected token to report.  In that
       case, just report a simple "syntax error".
     - Don't assume there isn't a lookahead just because this state is a
       consistent state with a default action.  There might have been a
       previous inconsistent state, consistent state with a non-default
       action, or user semantic action that manipulated yychar.
       In the first two cases, it might appear that the current syntax
       error should have been detected in the previous state when yy_lac
       was invoked.  However, at that time, there might have been a
       different syntax error that discarded a different initial context
       during error recovery, leaving behind the current lookahead.
  */
  if (yytoken != YYEMPTY)
    {
      int yyn = yypact[*yyssp];
      YYDPRINTF ((stderr, "Constructing syntax error message\n"));
      yyarg[yycount++] = yytname[yytoken];
      if (!yypact_value_is_default (yyn))
        {
          int yyx;

          for (yyx = 0; yyx < YYNTOKENS; ++yyx)
            if (yyx != YYTERROR && yyx != YYUNDEFTOK)
              {
                {
                  int yy_lac_status = yy_lac (yyesa, yyes, yyes_capacity,
                                              yyssp, yyx);
                  if (yy_lac_status == 2)
                    return 2;
                  if (yy_lac_status == 1)
                    continue;
                }
                if (yycount == YYERROR_VERBOSE_ARGS_MAXIMUM)
                  {
                    yycount = 1;
                    yysize = yysize0;
                    break;
                  }
                yyarg[yycount++] = yytname[yyx];
                {
                  YYSIZE_T yysize1 = yysize + yytnamerr (YY_NULLPTR, yytname[yyx]);
                  if (! (yysize <= yysize1
                         && yysize1 <= YYSTACK_ALLOC_MAXIMUM))
                    return 2;
                  yysize = yysize1;
                }
              }
        }
# if YYDEBUG
      else if (yydebug)
        YYFPRINTF (stderr, "No expected tokens.\n");
# endif
    }

  switch (yycount)
    {
# define YYCASE_(N, S)                      \
      case N:                               \
        yyformat = S;                       \
      break
      YYCASE_(0, YY_("syntax error"));
      YYCASE_(1, YY_("syntax error, unexpected %s"));
      YYCASE_(2, YY_("syntax error, unexpected %s, expecting %s"));
      YYCASE_(3, YY_("syntax error, unexpected %s, expecting %s or %s"));
      YYCASE_(4, YY_("syntax error, unexpected %s, expecting %s or %s or %s"));
      YYCASE_(5, YY_("syntax error, unexpected %s, expecting %s or %s or %s or %s"));
# undef YYCASE_
    }

  {
    YYSIZE_T yysize1 = yysize + yystrlen (yyformat);
    if (! (yysize <= yysize1 && yysize1 <= YYSTACK_ALLOC_MAXIMUM))
      return 2;
    yysize = yysize1;
  }

  if (*yymsg_alloc < yysize)
    {
      *yymsg_alloc = 2 * yysize;
      if (! (yysize <= *yymsg_alloc
             && *yymsg_alloc <= YYSTACK_ALLOC_MAXIMUM))
        *yymsg_alloc = YYSTACK_ALLOC_MAXIMUM;
      return 1;
    }

  /* Avoid sprintf, as that infringes on the user's name space.
     Don't have undefined behavior even if the translation
     produced a string with the wrong number of "%s"s.  */
  {
    char *yyp = *yymsg;
    int yyi = 0;
    while ((*yyp = *yyformat) != '\0')
      if (*yyp == '%' && yyformat[1] == 's' && yyi < yycount)
        {
          yyp += yytnamerr (yyp, yyarg[yyi++]);
          yyformat += 2;
        }
      else
        {
          yyp++;
          yyformat++;
        }
  }
  return 0;
}
#endif /* YYERROR_VERBOSE */

/*-----------------------------------------------.
| Release the memory associated to this symbol.  |
`-----------------------------------------------*/

static void
yydestruct (const char *yymsg, int yytype, YYSTYPE *yyvaluep)
{
  YYUSE (yyvaluep);
  if (!yymsg)
    yymsg = "Deleting";
  YY_SYMBOL_PRINT (yymsg, yytype, yyvaluep, yylocationp);

  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  YYUSE (yytype);
  YY_IGNORE_MAYBE_UNINITIALIZED_END
}




/* The lookahead symbol.  */
int yychar;

/* The semantic value of the lookahead symbol.  */
YYSTYPE yylval;
/* Number of syntax errors so far.  */
int yynerrs;


/*----------.
| yyparse.  |
`----------*/

int
yyparse (void)
{
    int yystate;
    /* Number of tokens to shift before error messages enabled.  */
    int yyerrstatus;

    /* The stacks and their tools:
       'yyss': related to states.
       'yyvs': related to semantic values.

       Refer to the stacks through separate pointers, to allow yyoverflow
       to reallocate them elsewhere.  */

    /* The state stack.  */
    yytype_int16 yyssa[YYINITDEPTH];
    yytype_int16 *yyss;
    yytype_int16 *yyssp;

    /* The semantic value stack.  */
    YYSTYPE yyvsa[YYINITDEPTH];
    YYSTYPE *yyvs;
    YYSTYPE *yyvsp;

    YYSIZE_T yystacksize;

    yytype_int16 yyesa[20];
    yytype_int16 *yyes;
    YYSIZE_T yyes_capacity;

  int yy_lac_established = 0;
  int yyn;
  int yyresult;
  /* Lookahead token as an internal (translated) token number.  */
  int yytoken = 0;
  /* The variables used to return semantic value and location from the
     action routines.  */
  YYSTYPE yyval;

#if YYERROR_VERBOSE
  /* Buffer for error messages, and its allocated size.  */
  char yymsgbuf[128];
  char *yymsg = yymsgbuf;
  YYSIZE_T yymsg_alloc = sizeof yymsgbuf;
#endif

#define YYPOPSTACK(N)   (yyvsp -= (N), yyssp -= (N))

  /* The number of symbols on the RHS of the reduced rule.
     Keep to zero when no symbol should be popped.  */
  int yylen = 0;

  yyssp = yyss = yyssa;
  yyvsp = yyvs = yyvsa;
  yystacksize = YYINITDEPTH;

  yyes = yyesa;
  yyes_capacity = sizeof yyesa / sizeof *yyes;
  if (YYMAXDEPTH < yyes_capacity)
    yyes_capacity = YYMAXDEPTH;

  YYDPRINTF ((stderr, "Starting parse\n"));

  yystate = 0;
  yyerrstatus = 0;
  yynerrs = 0;
  yychar = YYEMPTY; /* Cause a token to be read.  */
  goto yysetstate;

/*------------------------------------------------------------.
| yynewstate -- Push a new state, which is found in yystate.  |
`------------------------------------------------------------*/
 yynewstate:
  /* In all cases, when you get here, the value and location stacks
     have just been pushed.  So pushing a state here evens the stacks.  */
  yyssp++;

 yysetstate:
  *yyssp = yystate;

  if (yyss + yystacksize - 1 <= yyssp)
    {
      /* Get the current used size of the three stacks, in elements.  */
      YYSIZE_T yysize = yyssp - yyss + 1;

#ifdef yyoverflow
      {
        /* Give user a chance to reallocate the stack.  Use copies of
           these so that the &'s don't force the real ones into
           memory.  */
        YYSTYPE *yyvs1 = yyvs;
        yytype_int16 *yyss1 = yyss;

        /* Each stack pointer address is followed by the size of the
           data in use in that stack, in bytes.  This used to be a
           conditional around just the two extra args, but that might
           be undefined if yyoverflow is a macro.  */
        yyoverflow (YY_("memory exhausted"),
                    &yyss1, yysize * sizeof (*yyssp),
                    &yyvs1, yysize * sizeof (*yyvsp),
                    &yystacksize);

        yyss = yyss1;
        yyvs = yyvs1;
      }
#else /* no yyoverflow */
# ifndef YYSTACK_RELOCATE
      goto yyexhaustedlab;
# else
      /* Extend the stack our own way.  */
      if (YYMAXDEPTH <= yystacksize)
        goto yyexhaustedlab;
      yystacksize *= 2;
      if (YYMAXDEPTH < yystacksize)
        yystacksize = YYMAXDEPTH;

      {
        yytype_int16 *yyss1 = yyss;
        union yyalloc *yyptr =
          (union yyalloc *) YYSTACK_ALLOC (YYSTACK_BYTES (yystacksize));
        if (! yyptr)
          goto yyexhaustedlab;
        YYSTACK_RELOCATE (yyss_alloc, yyss);
        YYSTACK_RELOCATE (yyvs_alloc, yyvs);
#  undef YYSTACK_RELOCATE
        if (yyss1 != yyssa)
          YYSTACK_FREE (yyss1);
      }
# endif
#endif /* no yyoverflow */

      yyssp = yyss + yysize - 1;
      yyvsp = yyvs + yysize - 1;

      YYDPRINTF ((stderr, "Stack size increased to %lu\n",
                  (unsigned long int) yystacksize));

      if (yyss + yystacksize - 1 <= yyssp)
        YYABORT;
    }

  YYDPRINTF ((stderr, "Entering state %d\n", yystate));

  if (yystate == YYFINAL)
    YYACCEPT;

  goto yybackup;

/*-----------.
| yybackup.  |
`-----------*/
yybackup:

  /* Do appropriate processing given the current state.  Read a
     lookahead token if we need one and don't already have one.  */

  /* First try to decide what to do without reference to lookahead token.  */
  yyn = yypact[yystate];
  if (yypact_value_is_default (yyn))
    goto yydefault;

  /* Not known => get a lookahead token if don't already have one.  */

  /* YYCHAR is either YYEMPTY or YYEOF or a valid lookahead symbol.  */
  if (yychar == YYEMPTY)
    {
      YYDPRINTF ((stderr, "Reading a token: "));
      yychar = yylex ();
    }

  if (yychar <= YYEOF)
    {
      yychar = yytoken = YYEOF;
      YYDPRINTF ((stderr, "Now at end of input.\n"));
    }
  else
    {
      yytoken = YYTRANSLATE (yychar);
      YY_SYMBOL_PRINT ("Next token is", yytoken, &yylval, &yylloc);
    }

  /* If the proper action on seeing token YYTOKEN is to reduce or to
     detect an error, take that action.  */
  yyn += yytoken;
  if (yyn < 0 || YYLAST < yyn || yycheck[yyn] != yytoken)
    {
      YY_LAC_ESTABLISH;
      goto yydefault;
    }
  yyn = yytable[yyn];
  if (yyn <= 0)
    {
      if (yytable_value_is_error (yyn))
        goto yyerrlab;
      YY_LAC_ESTABLISH;
      yyn = -yyn;
      goto yyreduce;
    }

  /* Count tokens shifted since error; after three, turn off error
     status.  */
  if (yyerrstatus)
    yyerrstatus--;

  /* Shift the lookahead token.  */
  YY_SYMBOL_PRINT ("Shifting", yytoken, &yylval, &yylloc);

  /* Discard the shifted token.  */
  yychar = YYEMPTY;
  YY_LAC_DISCARD ("shift");

  yystate = yyn;
  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  *++yyvsp = yylval;
  YY_IGNORE_MAYBE_UNINITIALIZED_END

  goto yynewstate;


/*-----------------------------------------------------------.
| yydefault -- do the default action for the current state.  |
`-----------------------------------------------------------*/
yydefault:
  yyn = yydefact[yystate];
  if (yyn == 0)
    goto yyerrlab;
  goto yyreduce;


/*-----------------------------.
| yyreduce -- Do a reduction.  |
`-----------------------------*/
yyreduce:
  /* yyn is the number of a rule to reduce with.  */
  yylen = yyr2[yyn];

  /* If YYLEN is nonzero, implement the default value of the action:
     '$$ = $1'.

     Otherwise, the following line sets YYVAL to garbage.
     This behavior is undocumented and Bison
     users should not rely upon it.  Assigning to YYVAL
     unconditionally makes the parser a bit smaller, and it avoids a
     GCC warning that YYVAL may be used uninitialized.  */
  yyval = yyvsp[1-yylen];


  YY_REDUCE_PRINT (yyn);
  {
    int yychar_backup = yychar;
    switch (yyn)
      {
          case 2:
#line 100 "FLSC2SC.y" /* yacc.c:1646  */
    { printwords(concat((yyvsp[0].ptr), cons("\n", NULL))->start); }
#line 1566 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 4:
#line 104 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = concat(cons("FLSC_Require(", cons((yyvsp[-2].str), cons(", ",
			lnbrk(1, (yyvsp[0].ptr))))),
			lnbrk(-1, cons(").setLines(", cons((yyvsp[-4].str), cons(", ", cons((yyvsp[-1].str), cons(")" , NULL))))))); }
#line 1574 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 6:
#line 109 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = cons("nil", NULL); }
#line 1580 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 7:
#line 111 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = concat(concat(cons("FLSC_Define([",
			lnbrk(1, (yyvsp[-2].ptr))),
			lnbrk(0, cons("],",
			lnbrk(0, (yyvsp[0].ptr))))),
			lnbrk(-1, cons(").setLines(", cons((yyvsp[-4].str), cons(", ", cons((yyvsp[-1].str), cons(")" , NULL))))))); }
#line 1590 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 13:
#line 118 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = cons("FLSC_Nil()", NULL); }
#line 1596 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 19:
#line 125 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = concat(concat(concat((yyvsp[-5].ptr), (yyvsp[-3].ptr)), cons("],",
			lnbrk(1, (yyvsp[-1].ptr)))),
			lnbrk(-1, cons(").setLines(", cons((yyvsp[-6].str), cons(", ", cons((yyvsp[0].str), cons(")" , NULL))))))); }
#line 1604 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 20:
#line 129 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = concat(concat(concat(concat((yyvsp[-7].ptr), (yyvsp[-5].ptr)), cons(",", (yyvsp[-3].ptr))), cons("],",
			lnbrk(1, (yyvsp[-1].ptr)))), cons(",",
			lnbrk(-1, cons("true).setLines(", cons((yyvsp[-8].str), cons(", ", cons((yyvsp[0].str), cons(")" , NULL)))))))); }
#line 1612 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 21:
#line 133 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = concat(concat((yyvsp[-4].ptr), cons("],",
			lnbrk(1, (yyvsp[-1].ptr)))),
			lnbrk(-1, cons(").setLines(", cons((yyvsp[-5].str), cons(", ", cons((yyvsp[0].str), cons(")" , NULL))))))); }
#line 1620 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 22:
#line 137 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = concat(concat(concat((yyvsp[-6].ptr), (yyvsp[-3].ptr)), cons("],",
			lnbrk(1, (yyvsp[-1].ptr)))), cons(",",
			lnbrk(-1, cons("true).setLines(", cons((yyvsp[-7].str), cons(", ", cons((yyvsp[0].str), cons(")" , NULL)))))))); }
#line 1628 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 23:
#line 142 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = concat(concat(cons("FLSC_Module([", (yyvsp[-3].ptr)), cons("],",
			lnbrk(1, (yyvsp[-1].ptr)))),
			lnbrk(-1, cons(").setLines(", cons((yyvsp[-6].str), cons(", ", cons((yyvsp[0].str), cons(")" , NULL))))))); }
#line 1636 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 24:
#line 146 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = concat(cons("FLSC_Module([],",
			lnbrk(1, (yyvsp[-1].ptr))),
			lnbrk(-1, cons(").setLines(", cons((yyvsp[-5].str), cons(", ", cons((yyvsp[0].str), cons(")" , NULL))))))); }
#line 1644 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 25:
#line 151 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = concat(concat(concat((yyvsp[-5].ptr),
			lnbrk(1, (yyvsp[-3].ptr))),
			lnbrk(0, cons("],",
			lnbrk(0, (yyvsp[-1].ptr))))),
			lnbrk(-1, cons(").setLines(", cons((yyvsp[-6].str), cons(", ", cons((yyvsp[0].str), cons(")" , NULL))))))); }
#line 1654 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 28:
#line 160 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = concat(concat(cons("FLSC_If(",
			lnbrk(1, (yyvsp[-2].ptr))), cons(",",
			lnbrk(0, (yyvsp[-1].ptr)))),
			lnbrk(-1, cons(").setLines(", cons((yyvsp[-4].str), cons(", ", cons((yyvsp[0].str), cons(")" , NULL))))))); }
#line 1663 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 29:
#line 165 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = concat(concat(concat(cons("FLSC_If(",
			lnbrk(1, (yyvsp[-3].ptr))), cons(",",
			lnbrk(0, (yyvsp[-2].ptr)))), cons(",",
			lnbrk(0, (yyvsp[-1].ptr)))),
			lnbrk(-1, cons(").setLines(", cons((yyvsp[-5].str), cons(", ", cons((yyvsp[0].str), cons(")" , NULL))))))); }
#line 1673 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 30:
#line 172 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = concat(cons("FLSC_Cond([",
			lnbrk(1, (yyvsp[-1].ptr))),
			lnbrk(-1, cons("]).setLines(", cons((yyvsp[-3].str), cons(", ", cons((yyvsp[0].str), cons(")" , NULL))))))); }
#line 1681 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 31:
#line 177 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = concat(concat(cons("FLSC_Call(",
			lnbrk(1, (yyvsp[-2].ptr))), cons(",[",
			lnbrk(1, (yyvsp[-1].ptr)))) ,
			lnbrk(-2, cons("]).setLines(", cons((yyvsp[-3].str), cons(", ", cons((yyvsp[0].str), cons(")" , NULL))))))); }
#line 1690 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 32:
#line 182 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = cons("FLSC_Var('", cons((yyvsp[0].str), cons("')" , NULL))); }
#line 1696 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 33:
#line 184 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = cons("FLSC_Num(", cons((yyvsp[0].str), cons(")" , NULL))); }
#line 1702 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 34:
#line 186 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = cons("'", cons((yyvsp[0].str), cons("'", NULL))); }
#line 1708 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 35:
#line 187 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = cons("FLSC_NonUnique('", cons((yyvsp[0].str), cons("')", NULL))); }
#line 1714 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 36:
#line 189 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = cons("FLSC_Let([", NULL); }
#line 1720 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 37:
#line 190 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = cons("FLSC_LetRec([", NULL); }
#line 1726 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 38:
#line 191 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = cons("FLSC_LetStar([", NULL); }
#line 1732 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 39:
#line 192 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = cons("FLSC_NoWarp([", NULL); }
#line 1738 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 40:
#line 194 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = cons("FLSC_Lambda([", NULL); }
#line 1744 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 41:
#line 195 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = cons("FLSC_Patch([", NULL); }
#line 1750 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 42:
#line 198 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = concat(cons("FLSC_List([",
			lnbrk(1, (yyvsp[-1].ptr))),
			lnbrk(-1, cons("]).setLines(", cons((yyvsp[-2].str), cons(", ", cons((yyvsp[0].str), cons(")" , NULL))))))); }
#line 1758 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 43:
#line 202 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = cons("", NULL); }
#line 1764 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 44:
#line 203 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = concat((yyvsp[-1].ptr), (yyvsp[0].ptr)); }
#line 1770 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 45:
#line 205 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = cons("", NULL); }
#line 1776 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 46:
#line 206 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = concat(cons(",", lnbrk(0, (yyvsp[-1].ptr))), (yyvsp[0].ptr)); }
#line 1782 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 47:
#line 209 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = concat((yyvsp[-1].ptr), (yyvsp[0].ptr)); }
#line 1788 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 48:
#line 211 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = cons("", NULL); }
#line 1794 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 49:
#line 212 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = concat(concat((yyvsp[-1].ptr), (yyvsp[0].ptr)), cons(",", NULL)); }
#line 1800 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 50:
#line 214 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = cons("", NULL); }
#line 1806 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 51:
#line 215 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = concat((yyvsp[-1].ptr), (yyvsp[0].ptr)); }
#line 1812 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 52:
#line 217 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = cons("", NULL); }
#line 1818 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 53:
#line 218 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = concat(cons(",", lnbrk(0, (yyvsp[-1].ptr))), (yyvsp[0].ptr)); }
#line 1824 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 54:
#line 220 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = concat(concat(cons("[", (yyvsp[-2].ptr)), cons(",", (yyvsp[-1].ptr))), cons("]", NULL)); }
#line 1830 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 55:
#line 222 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = cons("", NULL); }
#line 1836 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 56:
#line 223 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = concat((yyvsp[-1].ptr), (yyvsp[0].ptr)); }
#line 1842 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 57:
#line 224 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = (yyvsp[0].ptr); }
#line 1848 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 58:
#line 226 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = cons("", NULL); }
#line 1854 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 59:
#line 227 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = concat(cons(",", lnbrk(0, (yyvsp[-1].ptr))), (yyvsp[0].ptr)); }
#line 1860 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 60:
#line 228 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = cons(",", lnbrk(0, (yyvsp[0].ptr))); }
#line 1866 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 61:
#line 230 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = concat(concat(cons("[", (yyvsp[-2].ptr)), cons(",", (yyvsp[-1].ptr))), cons("]", NULL)); }
#line 1872 "flsc2sc.c" /* yacc.c:1646  */
    break;

  case 62:
#line 232 "FLSC2SC.y" /* yacc.c:1646  */
    { (yyval.ptr) = concat(cons("[FLSC_Else(),", (yyvsp[-1].ptr)), cons("]", NULL)); }
#line 1878 "flsc2sc.c" /* yacc.c:1646  */
    break;


#line 1882 "flsc2sc.c" /* yacc.c:1646  */
        default: break;
      }
    if (yychar_backup != yychar)
      YY_LAC_DISCARD ("yychar change");
  }
  /* User semantic actions sometimes alter yychar, and that requires
     that yytoken be updated with the new translation.  We take the
     approach of translating immediately before every use of yytoken.
     One alternative is translating here after every semantic action,
     but that translation would be missed if the semantic action invokes
     YYABORT, YYACCEPT, or YYERROR immediately after altering yychar or
     if it invokes YYBACKUP.  In the case of YYABORT or YYACCEPT, an
     incorrect destructor might then be invoked immediately.  In the
     case of YYERROR or YYBACKUP, subsequent parser actions might lead
     to an incorrect destructor call or verbose syntax error message
     before the lookahead is translated.  */
  YY_SYMBOL_PRINT ("-> $$ =", yyr1[yyn], &yyval, &yyloc);

  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);

  *++yyvsp = yyval;

  /* Now 'shift' the result of the reduction.  Determine what state
     that goes to, based on the state we popped back to and the rule
     number reduced by.  */

  yyn = yyr1[yyn];

  yystate = yypgoto[yyn - YYNTOKENS] + *yyssp;
  if (0 <= yystate && yystate <= YYLAST && yycheck[yystate] == *yyssp)
    yystate = yytable[yystate];
  else
    yystate = yydefgoto[yyn - YYNTOKENS];

  goto yynewstate;


/*--------------------------------------.
| yyerrlab -- here on detecting error.  |
`--------------------------------------*/
yyerrlab:
  /* Make sure we have latest lookahead translation.  See comments at
     user semantic actions for why this is necessary.  */
  yytoken = yychar == YYEMPTY ? YYEMPTY : YYTRANSLATE (yychar);

  /* If not already recovering from an error, report this error.  */
  if (!yyerrstatus)
    {
      ++yynerrs;
#if ! YYERROR_VERBOSE
      yyerror (YY_("syntax error"));
#else
# define YYSYNTAX_ERROR yysyntax_error (&yymsg_alloc, &yymsg, \
                                        yyesa, &yyes, &yyes_capacity, \
                                        yyssp, yytoken)
      {
        char const *yymsgp = YY_("syntax error");
        int yysyntax_error_status;
        if (yychar != YYEMPTY)
          YY_LAC_ESTABLISH;
        yysyntax_error_status = YYSYNTAX_ERROR;
        if (yysyntax_error_status == 0)
          yymsgp = yymsg;
        else if (yysyntax_error_status == 1)
          {
            if (yymsg != yymsgbuf)
              YYSTACK_FREE (yymsg);
            yymsg = (char *) YYSTACK_ALLOC (yymsg_alloc);
            if (!yymsg)
              {
                yymsg = yymsgbuf;
                yymsg_alloc = sizeof yymsgbuf;
                yysyntax_error_status = 2;
              }
            else
              {
                yysyntax_error_status = YYSYNTAX_ERROR;
                yymsgp = yymsg;
              }
          }
        yyerror (yymsgp);
        if (yysyntax_error_status == 2)
          goto yyexhaustedlab;
      }
# undef YYSYNTAX_ERROR
#endif
    }



  if (yyerrstatus == 3)
    {
      /* If just tried and failed to reuse lookahead token after an
         error, discard it.  */

      if (yychar <= YYEOF)
        {
          /* Return failure if at end of input.  */
          if (yychar == YYEOF)
            YYABORT;
        }
      else
        {
          yydestruct ("Error: discarding",
                      yytoken, &yylval);
          yychar = YYEMPTY;
        }
    }

  /* Else will try to reuse lookahead token after shifting the error
     token.  */
  goto yyerrlab1;


/*---------------------------------------------------.
| yyerrorlab -- error raised explicitly by YYERROR.  |
`---------------------------------------------------*/
yyerrorlab:

  /* Pacify compilers like GCC when the user code never invokes
     YYERROR and the label yyerrorlab therefore never appears in user
     code.  */
  if (/*CONSTCOND*/ 0)
     goto yyerrorlab;

  /* Do not reclaim the symbols of the rule whose action triggered
     this YYERROR.  */
  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);
  yystate = *yyssp;
  goto yyerrlab1;


/*-------------------------------------------------------------.
| yyerrlab1 -- common code for both syntax error and YYERROR.  |
`-------------------------------------------------------------*/
yyerrlab1:
  yyerrstatus = 3;      /* Each real token shifted decrements this.  */

  for (;;)
    {
      yyn = yypact[yystate];
      if (!yypact_value_is_default (yyn))
        {
          yyn += YYTERROR;
          if (0 <= yyn && yyn <= YYLAST && yycheck[yyn] == YYTERROR)
            {
              yyn = yytable[yyn];
              if (0 < yyn)
                break;
            }
        }

      /* Pop the current state because it cannot handle the error token.  */
      if (yyssp == yyss)
        YYABORT;


      yydestruct ("Error: popping",
                  yystos[yystate], yyvsp);
      YYPOPSTACK (1);
      yystate = *yyssp;
      YY_STACK_PRINT (yyss, yyssp);
    }

  /* If the stack popping above didn't lose the initial context for the
     current lookahead token, the shift below will for sure.  */
  YY_LAC_DISCARD ("error recovery");

  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  *++yyvsp = yylval;
  YY_IGNORE_MAYBE_UNINITIALIZED_END


  /* Shift the error token.  */
  YY_SYMBOL_PRINT ("Shifting", yystos[yyn], yyvsp, yylsp);

  yystate = yyn;
  goto yynewstate;


/*-------------------------------------.
| yyacceptlab -- YYACCEPT comes here.  |
`-------------------------------------*/
yyacceptlab:
  yyresult = 0;
  goto yyreturn;

/*-----------------------------------.
| yyabortlab -- YYABORT comes here.  |
`-----------------------------------*/
yyabortlab:
  yyresult = 1;
  goto yyreturn;

#if 1
/*-------------------------------------------------.
| yyexhaustedlab -- memory exhaustion comes here.  |
`-------------------------------------------------*/
yyexhaustedlab:
  yyerror (YY_("memory exhausted"));
  yyresult = 2;
  /* Fall through.  */
#endif

yyreturn:
  if (yychar != YYEMPTY)
    {
      /* Make sure we have latest lookahead translation.  See comments at
         user semantic actions for why this is necessary.  */
      yytoken = YYTRANSLATE (yychar);
      yydestruct ("Cleanup: discarding lookahead",
                  yytoken, &yylval);
    }
  /* Do not reclaim the symbols of the rule whose action triggered
     this YYABORT or YYACCEPT.  */
  YYPOPSTACK (yylen);
  YY_STACK_PRINT (yyss, yyssp);
  while (yyssp != yyss)
    {
      yydestruct ("Cleanup: popping",
                  yystos[*yyssp], yyvsp);
      YYPOPSTACK (1);
    }
#ifndef yyoverflow
  if (yyss != yyssa)
    YYSTACK_FREE (yyss);
#endif
  if (yyes != yyesa)
    YYSTACK_FREE (yyes);
#if YYERROR_VERBOSE
  if (yymsg != yymsgbuf)
    YYSTACK_FREE (yymsg);
#endif
  return yyresult;
}
#line 234 "FLSC2SC.y" /* yacc.c:1906  */


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

int yyerror(const char *s) {
	printf("FLSC_ErrNode(\"Line %d: %s\")\n", linenum, s);
	return 0;
}

int main(void) {
	yyparse();
	return 0;
}

