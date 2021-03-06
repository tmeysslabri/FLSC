FLSC_ScoreSpec : FLSC_Object {
	// description du type
	classvar <typeDesc;
	// la base d'échantillonnage, compatible avec les classes SC
	var <rate;
	// la liste des ScoreSpec référencées, dans leur ordre d'apparition
	var <varList;
	// l'objet Score construit
	var score;
	// le SemanticNode parent
	var >parent;

	*initClass {
		typeDesc = "Signal";
	}

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
			case
			{error.isKindOf(FLSC_FatalError)}
			{error.throw}
			{error.isKindOf(FLSC_LocError)}
			{error.throw}
			{error.isKindOf(FLSC_Error)}
			{FLSC_LocError(error.errorString, parent.start, parent.end).throw}
			{true}
			{error.reportError; FLSC_FatalError("Non-FLSC error: see above").throw}
		};
		^res;
	}


	// méthode appelée à la racine du graphe de FLSC_ScoreSpec
	// encapsuler dans une FLSC_OutSpec et évaluer
	asFLSCScore {|before = 0, after = 0|
		^FLSC_OutSpec(rate, varList, this).parent_(parent).value(before, after);
	}

	initScore {|outBus, timeWarp, varDict, noWarpDict|
		score = FLSC_Score();
		score.outBus = this.makeOut(outBus, timeWarp);
		^score;
	}

	/*
	doesNotUnderstand {|selector ... args|
		if(Number.findRespondingMethodFor(selector).notNil)
		{FLSC_Error("Signal does not respond to numeric operator: %"
			.format(selector)).throw}
		{DoesNotUnderstandError(this, selector, args).throw};
	}
	*/

	// méthodes génériques
	isFLSCScoreSpec { ^true; }
	encapsulate { ^FLSC_VarSpec(this) }
}
