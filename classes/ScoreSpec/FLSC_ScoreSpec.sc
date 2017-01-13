FLSC_ScoreSpec {
	// la définition de la sortie système
	// classvar systemOut;
	// la base d'échantillonnage, compatible avec les classes SC
	var <rate;
	// la liste des ScoreSpec référencées, dans leur ordre d'apparition
	var <varList;
	// l'objet Score construit
	var score;
	// le SemanticNode parent
	var >parent;

	*new {|timeBase, vars|
		^super.new.scoreSpecInit(timeBase, vars);
	}

	scoreSpecInit {|timeBase, vars|
		rate = timeBase;
		varList = vars;
		^this;
	}

	value {|outBus, timeWarp, varDict, noWarpDict|
		var res;
		try {
			res = this.scoreValue(outBus, timeWarp, varDict, noWarpDict);
		} {|error|
			if(error.isKindOf(FLSC_Error))
			{FLSC_LocError(error.errorString, parent.start, parent.end).throw}
			{error.throw}
		}
		^res;
	}


	// méthode appelée à la racine du graphe de FLSC_ScoreSpec
	// encapsuler dans une FLSC_OutSpec et évaluer
	asFLSCScore {|before = 0, after = 0|
		^FLSC_OutSpec(rate, varList, this).value(before, after);
	}

	initScore {|outBus, timeWarp, varDict, noWarpDict|
		score = FLSC_Score();
		score.outBus = this.makeOut(outBus, timeWarp);
		^score;
	}

	doesNotUnderstand {|selector ... args|
		if(Number.findRespondingMethodFor(selector).notNil)
		{FLSC_Error("Signal does not respond to numeric operator: %"
			.format(selector)).throw}
		{DoesNotUnderstandError(this, selector, args).throw};
	}

	// méthodes génériques
	isFLSCScoreSpec { ^true; }
	encapsulate { ^FLSC_VarSpec(this) }
}
