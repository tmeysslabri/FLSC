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

	value {|outBus, timeWarp, varDict|
		// on va chercher la référence dans le varDict
		var sub = varDict[subSpec];
		// toutes les informations ont déjà été ajoutées,
		// seul le bus de sortie nous intéresse
		^FLSC_Score(sub.outBus, Dictionary(), List(), List(), List());
	}

	encapsulate { ^this; }
}