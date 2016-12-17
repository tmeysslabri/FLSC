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

	value {|outBus, timeWarp, varDict, noWarpDict|
		// le résultat de l'évaluation du sous-graphe
		var subScore;

		super.value(outBus, timeWarp, varDict, noWarpDict);

		noWarpList.do {|item|
			var subScore = item.value(nil, timeWarp, varDict, noWarpDict);
			// il faut également récupérer les définitions, les bus, les messages, les bundles
			score.add(subScore);
			noWarpDict.put(item, subScore);
		};

		// on rappelle sur la subSpec
		subScore = subSpec.value(score.outBus, timeWarp, varDict, noWarpDict);

		// récupérer les valeurs de la subScore
		score.add(subScore);

		^score;
	}
}
