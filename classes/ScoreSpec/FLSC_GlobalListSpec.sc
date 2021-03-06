FLSC_GlobalListSpec : FLSC_GlobalScoreSpec {
	// la liste de ListSpec/ScoreSpec/VarSpec à laquelle on se réfère
	var subSpecs;

	*new {|specs|
		var varList, subSpecs;
		// gérer le cas des listes récursives
		// -- ceci soulève une erreur si un membre n'est ni une liste, ni une ScoreSpec
		subSpecs = specs.collect(_.asFLSCScoreSpec(true));
		// on vérifie que les éléments sont tous de rate 'audio'
		subSpecs.do {|item| if(item.rate != 'audio')
			{FLSC_Error("Non-audio member in ListSpec: %".format(item)).throw}};
		varList = subSpecs.inject(List()) {|acc, it| acc.union(it.varList)};
		^super.new('audio', varList).listSpecInit(subSpecs);
	}

	listSpecInit {|specs|
		subSpecs = specs;
		^this;
	}

	scoreValue {|outBus, timeWarp, varDict, noWarpDict|
		this.initScore(outBus, timeWarp, varDict, noWarpDict);

		// on itère sur les éléments
		subSpecs.do {|item|
			// on évalue le sous-graphe
			var subScore = item.value(score.outBus, timeWarp, varDict, noWarpDict);
			// on ajoute les bus, les définitions, les messages, les bundle
			score.add(subScore);
		};

		// si aucun Bus de sortie n'est demandé, ajouter le début et la fin au Bus créé
		if(outBus.isNil) {
			score.outBus.start = score.start;
			score.outBus.end = score.end;
		};

		^score.checkTimes;
	}
}
