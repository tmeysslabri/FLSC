FLSC_SignSpec : FLSC_ScoreSpec {
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
		var busses = List();
		// le Bus de sortie est celui demandé, ou un nouveau Bus en son absence
		var out = if(outBus.notNil) {outBus}
		{
			var bus = FLSC_Bus(rate, timeWarp.value(0), timeWarp.value('end'));
			busses.add(bus);
			bus;
		};
		// les SynthDef utilisées
		var defs = Dictionary();
		// les FLSC_Bundle des sous-graphes
		var bundles = List();

		// on rappelle sur la FLSC_WarpSpec
		// le bus demandé est le bus de sortie
		// le varDict n'est pas nécessaire,
		// puisque les variables sont uniquement celles du sous-graphe
		var score = subSpec.value(out, timeWarp);

		// le résultat est celui de la WarpSpec, avec le FLSC_Bus créé éventuellement
		^FLSC_Score(out, defs.putAll(score.defDict), busses.addAll(score.busList),
			List(), bundles.addAll(score.bundleList), score.start, score.end, score.rank);
	}
}
