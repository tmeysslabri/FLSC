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
		var warpFunc = {|callContext|
			var sig = nodeVal.value(callContext).asFLSCScoreSpec;
			var sign = signature.value(callContext);
			var warp = FLSC_WarpSpec({|t|
				case
				{t == 0}      {0}
				{t == 'end'}  {sign.last}
				{t.isInteger} {sign[t-1]}
				{true}        {Error("Non-integer signature index").throw}
			}, sig);
			FLSC_SignSpec(sig.rate, warp); };

		if(hasRest)
		{
			^FLSC_RestFunc(context, parmNames, warpFunc, true)
		} {
			^FLSC_Function(context, parmNames, warpFunc, true)
		};
	}

	asFLSC {
		var text = super.asFLSC;
		^("(patch " ++ text[0] ++ " " ++ signature.asFLSC ++ " " ++ text[1] ++ ")");
	}
}
