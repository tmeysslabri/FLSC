FLSC_SignSpec : FLSC_ScoreSpec {
	// la FLSC_WarpSpec à laquelle on se réfère
	var subSpec;

	*new {|rate, varList, spec|
		^super.new(rate, varList).signSpecInit(spec);
	}

	signSpecInit {|spec|
		subSpec = spec;
		^this;
	}
}
