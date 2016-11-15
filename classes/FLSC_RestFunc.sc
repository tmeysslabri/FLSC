FLSC_RestFunc : FLSC_Function {
	// existence du paramètre spécial: un booléen
	var hasRest;

	*new {|context, parms, func, rest|
		^super.new(context, parms, func).restFuncInit(rest);
	}

	restFuncInit {|rest|
		hasRest = rest;
	}

	value {|args|
		var parmSize = funcParms.size;
		^super.value(if(hasRest,
			{ args[..parmSize-2] ++ [args[parmSize-1..]]; },
			{ args }
		));
	}
}