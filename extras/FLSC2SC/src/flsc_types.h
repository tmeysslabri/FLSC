typedef struct words {
	char *word;
	struct words *next;
} words;

typedef struct {
	words *start;
	words **end;
} wptr;

