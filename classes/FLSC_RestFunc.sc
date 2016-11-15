FLSC_RestFunc : FLSC_Function {
	*new {|context, parms, func|
		^super.new(context, parms, func);
	}

	value {|args|
		var parmSize = funcParms.size;
		^super.value(if(parmSize > 1, {args[..parmSize-2]}, {[]}) ++
			[args[parmSize-1..]]);
	}
}