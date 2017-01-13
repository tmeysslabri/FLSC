FLSC_RestFuncDef : FLSC_FuncDef {
	// drapeau du paramètre spécial: booléen
	var hasRest;

	*new {|parms, body, rest = false|
		^super.new(parms, body).restFuncDefInit(rest);
	}

	restFuncDefInit {|rest|
		hasRest = rest;
		^this;
	}

	semanticValue {|context|
		if(hasRest,
			{ ^FLSC_RestFunc(context, parmNames, {|callContext| nodeVal.value(callContext)}); },
			{ ^super.semanticValue(context); }
		);
	}

	asFLSC {
		var text = super.asFLSC;
		^[
			text[0] ++ if(hasRest, {"& "}, {""}) ++ text[1],
			text[2]
		];
	}
}