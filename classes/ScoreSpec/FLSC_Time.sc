FLSC_Time {
	// le nombre de segments
	var <nbSeg;

	*new {|nbSeg|
		^super.new.timeInit(nbSeg);
	}

	timeInit {|num|
		nbSeg = num;
	}

	value {|timeWarp|
		/*
		var times = [
			(([{|t|t},_]) ! (nbSeg - 1)) ++ [[{|t|t}]],
			[[0]] ++ ({|i|[0, i+1]} ! (nbSeg - 1))
		].flop;
		*/
		/*
		^times.collect {|item|
			var res = timeWarp.(item[0]) - timeWarp.(item[1]);
			if(res < 0)
			{FLSC_Error("Anachronism in Time: duration(%) < 0".format(res)).throw};
			res;
		}
		*/
		var times = [[0]] ++ ([0,_+1] ! (nbSeg-1)) ++ [[{|t|t}]];
		var res = times.collect(timeWarp).differentiate[1..];
		if(res.minItem < 0)
		{FLSC_Error("Anachronism in Time: duration(%) < 0".format(res.minItem)).throw};
		^res;
	}

	isFLSCTime { ^true }
}
