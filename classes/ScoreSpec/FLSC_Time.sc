FLSC_Time[slot] : Array {
	*newFrom {|times|
		^super.newFrom([times, nil!times.size].flop);
	}

	value {|timeWarp|
		var times = this.collect(timeWarp);
		^[times[1..], times[..(times.size-2)]].flop.collect
		{|item|
			var res = item[0] - item[1];
			if(res < 0)
			{Error("Anachronism in Time: duration(%) < 0".format(res)).throw};
			res;
		};
	}

	isFLSCTime { ^true }
}
