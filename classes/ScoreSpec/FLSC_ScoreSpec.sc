FLSC_ScoreSpec {
	// la base d'échantillonnage, compatible avec les classes SC
	var <rate;
	// la liste des ScoreSpec référencées, dans leur ordre d'apparition
	var <varList;

	*new {|timeBase, vars|
		^super.new.scoreSpecInit(timeBase, vars);
	}

	scoreSpecInit {|timeBase, vars|
		rate = timeBase;
		varList = vars;
		^this;
	}

	asFLSCScore {
		// le resultat: une FLSC_Score
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
			var value = item.value(0, timeWarp, varDict);
			defs.putAll(value.defDict);
			busses.addAll(value.busList);
			msgs.addAll(value.bundle);
			bundles.addAll(value.bundleList);
			varDict.put(item, value);
		};
		scoreValue = this.value(0, timeWarp, varDict);
		bundles.addAll(scoreValue.bundleList);
		if(scoreValue.bundle.notEmpty) {
			Error("DEBUG: non-empty bundle in top-level FLSC_Score: %".format(
				scoreValue.bundle)).throw;
		};
		^FLSC_Score(scoreValue.outBus, defs.putAll(scoreValue.defDict),
			busses.addAll(scoreValue.busList), List(), bundles,
			scoreValue.start, scoreValue.end);
	}

	isFLSCScoreSpec { ^true; }
	encapsulate { ^FLSC_VarSpec(this) }
	asFLSCScoreSpec { ^this; }
}
