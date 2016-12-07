FLSC_ScoreSpec {
	// la base d'échantillonnage, compatible avec les classes SC
	var <rate;
	// la liste des ScoreSpec référencées, dans leur ordre d'apparition
	var <varList;

	*new {|timeBase, vars|
		^super.new.scoreSpecInit(timeBase, vars);
	}

	scoreSpecInit {|timeBase, vars|
		rate = timeBase;
		varList = vars;
		^this;
	}

	isFLSCScoreSpec { ^true; }
	encapsulate { ^FLSC_VarSpec(this) }
	asFLSCScoreSpec { ^this; }
}
