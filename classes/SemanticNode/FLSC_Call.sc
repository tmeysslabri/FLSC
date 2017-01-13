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

	semanticValue {|context|
		var res;
		var args = argList.collect({|item| item.value(context)});
		res = nodeVal.value(context).value(args);
		if(res.isFLSCScoreSpec) {res.parent = this}
		^res;
	}

	asFLSC {
		^("(" ++ nodeVal.asFLSC ++
			argList.inject("", {|acc, item| acc ++ " " ++ item.asFLSC }) ++ ")");
	}
}