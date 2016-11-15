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

	value {|context, func|
		var flscFunc = super.value(context, func);
		^FLSC_RestFunc.new(flscFunc[0], flscFunc[1], flscFunc[2], hasRest);
	}

	asFLSC {
		var text = super.asFLSC;
		^[
			text[0] ++ if(hasRest, {"& "}, {""}) ++ text[1],
			text[2]
		];
	}
}