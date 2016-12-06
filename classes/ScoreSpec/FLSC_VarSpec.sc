FLSC_VarSpec {
	// la ScoreSpec à laquelle on se réfère
	var subSpec;

	*new {|spec|
		^super.new.listSpecInit(spec);
	}

	listSpecInit {|spec|
		subSpec = spec;
		^this;
	}

	rate {
		^subSpec.rate;
	}

	varList {
		^subSpec.varList;
	}
}