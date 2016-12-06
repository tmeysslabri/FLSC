FLSC_CompSpec : FLSC_ScoreSpec {
	// la liste des FLSC_WarpSpec auxquelles on se réfère
	var subSpecs;

	*new {|rate, varList, specs|
		^super.new(rate, varList).compSpecInit(specs);
	}

	compSpecInit {|specs|
		subSpecs = specs;
		^this;
	}
}
