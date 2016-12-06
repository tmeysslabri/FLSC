FLSC_VarUGen : FLSC_SubsUGen {
	*new {|sub|
		^super.new([sub]).varUGenInit;
	}

	varUGenInit {
		varList = varList.union(subGraphs);
		^this;
	}

	value {|varDict|
		^varDict[subGraphs[0]];
	}

	// identifier les FLSC_VarUGen pour ne pas les réencapsuler
	encapsulate { ^this }
}