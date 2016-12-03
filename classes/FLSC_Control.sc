FLSC_Control : FLSC_AbstractUGen {
	// le nom du contrôle
	var controlName;
	// polymorphisme des UGen: varList et timeControls
	var <varList, <timeControls;

	*new {|symb|
		^super.new.controlInit(symb);
	}

	controlInit {|symb|
		controlName = symb;
		varList = List();
		timeControls = List();
		^this;
	}

	value {|varDict|
		var input = varDict[controlName];
		^switch(input.rate)
		{'audio'}   {In.ar(controlName.ir())}
		{'control'} {In.kr(controlName.ir())}
		{'scalar'}  {controlName.ir};
	}
}