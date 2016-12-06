FLSC_ListSpec {
	// la liste de ListSpec/ScoreSpec/VarSpec à laquelle on se réfère
	var subSpecs;
	// la liste des variables référencées
	var varList;

	*new {|specs|
		^super.new.listSpecInit(specs);
	}

	listSpecInit {|specs|
		subSpecs = specs;
		varList = subSpecs.inject(List(), _.union(_));
		^this;
	}

	rate {
		^'audio';
	}
}