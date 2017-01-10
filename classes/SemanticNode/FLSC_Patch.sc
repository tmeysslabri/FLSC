FLSC_Patch : FLSC_RestFuncDef {
	/*
	// la signature temporelle: un SemanticNode
	var signature;
	*/
	/*
	// la durée: un SemanticNode
	var duration;

	*new {|parms, duration, body, rest = false|
		^super.new(parms, body, rest).patchInit(duration);
	}

	patchInit {|dur|
		duration = dur;
		^this;
	}
	*/
	value {|context|
		var warpFunc = {|callContext|
			// var sig = nodeVal.value(callContext).asFLSCScoreSpec;
			// var dur = duration.value(callContext);
			var pair = nodeVal.value(callContext);
			var sig = pair[1].asFLSCScoreSpec;
			var dur = pair[0];
			var warp = FLSC_WarpSpec({|t| t.(dur)}, sig);
			FLSC_GlobalTimeSpec(sig.rate, warp);
		};

		if(hasRest)
		{
			^FLSC_RestFunc(context, parmNames, warpFunc)
		} {
			^FLSC_Function(context, parmNames, warpFunc)
		};
	}

	asFLSC {
		var text = super.asFLSC;
		^("(patch " ++ text[0] ++ " " /*++ duration.asFLSC ++ " "*/ ++ text[1] ++ ")");
	}
}
