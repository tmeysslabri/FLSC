FLSC_NonUnique {
	// le Symbol de l'identifiant
	var ident;

	*new {|ident|
		^super.new.nonUniqueInit(ident);
	}

	nonUniqueInit {|id|
		ident = id;
		^this;
	}

	isFLSCUnique { ^false }
	value { ^ident }
}