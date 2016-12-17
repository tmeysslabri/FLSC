FLSC_ModSpec : FLSC_LocalScoreSpec {
	// la SynthDef associée à cette instance de module
	var def;
	// les arguments à passer à la SynthDef
	var args;

	*new {|rate, varList, synthDef, synthArgs|
		^super.new(rate, varList).modSpecInit(synthDef, synthArgs);
	}

	modSpecInit {|synthDef, synthArgs|
		def = synthDef;
		args = synthArgs;
		^this;
	}

	value {|outBus, timeWarp, varDict, noWarpDict|
		// les arguments à passer au synthétiseur
		var synthArgs;

		super.value(outBus, timeWarp, varDict, noWarpDict);

		synthArgs = args.collect {|item|
			case
			{item.isNumber}        {item}
			{item.isFLSCTime}      {item.value(timeWarp)}
			{item.isFLSCScoreSpec} {
				// on évalue le sous-graphe
				var subScore = item.value(nil, timeWarp, varDict, noWarpDict);
				score.add(subScore);
				// on retourne le bus de sortie
				subScore.outBus;
			}
			// ne pas oublier d'ajouter le Bus de sortie aux arguments
		}.put('out', score.outBus);

		// on ajoute la SynthDef locale aux défintitions
		score.defDict.put(def.name, def);

		// on créée le message courant
		score.bundle.add(FLSC_MsgPair(def.name, synthArgs, score.rank));

		// ajouter les temps de début et de fin, et le rang
		// (obligatoire car il n'y a pas nécessairement de subScore)
		score.start = timeWarp.value(0);
		score.end = timeWarp.value('end');
		score.rank = score.rank + 1;

		^score;
	}
}
