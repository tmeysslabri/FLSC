FLSC_UGenFunc : FLSC_Function {
	value {|args|
		var isUGen = args.inject(false) {|acc, item| acc || item.isFLSCUGen};
		if(isUGen)
		{
			^FLSC_UGen(args, {|subs|
				super.value(subs);
			});
		} {
			^super.value(args);
		}
	}
}