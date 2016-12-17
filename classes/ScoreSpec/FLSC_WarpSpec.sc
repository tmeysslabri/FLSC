FLSC_WarpSpec {
	// la distorsion temporelle à laquelle on fait référence
	var timeWarp;
	// la spécification à laquelle on fait référence
	var subSpec;

	*new {|warp, spec|
		^super.new.warpSpecInit(warp, spec);
	}

	warpSpecInit {|warp, spec|
		timeWarp = warp;
		subSpec = spec;
		^this;
	}

	value {|outBus, superTimeWarp|
		// résultat de l'évaluation du sous-graphe
		var score = FLSC_Score();
		// création de la distorsion locale
		var newTimeWarp = superTimeWarp <> timeWarp;
		var varDict = Dictionary();
		// création du varDict par itération sur la varList
		subSpec.varList.do {|item|
			var subScore = item.value(nil, newTimeWarp, varDict);
			// il faut également récupérer les définitions, les bus, les messages, les bundles
			score.add(subScore);
			varDict.put(item, subScore);
		};

		// le outBus est simplement celui passé en argument
		score.outBus = outBus;
		// on rappelle sur la spécification de référence
		score.add(subSpec.value(outBus, newTimeWarp, varDict));
		// ajouter les messages courants à la bundleList, si il y en a
		score.pushBundle;

		^score;
	}
}
