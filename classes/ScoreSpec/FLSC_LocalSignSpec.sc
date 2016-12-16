FLSC_LocalSignSpec : FLSC_LocalScoreSpec {
	// la FLSC_WarpSpec à laquelle on se réfère
	var subSpec;

	*new {|rate, spec|
		^super.new(rate, List()).signSpecInit(spec);
	}

	signSpecInit {|spec|
		subSpec = spec;
		^this;
	}

	value {|outBus, timeWarp, varDict|
		// les bus utilisés
		// var busses = List();
		// le Bus de sortie est celui demandé, ou un nouveau Bus en son absence
		/*
		var out = if(outBus.notNil) {outBus}
		{
			var bus = FLSC_Bus(rate, nil, nil);
			busses.add(bus);
			bus;
		};
		*/
		// les SynthDef utilisées
		// var defs = Dictionary();
		// les FLSC_Bundle des sous-graphes
		// var bundles = List();

		// le résultat de l'évaluation du sous-graphe
		var subScore;

		super.value(outBus, timeWarp, varDict);

		// on rappelle sur la FLSC_WarpSpec
		// le bus demandé est le bus de sortie
		// le varDict n'est pas nécessaire,
		// puisque les variables sont uniquement celles du sous-graphe
		subScore = subSpec.value(score.outBus, timeWarp);

		// récupérer les valeurs de la subScore
		score.add(subScore);
		/*
		score.defDict.putAll(subScore.defDict);
		score.busList.addAll(subScore.busList);
		score.bundleList.addAll(subScore.bundleList);
		score.start = subScore.start;
		score.end = subScore.end;
		score.rank = subScore.rank;
		*/
		// eventuellement affecter le début et la fin du Bus créé
		/*
		if(outBus.isNil) {
			score.outBus.start = subScore.start;
			score.outBus.end = subScore.end;
		};
		*/
		// le résultat est celui de la WarpSpec, avec le FLSC_Bus créé éventuellement
		^score;
		// ^FLSC_Score(out, defs.putAll(score.defDict), busses.addAll(score.busList),
		// List(), bundles.addAll(score.bundleList), score.start, score.end, score.rank);
	}
}
