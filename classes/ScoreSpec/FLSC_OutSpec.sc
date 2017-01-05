FLSC_OutSpec : FLSC_ScoreSpec {
	// la définition de la sortie système
	classvar systemOut;
	// la ScoreSpec globale
	var subSpec;

	*new {|timeBase, vars, spec|
		^super.new(timeBase, vars).outSpecInit(spec);
	}

	outSpecInit {|spec|
		subSpec = spec;
		^this;
	}

	*initClass {
		systemOut = SynthDef('systemOut', {|in|
			// duplication de la sortie du programme
			Out.ar(0, In.ar(in)!2);
		});
	}

	// méthode appelée à la racine du graphe de FLSC_ScoreSpec
	value {|before = 0, after = 0|
		// la FLSC_Score produite par la subSpec
		var subScore;
		// distorsion temporelle par défaut
		var timeWarp = {|t|
			if(t.isNumber.not)
			{Error("Global time value is not a number: %".format(t)).throw}
			// ajouter la marge de début
			{t + before}
		};
		// varDict pour l'interprétation des FLSC_Score
		var varDict = Dictionary();
		var noWarpDict = Dictionary();

		score = FLSC_Score();

		// évaluer la varList
		// (il est possible que des variables ne soient pas encore évaluées,
		// si elles sont en dehors des GlobalSignSpec)
		varList.do {|item|
			var subScore = item.value(nil, timeWarp, varDict, noWarpDict).checkTimes;
			score.add(subScore);
			varDict.put(item, subScore);
		};
		// evaluer la ScoreSpec référencée dans le contexte global
		subScore = subSpec.value(nil, timeWarp, varDict, noWarpDict).checkTimes;
		// récupérer le outBus et le contenu
		score.outBus = subScore.outBus;
		// si la partition est vide, produire une partition vide
		if(score.outBus.isNil) {^Score()};
		score.add(subScore);

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
		// ajouter la marge de fin
		score.end = score.end + after;
		// ajouter le bundle
		score.pushBundle;

		// incrémenter score.rank, pour créér le dernier groupe
		score.rank = score.rank + 1;
		// ajouter la définition de la sortie système
		score.defDict.put(systemOut.name, systemOut);

		// retourner le résultat
		^score;
	}
}