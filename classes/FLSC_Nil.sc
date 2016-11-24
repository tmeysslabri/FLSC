FLSC_Nil : FLSC_SemanticNode {

	*new {
		^super.new(nil);
	}

	value {
		^this;
	}

	/*
	Has no effect because SC isn't rational about this
	isNil {
		^true;
	}

	notNil {
		^false;
	}
	*/

	// try this:
	== {|obj|
		^(this.hash == obj.hash);
	}

	!= {|obj|
		^(this.hash != obj.hash);
	}

	hash {
		^nil.hash;
	}
}