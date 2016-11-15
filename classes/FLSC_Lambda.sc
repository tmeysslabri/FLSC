FLSC_Lambda : FLSC_RestFuncDef {
	*new {|parms, body, rest = false|
		^super.new(parms, body, rest);
	}

	value {|context|
		^super.value(context, {|callContext| nodeVal.value(callContext)});
	}

	asFLSC {
		var text = super.asFLSC;
		^("(lambda " ++ text[0] ++ " " ++ text[1] ++ ")");
	}
}