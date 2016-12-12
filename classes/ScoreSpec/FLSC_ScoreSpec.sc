FLSC_ScoreSpec {
	// la définition de la sortie système
	classvar systemOut;
	// la base d'échantillonnage, compatible avec les classes SC
	var <rate;
	// la liste des ScoreSpec référencées, dans leur ordre d'apparition
	var <varList;

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
		// le resultat de l'évaluation du noeud: une FLSC_Score
		var scoreValue;
		// distorsion temporelle par défaut
		var timeWarp = {|t| t};
		// définitions, bus, messages, bundle accumulés
		var defs = Dictionary();
		var busses = List();
		var msgs = List();
		var bundles = List();
		// et varDict pour l'interprétation des FLSC_Score
		var varDict = Dictionary();

		// évaluer la varList
		// (il est possible que des variables ne soient pas encore évaluées)
		varList.do {|item|
			var value = item.value(nil, timeWarp, varDict);
			defs.putAll(value.defDict);
			busses.addAll(value.busList);
			msgs.addAll(value.bundle);
			bundles.addAll(value.bundleList);
			varDict.put(item, value);
		};
		// evaluer la ScoreSpec dans le contexte global
		scoreValue = this.value(nil, timeWarp, varDict);
		// récupérer les bundle, les defs, les bus
		bundles.addAll(scoreValue.bundleList);
		defs.putAll(scoreValue.defDict);
		busses.addAll(scoreValue.busList);
		// vérifier qu'il ne reste plus de messages en attente
		// sinon, créér le dernier bundle
		if(scoreValue.bundle.notEmpty) {
			/*
			Error("DEBUG: non-empty bundle in top-level FLSC_Score: %".format(
				scoreValue.bundle)).throw;
			*/
			"DEBUG: non-empty bundle in top-level FLSC_Score: %".format(
				scoreValue.bundle).postln;
			bundles.add(FLSC_Bundle(scoreValue.start, scoreValue.end, scoreValue.bundle));
		};
		// ajouter systemOut à la fin
		bundles.add(FLSC_Bundle(scoreValue.start, scoreValue.end,
			List.newFrom([FLSC_MsgPair(systemOut.name,
				Dictionary.newFrom(['in', scoreValue.outBus]), scoreValue.rank)])));
		// ajouter la définition de la sortie système
		defs.put(systemOut.name, systemOut);

		// retourner le résultat
		^FLSC_Score(scoreValue.outBus, defs, busses, List(), bundles,
			scoreValue.start, scoreValue.end, scoreValue.rank + 1);
	}

	isFLSCScoreSpec { ^true; }
	encapsulate { ^FLSC_VarSpec(this) }
	asFLSCScoreSpec { ^this; }
}
