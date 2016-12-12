FLSC_Bundle {
	// la liste de FLSC_MsgPair contenue
	var msgList;
	// les dates de début et de fin
	var start, end;

	*new {|t0, tf, msgs|
		^super.new.bundleInit(t0, tf, msgs);
	}

	bundleInit {|t0, tf, msgs|
		start = t0;
		end = tf;
		msgList = msgs;
	}

	asSCScorePair {|server, groups|
		var startBundle = [], endBundle = [];
		msgList.do {|item|
			var synth;
			startBundle = server.makeBundle(false,
				{ synth = item.asSynth(groups) }, startBundle);
			endBundle = server.makeBundle(false,
				{ synth.free }, endBundle);
		};
		^[[start, startBundle], [end, endBundle]];
	}
}
