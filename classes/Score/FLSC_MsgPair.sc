FLSC_MsgPair {
	// le nom de la définition associée
	var defName;
	// les arguments d'appel
	var synthArgs;

	*new {|name, args|
		^super.new.msgPairInit(name, args);
	}

	msgPairInit {|name, args|
		defName = name;
		synthArgs = args;
		^this;
	}

	asSynth {
		^Synth(defName, synthArgs.collect({|item| item.value}).asKeyValuePairs,
			addAction: 'addToTail');
	}

	debug {
		^[defName, synthArgs];
	}
}