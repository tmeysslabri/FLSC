FLSC_CompSpec : FLSC_LocalScoreSpec {
	// la liste des FLSC_WarpSpec auxquelles on se réfère
	var subSpecs;

	*new {|rate, specs|
		^super.new(rate, List()).compSpecInit(specs);
	}

	compSpecInit {|specs|
		subSpecs = specs;
		^this;
	}

	value {|outBus, timeWarp, varDict, noWarpDict|
		super.value(outBus, timeWarp, varDict);

		// on rappelle itérativement sur les FLSC_WarpSpec
		// le bus demandé est le outBus
		// le varDict n'est pas nécessaire,
		// puisque les variables sont uniquement celles du sous-graphe
		subSpecs.do {|item|
			var subScore = item.value(score.outBus, timeWarp, noWarpDict);
			// on ajoute les bus, les définitions, les bundle
			// il n'y a pas de messages dans le contexte courant
			score.add(subScore);
		};

		// ajouter les temps de début et de fin
		// (au cas où les subSpecs seraient sur un support temporel plus réduit)
		// ??? est-ce nécessaire ?
		score.start = timeWarp.value([0, nil]);
		score.end = timeWarp.value(['end', nil]);

		^score;
	}
}
