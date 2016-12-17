FLSC_ScoreSpec {
	// la définition de la sortie système
	// classvar systemOut;
	// la base d'échantillonnage, compatible avec les classes SC
	var <rate;
	// la liste des ScoreSpec référencées, dans leur ordre d'apparition
	var <varList;
	// l'objet Score construit
	var score;

	/*
	*initClass {
		systemOut = SynthDef('systemOut', {|in|
			// duplication de la sortie du programme
			Out.ar(0, In.ar(in)!2);
		});
	}
	*/

	*new {|timeBase, vars|
		^super.new.scoreSpecInit(timeBase, vars);
	}

	scoreSpecInit {|timeBase, vars|
		rate = timeBase;
		varList = vars;
		^this;
	}

	// méthode appelée à la racine du graphe de FLSC_ScoreSpec
	// encapsuler dans une FLSC_OutSpec et évaluer
	asFLSCScore {
		^FLSC_OutSpec(rate, varList, this).value;
	}

	value {|outBus, timeWarp, varDict|
		score = FLSC_Score();
		score.outBus = this.makeOut(outBus, timeWarp);
		^score;
	}

	// méthodes génériques
	isFLSCScoreSpec { ^true; }
	encapsulate { ^FLSC_VarSpec(this) }
	asFLSCScoreSpec { ^this; }
}
