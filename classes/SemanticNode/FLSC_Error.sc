FLSC_ErrNode : FLSC_SemanticNode {
	*new {|string|
		^super.new(string);
	}

	value {
		FLSC_LocError(nodeVal, start, end).throw;
	}
}