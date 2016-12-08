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
}