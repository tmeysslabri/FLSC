FLSC_SplitSpec : FLSC_LocalScoreSpec {
	// la FLSC_ScoreSpec à laquelle on se réfère
	var subSpec;
	// la liste des variables sans distorsion
	var noWarpList;

	*new {|rate, varList, spec, noWarpList|
		^super.new(rate, varList).splitSpecInit(spec, noWarpList);
	}

	splitSpecInit {|spec, list|
		subSpec = spec;
		noWarpList = list;
		^this;
	}

	scoreValue {|outBus, timeWarp, varDict, noWarpDict|
		// le résultat de l'évaluation du sous-graphe
		var subScore;
		// on copie le noWarpDict pour éliminer les interférences
		var newNoWarpDict = noWarpDict.copy;

		this.initScore(outBus, timeWarp, varDict, newNoWarpDict);

		noWarpList.do {|item|
			var subScore = item.value(nil, timeWarp, varDict, newNoWarpDict);
			// il faut également récupérer les définitions, les bus, les messages, les bundles
			score.add(subScore);
			newNoWarpDict.put(item, subScore);
		};

		// on rappelle sur la subSpec
		subScore = subSpec.value(score.outBus, timeWarp, varDict, newNoWarpDict);

		// récupérer les valeurs de la subScore
		score.add(subScore);

		^score;
	}
}
