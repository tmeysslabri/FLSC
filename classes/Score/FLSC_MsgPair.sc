FLSC_MsgPair {
	// le nom de la définition associée
	var defName;
	// les arguments d'appel
	var synthArgs;
	// le rang
	var rank;

	*new {|name, args, rank|
		^super.new.msgPairInit(name, args, rank);
	}

	msgPairInit {|name, args, rankNum|
		defName = name;
		synthArgs = args;
		rank = rankNum;
		^this;
	}

	asSynth {|groups|
		^Synth(defName, synthArgs.asKeyValuePairs, groups[rank]);
	}
}
