FLSC_ScoreSpec {
	// la définition de la sortie système
	classvar systemOut;
	// la base d'échantillonnage, compatible avec les classes SC
	var <rate;
	// la liste des ScoreSpec référencées, dans leur ordre d'apparition
	var <varList;
	// l'objet Score construit
	var score;

	*initClass {
		systemOut = SynthDef('systemOut', {|in|
			// duplication de la sortie du programme
			Out.ar(0, In.ar(in)!2);
		});
	}

	*new {|timeBase, vars|
		^super.new.scoreSpecInit(timeBase, vars);
	}

	scoreSpecInit {|timeBase, vars|
		rate = timeBase;
		varList = vars;
		^this;
	}

	// méthode appelée à la racine du graphe de FLSC_ScoreSpec
	asFLSCScore {
		// distorsion temporelle par défaut
		var timeWarp = {|t|
			if(t.isNumber.not)
			{Error("Global time value is not a number: %".format(t)).throw}
			{t}
		};
		// varDict pour l'interprétation des FLSC_Score
		var varDict = Dictionary();

		// évaluer la varList
		// (il est possible que des variables ne soient pas encore évaluées)
		varList.do {|item|
			var subScore = item.value(nil, timeWarp, varDict);
			score.add(subScore);
			varDict.put(item, subScore);
		};
		// evaluer la ScoreSpec dans le contexte global
		score.add(this.value(nil, timeWarp, varDict));

		// vérifier qu'il ne reste plus de messages en attente
		// sinon, déclencher une erreur
		// (cela signifie qu'un module est présent en dehors d'un patch)
		if(score.bundle.notEmpty) {
			Error("DEBUG: non-empty bundle in top-level FLSC_ScoreSpec: %".format(
				score.bundle)).throw;
		};
		// ajouter systemOut à la fin
		score.bundle.add(FLSC_MsgPair(systemOut.name,
				Dictionary.newFrom(['in', score.outBus]), score.rank));
		score.pushBundle;
		// incrémenter score.rank, pour créér le dernier groupe
		score.rank = score.rank + 1;
		// ajouter la définition de la sortie système
		score.defDict.put(systemOut.name, systemOut);

		// retourner le résultat
		^score;
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
