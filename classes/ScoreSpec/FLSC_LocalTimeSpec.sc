FLSC_LocalTimeSpec : FLSC_LocalScoreSpec {
	// la FLSC_WarpSpec à laquelle on se réfère
	var subSpec;

	*new {|rate, spec|
		^super.new(rate, List()).localTimeSpecInit(spec);
	}

	localTimeSpecInit {|spec|
		subSpec = spec;
		^this;
	}

	scoreValue {|outBus, timeWarp, varDict, noWarpDict|
		// le résultat de l'évaluation du sous-graphe
		var subScore;

		this.initScore(outBus, timeWarp, varDict, noWarpDict);

		// on rappelle sur la FLSC_WarpSpec
		// le bus demandé est le bus de sortie
		// le varDict n'est pas nécessaire,
		// puisque les variables sont uniquement celles du sous-graphe
		subScore = subSpec.value(score.outBus, timeWarp, noWarpDict);
		// récupérer les valeurs de la subScore
		score.add(subScore);

		// si outBus.isNil && (score.outBus.end == inf),
		// alors nous sommes en train de définir un nouveau Bus
		// sur un support dans le contexte global
		// => corriger l'erreur en ajustant les bornes
		if(outBus.isNil && (score.outBus.end == inf))
		{
			score.outBus.start = score.start;
			score.outBus.end = score.end;
		}

		^score;
	}
}
