FLSC_VarSpec : FLSC_ScoreSpec {
	// la ScoreSpec à laquelle on se réfère
	var subSpec;

	*new {|spec|
		^super.new(spec.rate, spec.varList).listSpecInit(spec);
	}

	listSpecInit {|spec|
		subSpec = spec;
		varList.add(subSpec);
		^this;
	}

	encapsulate { ^this; }
}