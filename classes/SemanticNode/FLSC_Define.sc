FLSC_Define : FLSC_SemanticNode {
	// la liste des définitions
	var defs;

	*new {|defs, body|
		^super.new(body).defineInit(defs);
	}

	defineInit {|defPairs|
		defs = defPairs;
	}

	value {|context|
		var bindings = defs.inject([])
		{|res, item| res ++ [[item[0], item[1].value(FLSC_Context(context, res))]]};
		/*
		var newContext = FLSC_Context(context, bindings);
		if(nodeVal.isNil)
		{^newContext}
		{^nodeVal.value(newContext)};
		*/
		if(nodeVal.isNil)
		// dans le cas où on définit un paquetage, on abandonne les définitions précédentes
		// !!! dans ce cas on ne doit avoir qu'un seul define dans un paquetage
		{^FLSC_Context(nil, bindings)}
		{^nodeVal.value(FLSC_Context(context, bindings))};
	}

	asFLSC {
		var letList = nodeVal.collect
		{|item| "(" ++ item[0].asString + item[1].asFLSC ++ ")"};
		^("(define" + letList[1..].inject(letList[0], (_+_)) ++ ")");
	}
}
