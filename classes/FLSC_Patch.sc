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

	asFLSC {
		var text = super.asFLSC;
		^("(patch " ++ text[0] ++ " " ++ signature.asFLSC ++ " " ++ text[1] ++ ")");
	}
}