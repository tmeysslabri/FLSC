FLSC_ListSpec : FLSC_ScoreSpec {
	// la liste de ListSpec/ScoreSpec/VarSpec à laquelle on se réfère
	var subSpecs;

	*new {|specs|
		var varList, subSpecs;
		// gérer le cas des listes récursives
		// -- ceci soulève une erreur si un membre n'est ni une liste, ni une ScoreSpec
		subSpecs = specs.collect(_.asFLSCScoreSpec);
		// on vérifie que les éléments sont tous de rate 'audio'
		subSpecs.do {|item| if(item.rate != 'audio')
			{Error("Non-audio member in ListSpec: %".format(item)).throw}};
		varList = subSpecs.inject(List()) {|acc, it| acc.union(it.varList)};
		^super.new('audio', varList).listSpecInit(subSpecs);
	}

	listSpecInit {|specs|
		subSpecs = specs;
		^this;
	}

	value {|outBus, timeWarp, varDict|
		// les bus utilisés
		var busses = List();
		// les SynthDef utilisées
		var defs = Dictionary();
		// les FLSC_MsgPair du contexte courant
		var msgs = List();
		// les FLSC_Bundle des sous-graphes
		var bundles = List();
		// les dates de début et de fin
		var start = inf, end = 0;
		// le rang global
		var rank = 0;
		// le Bus de sortie est celui demandé, ou un nouveau Bus en son absence
		// dans le deuxième cas on doit le créér, puis calculer le début et la fin
		// suivant le résultat des sous-spécifications
		var out = if(outBus.notNil) {outBus}
		{
			var bus = FLSC_Bus(rate, nil, nil);
			busses.add(bus);
			bus;
		};
		// on itère sur les éléments
		subSpecs.do {|item|
			// on évalue le sous-graphe
			var score = item.value(out, timeWarp, varDict);
			// on ajoute les bus, les définitions, les messages, les bundle
			busses.addAll(score.busList);
			defs.putAll(score.defDict);
			msgs.addAll(score.bundle);
			bundles.addAll(score.bundleList);
			start = min(start, score.start);
			end = max(end, score.end);
			rank = max(rank, score.rank);
		};

		// si aucun Bus de sortie n'est demandé, ajouter le début et la fin au Bus créé
		if(outBus.isNil) {out.start = start; out.end = end;};

		^FLSC_Score(out, defs, busses, msgs, bundles, start, end, rank);
	}
}
