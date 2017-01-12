FLSC_List : FLSC_SemanticNode {
	*new {|list|
		^super.new(list);
	}

	semanticValue {|context|
		^nodeVal.collect {|item| item.value(context) };
	}

	asFLSC {
		^if(nodeVal.size > 0) {
			"[" ++ nodeVal[1..].inject(nodeVal[0].asFLSC)
			{|acc, item| acc ++ " " ++ item.asFLSC } ++ "]"
		} {
			"[]"
		};
	}
}