FLSC_Patch : FLSC_RestFuncDef {
	// la signature temporelle: un SemanticNode
	var signature;

	*new {|parms, sign, body, rest = false|
		^super.new(parms, body, rest).patchInit(sign);
	}

	patchInit {|sign|
		signature = sign;
		^this;
	}

	value {|context|
		if(hasRest)
		{
			^FLSC_RestFunc(context, parmNames, {|callContext|
				var sig = nodeVal.value(callContext);
				var sign = signature.value(callContext);
				// !!! vérifier que t est un entier -- sinon il y a des Patch imbriqués
				var warp = FLSC_WarpSpec({|t| sign[t]}, sig);
				FLSC_SignSpec(sig.rate, List(), warp); })
		} {
			^FLSC_Function(context, parmNames, {|callContext|
				var sig = nodeVal.value(callContext);
				var sign = signature.value(callContext);
				// !!! vérifier que t est un entier -- sinon il y a des Patch imbriqués
				var warp = FLSC_WarpSpec({|t| sign[t]}, sig);
				FLSC_SignSpec(sig.rate, List(), warp); })
		};
	}

	asFLSC {
		var text = super.asFLSC;
		^("(patch " ++ text[0] ++ " " ++ signature.asFLSC ++ " " ++ text[1] ++ ")");
	}
}