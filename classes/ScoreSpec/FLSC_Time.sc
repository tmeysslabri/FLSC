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
		var times = [
			(([_,{|t|t}]) ! (nbSeg - 1)) ++ [{|t|t}],
			[0] ++ ({|i|[i+1,0]} ! (nbSeg - 1))
		].flop;
		^times.collect {|item|
			var res = timeWarp.(item[0]) - timeWarp.(item[1]);
			if(res < 0)
			{Error("Anachronism in Time: duration(%) < 0".format(res)).throw};
			res;
		}
	}

	isFLSCTime { ^true }
}
