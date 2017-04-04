FLSC_CatArr : FLSC_Catalog {
	// le nom de l'option
	var name;

	*new {|expr|
		^super.new.catArrInit(expr);
	}

	catArrInit {|expr|
		var content = expr.collect(_.asString).reduce('+');
		name = content.replace(" ", "_");
		flscString = "[" ++ content ++ "]";
	}

	asPathExprPairList {
		^[[name, flscString]];
	}
}