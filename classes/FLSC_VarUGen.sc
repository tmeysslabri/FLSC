FLSC_VarUGen : FLSC_SubsUGen {
	*new {|sub|
		^super.new([sub]).varUGenInit;
	}

	varUGenInit {
		varList = varList.union(subGraphs);
		^this;
	}

	value {|varDict|
		^subGraphs[0].value(varDict);
	}

	// identifier les FLSC_VarUGen pour ne pas les réencapsuler
	encapsulate { ^this }
}