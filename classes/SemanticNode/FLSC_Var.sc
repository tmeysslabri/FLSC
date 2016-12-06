FLSC_Var : FLSC_SemanticNode {
	*new{|symb|
		^super.new(symb);
	}

	value {|context|
		^context.at(nodeVal);
	}
}