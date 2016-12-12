FLSC_RestFunc : FLSC_Function {
	*new {|context, parms, func, nonUnique = false|
		^super.new(context, parms, func, nonUnique);
	}

	value {|args|
		var parmSize = funcParms.size;
		^super.value(args[..parmSize-2] ++ (FLSC_Nil()!(parmSize-1-args.size)) ++
			[args[parmSize-1..]]);
	}
}