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
		if(hasRest,
			{ ^FLSC_RestFunc(context, parmNames, func); },
			{ ^super.value(context, func); }
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