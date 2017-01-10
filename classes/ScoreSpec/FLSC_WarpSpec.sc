FLSC_WarpSpec {
	// la distorsion temporelle à laquelle on fait référence
	var timeWarp;
	// la fonction de vérification
	var checkFunc;
	// la spécification à laquelle on fait référence
	var subSpec;

	*new {|warp, spec, checkFunc|
		^super.new.warpSpecInit(warp, spec, checkFunc);
	}

	warpSpecInit {|warp, spec, func|
		timeWarp = warp;
		subSpec = spec;
		checkFunc = func;
		^this;
	}

	value {|outBus, superTimeWarp, noWarpDict|
		// résultat de l'évaluation du sous-graphe
		var score = FLSC_Score();
		// création de la distorsion locale
		var newTimeWarp = superTimeWarp <> timeWarp;
		var varDict = Dictionary();
		// vérifier l'intégrité temporelle
		if(checkFunc.notNil) {checkFunc.(newTimeWarp, superTimeWarp)};
		// création du varDict par itération sur la varList
		subSpec.varList.do {|item|
			var subScore = item.value(nil, newTimeWarp, varDict, noWarpDict).checkTimes;
			// il faut également récupérer les définitions, les bus, les messages, les bundles
			score.add(subScore);
			varDict.put(item, subScore);
		};

		// le outBus est simplement celui passé en argument
		score.outBus = outBus;
		// on rappelle sur la spécification de référence
		score.add(subSpec.value(outBus, newTimeWarp, varDict, noWarpDict));
		// ajouter les messages courants à la bundleList, si il y en a
		score.pushBundle;

		^score.checkTimes;
	}
}
