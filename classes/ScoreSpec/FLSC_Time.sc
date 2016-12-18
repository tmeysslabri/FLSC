FLSC_Time[slot] : Array {
	*newFrom {|times|
		^super.newFrom([times, nil!times.size].flop);
	}

	value {|timeWarp|
		var times = this.collect(timeWarp);
		^[times[1..], times[..(times.size-2)]].flop.collect
		{|item| item[0] - item[1]};
	}

	isFLSCTime { ^true }
}
