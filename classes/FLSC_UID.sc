FLSC_UID {
	classvar current;

	*initClass {
		current = 0;
	}

	*next {
		current = current + 1;
		^("uID" ++ current.asString);
	}

	*reset {
		current = 0;
	}
}