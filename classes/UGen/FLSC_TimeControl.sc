FLSC_TimeControl : FLSC_Control {
	*new {|nbSeg = 1|
		^super.new(FLSC_UID.next.asSymbol).timeControlInit(nbSeg);
	}

	timeControlInit {|nbSeg|
		timeControls.add([controlName, FLSC_Time(nbSeg)]);
		^this;
	}

	value {|varDict|
		var times = varDict[controlName];
		^controlName.ir(0!times.nbSeg);
	}

}
