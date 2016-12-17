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
		/*
		// définitions, bus, et messages accumulés
		var defs = Dictionary();
		var busses = List();
		var msgs = List();
		// bundleList résultant de l'ajout du bundle courant à la bundleList accumulée
		var bundles = List();
		*/
		// création de la distorsion locale
		var newTimeWarp = superTimeWarp <> timeWarp;
		var varDict = Dictionary();
		// création du varDict par itération sur la varList
		// il faut également récupérer les définitions, les bus, les messages, les bundles
		subSpec.varList.do {|item|
			var subScore = item.value(nil, newTimeWarp, varDict);
			score.add(subScore);
			/*
			defs.putAll(value.defDict);
			busses.addAll(value.busList);
			msgs.addAll(value.bundle);
			bundles.addAll(value.bundleList);
			*/
			varDict.put(item, subScore);
		};

		// le outBus est simplement celui passé en argument
		score.outBus = outBus;
		// on rappelle sur la spécification de référence
		score.add(subSpec.value(outBus, newTimeWarp, varDict));
		// ajouter les messages courants à la bundleList, si il y en a
		score.pushBundle;
		/*
		if(msgs.notEmpty) {
			bundles.add(FLSC_Bundle(
			score.start, score.end, msgs))
		};
		*/

		^score;
		// ^FLSC_Score(score.outBus, defs.putAll(score.defDict), busses.addAll(score.busList),
		// List(), bundles, score.start, score.end, score.rank);
	}
}
