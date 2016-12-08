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
		varList = subSpecs.inject(List(), _.union(_.varList));
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
		// normalement ceci devrait être vide, puisqu'on est en dehors de patch
		var msgs = List();
		// les FLSC_Bundle des sous-graphes
		var bundles = List();
		// on itère sur les éléments
		subSpecs.do {|item|
			case
			{item.isNumber}        {item}
			{item.isFLSCTime}      {item.value(timeWarp)}
			{item.isFLSCScoreSpec} {
				// on évalue le sous-graphe
				var score = item.value(nil, timeWarp, varDict);
				// on ajoute les bus, les définitions, les messages, les bundle
				busses.addAll(score.busList);
				defs.putAll(score.defDict);
				msgs.addAll(score.bundle);
				bundles.addAll(score.bundleList);
			}
		};

		// le Bus de sortie est celui demandé, vérifier son existence
		if(outBus.isNil) {Error("ListSpec outBus is nil").throw};

		^FLSC_Score(outBus, defs, busses, msgs, bundles);
	}
}