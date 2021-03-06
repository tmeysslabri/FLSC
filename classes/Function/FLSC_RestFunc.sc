FLSC_RestFunc : FLSC_Function {
	*new {|context, parms, func|
		^super.new(context, parms, func);
	}

	value {|args|
		var parmSize = funcParms.size;
		^super.value(args[..parmSize-2] ++ (FLSC_Nil()!(parmSize-1-args.size)) ++
			[args[parmSize-1..]]);
	}
}