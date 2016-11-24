FLSC_Nil : FLSC_SemanticNode {

	*new {
		^super.new(nil);
	}

	value {
		^this;
	}

	isFLSCNil {
		^true;
	}

	notFLSCNil {
		^false;
	}

	== {|obj|
		^obj.isFLSCNil;
	}

	!= {|obj|
		^obj.notFLSCNil;
	}

	hash {
		^this.instVarHash;
	}
}