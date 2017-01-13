FLSC_LocError : Error {
	// classe spécifique des erreurs FLSC
	// avec localisation
	var <start, <end;

	*new {|what, start, end|
		^super.new(what).locErrorInit(start, end);
	}

	locErrorInit {|first, last|
		start = first;
		end = last;
	}
}