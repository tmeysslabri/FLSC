FLSC_TimeControl : FLSC_Control {
	*new {|times|
		^super.new(FLSC_UID.next.asSymbol).timeControlInit(times);
	}

	timeControlInit {|times|
		timeControls.add([controlName, FLSC_Time.newFrom(times)]);
		^this;
	}

	value {|varDict|
		var times = varDict[controlName];
		^controlName.ir(0!(times.size - 1));
	}

}
