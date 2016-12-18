
flsc2sc: flsc2sc.o flsc2sc-lex.o build
	gcc -Wall flsc2sc.o flsc2sc-lex.o -o ../build/flsc2sc
	
flsc2sc.o: flsc2sc.c flsc2sc.h flsc_types.h
	gcc -Wall -c flsc2sc.c
	
flsc2sc-lex.o: flsc2sc-lex.c flsc2sc.h flsc_types.h
	gcc -Wall -c flsc2sc-lex.c

flsc2sc.c flsc2sc.h: FLSC2SC.y
	bison --defines=flsc2sc.h -o flsc2sc.c FLSC2SC.y

flsc2sc-lex.c: FLSC2SC.l
	flex -o flsc2sc-lex.c FLSC2SC.l

build:
	mkdir -p ../build
	
clean:
	rm -f flsc2sc.o flsc2sc-lex.o
