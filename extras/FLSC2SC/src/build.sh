#! /bin/sh

bison --defines=flsc2sc.h -o flsc2sc.c  FLSC2SC.y
flex -o flsc2sc-lex.c FLSC2SC.l

mkdir -p ../build
cd ../build
gcc -Wall -c ../src/flsc2sc.c
gcc -Wall -c ../src/flsc2sc-lex.c
gcc -Wall flsc2sc.o flsc2sc-lex.o -o flsc2sc

