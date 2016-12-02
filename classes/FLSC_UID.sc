FLSC_UID {
	classvar current;

	*initClass {
		current = 0;
	}

	*next {
		current = current + 1;
		^("UID" ++ current.asString);
	}
}