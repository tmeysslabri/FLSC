FLSC_SignSpec : FLSC_ScoreSpec {
	// la FLSC_WarpSpec à laquelle on se réfère
	var subSpec;

	*new {|rate, spec|
		^super.new(rate, List()).signSpecInit(spec);
	}

	signSpecInit {|spec|
		subSpec = spec;
		^this;
	}
}
