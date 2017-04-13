#!/bin/bash

for FILE in $(find $1 -name '*.WAV')
do 
	flac -V --replay-gain --delete-input-file $FILE
done
