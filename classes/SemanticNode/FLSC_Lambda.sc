FLSC_Lambda : FLSC_RestFuncDef {
	*new {|parms, body, rest = false|
		^super.new(parms, body, rest);
	}

	semanticValue {|context|
		^super.semanticValue(context);
	}

	asFLSC {
		var text = super.asFLSC;
		^("(lambda " ++ text[0] ++ " " ++ text[1] ++ ")");
	}
}