FLSC_Bus {
	// le type de Bus représenté: 'audio' ou 'control'
	var <type;
	// le début et la fin de la réservation
	// peuvent être affectés a posteriori par FLSC_ListSpec
	var <>start, <>end;
	// le Bus finalement alloué
	var <>bus;

	*new {|rate, t0, tf|
		^super.new.busInit(rate, t0, tf)
	}

	busInit {|rate, t0, tf|
		type = rate;
		start = t0;
		end = tf;
	}

	value {
		^bus;
	}
}