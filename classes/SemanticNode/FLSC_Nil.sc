FLSC_Nil : FLSC_SemanticNode {

	*new {
		^super.new(nil);
	}

	value {
		^this;
	}

	rate {
		^nil;
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

	asArray {
		^[];
	}
}