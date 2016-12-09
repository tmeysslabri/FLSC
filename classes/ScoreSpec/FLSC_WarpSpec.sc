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
		var score;
		// définitions, bus, et messages accumulés
		var defs = Dictionary();
		var busses = List();
		var msgs = List();
		// bundleList résultant de l'ajout du bundle courant à la bundleList accumulée
		var bundles = List();
		// création de la distorsion locale
		var newTimeWarp = superTimeWarp <> timeWarp;
		var varDict = Dictionary();
		// création du varDict par itération sur la varList
		// les variables doivent créer des outBus, sauf si la sortie est la sortie système
		var varOut = if(outBus == 0) {0} {nil};
		// il faut également récupérer les définitions, les bus, les messages, les bundles
		subSpec.varList.do {|item|
			var value = item.value(varOut, newTimeWarp, varDict);
			defs.putAll(value.defDict);
			busses.addAll(value.busList);
			msgs.addAll(value.bundle);
			bundles.addAll(value.bundleList);
			varDict.put(item, value);
		};
		// le outBus est simplement celui passé en argument
		// on rappelle sur la spécification de référence
		score = subSpec.value(outBus, newTimeWarp, varDict);
		bundles.addAll(score.bundleList);
		if(score.bundle.notEmpty) {
			"% % %".format(score.bundle.collect(_.debug),
				newTimeWarp.value(0), newTimeWarp.value('end')).postln;
			bundles.add(FLSC_Bundle(
			newTimeWarp.value(0), newTimeWarp.value('end'), msgs.addAll(score.bundle)))
		};
		^FLSC_Score(score.outBus, defs.putAll(score.defDict), busses.addAll(score.busList),
			List(), bundles);
	}
}
