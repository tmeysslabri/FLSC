FLSC_ModSpec : FLSC_ScoreSpec {
	// la SynthDef associée à cette instance de module
	// DEBUG: rendu accessible pour les tests
	var <def;
	// les arguments à passer à la SynthDef
	var args;

	*new {|rate, varList, synthDef, synthArgs|
		^super.new(rate, varList).modSpecInit(synthDef, synthArgs);
	}

	modSpecInit {|synthDef, synthArgs|
		def = synthDef;
		args = synthArgs;
		^this;
	}
}
