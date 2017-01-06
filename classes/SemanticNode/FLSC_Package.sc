FLSC_Package : FLSC_SemanticNode {
	new {|bindList|
		^super.new(bindList);
	}

	value {|context|
		var bindings = nodeVal.inject([])
		{|res, item| res ++ [[item[0], item[1].value(FLSC_Context(context, res))]]};
		^FLSC_Context(context, bindings);
	}

	asFLSC {
		var letList = nodeVal.collect
		{|item| "(" ++ item[0].asString + item[1].asFLSC ++ ")"};
		^("(package" + letList[1..].inject(letList[0], (_+_)) ++ ")");
	}
}
