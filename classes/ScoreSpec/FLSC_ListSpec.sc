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
}