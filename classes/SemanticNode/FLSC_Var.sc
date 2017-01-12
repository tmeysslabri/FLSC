FLSC_Var : FLSC_SemanticNode {
	*new{|symb|
		^super.new(symb);
	}

	semanticValue {|context|
		^context.at(nodeVal);
	}
}