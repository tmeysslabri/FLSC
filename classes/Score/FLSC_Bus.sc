FLSC_Bus {
	// le type de Bus représenté: 'audio' ou 'control'
	var type;
	// le début et la fin de la réservation
	var start, end;

	*new {|rate, t0, tf|
		^super.new.busInit(rate, t0, tf)
	}

	busInit {|rate, t0, tf|
		type = rate;
		start = t0;
		end = tf;
	}
}