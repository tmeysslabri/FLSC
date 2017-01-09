FLSC_Patch : FLSC_RestFuncDef {
	/*
	// la signature temporelle: un SemanticNode
	var signature;
	*/
	// la durée: un SemanticNode
	var duration;

	*new {|parms, duration, body, rest = false|
		^super.new(parms, body, rest).patchInit(duration);
	}

	patchInit {|dur|
		duration = dur;
		^this;
	}

	value {|context|
		var warpFunc = {|callContext|
			var sig = nodeVal.value(callContext).asFLSCScoreSpec;
			var dur = duration.value(callContext);
			/*
			var signValue = signature.value(callContext);
			var sign = switch(signValue.rank,
				1, {
					if(signValue[0] != 0)
					{[[0] ++ signValue]}
					{[signValue]}
				},
				2, {
					if(signValue[0][0] != 0)
					{[[0] ++ signValue[0]] ++ signValue[1..]}
					{signValue}
				},
				{Error("Patch signature not compliant: %".format(signValue)).throw}
			);
			*/
			/*
			var warp = FLSC_WarpSpec({|pair|
				var t = pair[0];
				var i = pair[1];
				if(i.isNil) {i = (0..sign.size-1)};
				if(i.size == 1) {i = i[0]};
				case
				// cas classique, on accède à une signature donnée
				{i.isNumber} {
					case
					{t == 'end'}  {sign[i].last}
					{t.isInteger} {sign[i][t]}
					{true}        {Error("Non-integer signature index").throw}
				}
				// cas d'un sous-ensemble
				// il vaut mieux n'accéder qu'à 0 et 'end'
				// sauf si on connaît l'ordre des éléments
				{i.isArray} {
					var signMerge = i.collect(sign[_]).flatten;
					case
					{t == 0}      {signMerge.reduce(min(_,_))}
					{t == 'end'}  {signMerge.reduce(max(_,_))}
					{t.isInteger} {
						"WARNING: multiple signatures selected".postln;
						signMerge[t]
					}
					{true}        {Error("Non-integer signature index").throw}
				}
				{true} {Error("Non-compatible signature selection").throw}
			}, sig);
			*/
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
		^("(patch " ++ text[0] ++ " " ++ duration.asFLSC ++ " " ++ text[1] ++ ")");
	}
}
