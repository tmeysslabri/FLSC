FLSC_ModSpec : FLSC_ScoreSpec {
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
		// les FLSC_MsgPair du contexte courant
		var msgs = List();
		// les FLSC_Bundle des sous-graphes
		var bundles = List();
		// les arguments du message créé
		// on itère sur les arguments
		var synthArgs = args.collect {|item|
			case
			{item.isNumber}        {item}
			{item.isFLSCTime}      {item.value(timeWarp)}
			{item.isFLSCScoreSpec} {
				// on évalue le sous-graphe
				// si c'est une liste, on en fait une FLSC_ListSpec
				var score = item.value(nil, timeWarp, varDict);
				// on ajoute les bus, les définitions, les messages, les bundle
				busses.addAll(score.busList);
				defs.putAll(score.defDict);
				msgs.addAll(score.bundle);
				bundles.addAll(score.bundleList);
				// on retourne le bus de sortie
				score.outBus;
			}
			// ne pas oublier d'ajouter le Bus de sortie aux arguments
		}.put('out', out);

		// on ajoute la SynthDef locale aux défintitions
		defs.put(def.name, def);

		// on créée le message courant à la fin, pour respecter le chaînage causal
		msgs.add(FLSC_MsgPair(def.name, synthArgs));

		^FLSC_Score(out, defs, busses, msgs, bundles,
			timeWarp.value(0), timeWarp.value('end'));
	}
}
