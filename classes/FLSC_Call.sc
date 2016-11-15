FLSC_Call : FLSC_SemanticNode {
	// la liste des arguments: un Array de FLSC_SemanticNode
	var argList;

	*new {|sel, exprs|
		^super.new(sel).callInit(exprs);
	}

	callInit {|exprs|
		argList = exprs;
		^this;
	}

	value {|context|
		var args = argList.collect({|item| item.value(context)});
		^nodeVal.value(context).value(args);
	}

	asFLSC {
		^("(" ++ nodeVal.asFLSC ++ if(argList.size > 0,
			{ argList.inject("", {|acc, item| acc ++ " " ++ item.asFLSC }) },
			{""}
		) ++ ")");
	}
}