FLSC_CompSpec : FLSC_ScoreSpec {
	// la liste des FLSC_WarpSpec auxquelles on se réfère
	var subSpecs;

	*new {|rate, specs|
		^super.new(rate, List()).compSpecInit(specs);
	}

	compSpecInit {|specs|
		subSpecs = specs;
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

		// on rappelle itérativement sur les FLSC_WarpSpec
		// le bus demandé est le outBus
		// le varDict n'est pas nécessaire,
		// puisque les variables sont uniquement celles du sous-graphe
		subSpecs.do {|item|
			var score = item.value(out, timeWarp);
			// on ajoute les bus, les définitions, les bundle
			// il n'y a pas de messages dans le contexte courant
			busses.addAll(score.busList);
			defs.putAll(score.defDict);
			bundles.addAll(score.bundleList);
		}

		^FLSC_Score(out, defs, busses, List(), bundles,
			timeWarp.value(0), timeWarp.value('end'));
	}
}
