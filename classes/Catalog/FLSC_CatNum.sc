FLSC_CatNum : FLSC_Catalog {
	// le nom associé à l'option
	var name;

	*new {|expr|
		^super.new.catNumInit(expr);
	}

	catNumInit {|expr|
		// nombre de chiffres avant la virgule
		var order = min(max(expr.abs.log10.floor + 1, -10), 10);
		var digits = (expr.abs * (10**(4-order))).trunc.asString.replace(".","").padRight(4,"0");
		var base = 10;
		flscString = expr.asString;
		if (expr.sign == -1) {order = order.neg} {base = 30};
		base = (base + order).asString.padLeft(2, "0");
		name = base ++ "_" ++ digits;
	}

	asPathExprPairList {
		^[[name, flscString]];
	}
}