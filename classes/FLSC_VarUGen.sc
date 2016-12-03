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
}