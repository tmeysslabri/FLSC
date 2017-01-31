FLSC_Patch : FLSC_RestFuncDef {
	semanticValue {|context|
		var warpFunc = {|callContext|
			var pair = nodeVal.value(callContext);
			var sig = pair[1].asFLSCScoreSpec;
			var dur = pair[0];
			var warp = FLSC_WarpSpec({|t|
				// vérifier que la valeur est non-signée
				if(t.size > 1)
				{FLSC_Error("Signed time value in unsigned base: %".format(t)).throw};
				t.first.(dur)
			}, sig);
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
		^("(patch " ++ text[0] ++ " " ++ text[1] ++ ")");
	}
}
