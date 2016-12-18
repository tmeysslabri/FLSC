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
			var warp = FLSC_WarpSpec({|pair|
				var t = pair[0];
				var i = pair[1];
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
					var signMerge = i.collect(sign[_]).flatten.sort;
					case
					{t == 'end'}  {signMerge.last}
					{t.isInteger} {signMerge[t]}
					{true}        {Error("Non-integer signature index").throw}
				}
				// cas où on accède à la totalité de la signature
				// il vaut mieux n'accéder qu'à 0 et 'end'
				// sauf si on connaît l'ordre des éléments
				{i.isNil} {
					var signMerge = sign.flatten.sort;
					case
					{t == 'end'}  {signMerge.last}
					{t.isInteger} {signMerge[t]}
					{true}        {Error("Non-integer signature index").throw}
				}
			}, sig);
			FLSC_GlobalSignSpec(sig.rate, warp); };

		if(hasRest)
		{
			^FLSC_RestFunc(context, parmNames, warpFunc)
		} {
			^FLSC_Function(context, parmNames, warpFunc)
		};
	}

	asFLSC {
		var text = super.asFLSC;
		^("(patch " ++ text[0] ++ " " ++ signature.asFLSC ++ " " ++ text[1] ++ ")");
	}
}
