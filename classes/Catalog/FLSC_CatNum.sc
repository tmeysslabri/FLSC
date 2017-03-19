FLSC_CatNum : FLSC_Catalog {
	*new {|expr|
		^super.new.catNumInit(expr);
	}

	catNumInit {|expr|
		flscString = expr.asString;
	}

	asPathExprPairList {
		^[[flscString, flscString]];
	}
}