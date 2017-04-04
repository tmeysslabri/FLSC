FLSC_CatNum : FLSC_Catalog {
	// le nom associé à l'option
	var name;

	*new {|expr|
		^super.new.catNumInit(expr);
	}

	catNumInit {|expr|
		name = expr.asString.replace(".","_");
		flscString = expr.asString;
	}

	asPathExprPairList {
		^[[name, flscString]];
	}
}