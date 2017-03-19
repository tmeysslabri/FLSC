FLSC_CatSymb : FLSC_Catalog {
	*new {|expr|
		^super.new.catSymbInit(expr);
	}

	catSymbInit {|expr|
		flscString = expr;
	}

	asPathExprPairList {
		^[[flscString, flscString]];
	}
}