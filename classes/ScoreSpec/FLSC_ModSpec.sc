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

	value {|outBus, timeWarp, varDict|
		// les bus utilisés
		// var busses = List();
		// le Bus de sortie est celui demandé, ou un nouveau Bus en son absence
		/*
		var out = if(outBus.notNil) {outBus}
		{
			var bus = FLSC_Bus(rate, timeWarp.value(0), timeWarp.value('end'));
			busses.add(bus);
			bus;
		};
		*/
		// les SynthDef utilisées
		// var defs = Dictionary();
		// les FLSC_MsgPair du contexte courant
		// var msgs = List();
		// les FLSC_Bundle des sous-graphes
		// var bundles = List();
		// le rang de ce sous-graphe
		// var rank = 0;
		// les arguments du message créé
		// on itère sur les arguments
		var synthArgs;

		super.value(outBus, timeWarp, varDict);

		synthArgs = args.collect {|item|
			case
			{item.isNumber}        {item}
			{item.isFLSCTime}      {item.value(timeWarp)}
			{item.isFLSCScoreSpec} {
				// on évalue le sous-graphe
				var subScore = item.value(nil, timeWarp, varDict);
				score.add(subScore);
				/*
				// on ajoute les bus, les définitions, les messages, les bundle
				score.busList.addAll(subScore.busList);
				score.defDict.putAll(subScore.defDict);
				score.bundle.addAll(subScore.bundle);
				score.bundleList.addAll(subScore.bundleList);
				rank = max(rank, subScore.rank);
				*/
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
		score.start = timeWarp.value(0);
		score.end = timeWarp.value('end');
		score.rank = score.rank + 1;

		^score;
	}
}
