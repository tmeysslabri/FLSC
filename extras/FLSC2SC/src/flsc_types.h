typedef struct words {
	char *word;
	int lnbrk;
	struct words *next;
} words;

typedef struct {
	words *start;
	words **end;
} wptr;

