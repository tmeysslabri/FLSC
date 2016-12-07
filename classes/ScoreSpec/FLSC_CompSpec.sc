FLSC_CompSpec : FLSC_ScoreSpec {
	// la liste des FLSC_WarpSpec auxquelles on se réfère
	var subSpecs;

	*new {|rate, specs|
		^super.new(rate, List()).compSpecInit(specs);
	}

	compSpecInit {|specs|
		subSpecs = specs;
		^this;
	}
}
