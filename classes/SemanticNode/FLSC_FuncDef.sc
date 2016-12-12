FLSC_FuncDef : FLSC_SemanticNode {
	// les noms des paramètres: un tableau de Symbol
	var parmNames;

	*new {|parms, body|
		^super.new(body).funcDefInit(parms);
	}

	funcDefInit {|parms|
		parmNames = parms;
		^this;
	}

	value {|context|
		^FLSC_Function(context, parmNames, {|callContext| nodeVal.value(callContext)}, true);
	}

	asFLSC {
		var size = parmNames.size;
		var parmsStart = "";
		max(0, (size-1)).do({|i|
			parmsStart = parmsStart ++ parmNames[i].asString ++ " ";
		});
		^[
			"(" ++ parmsStart,
			if(size > 0, { parmNames[size-1].asString }, {""}) ++ ")",
			nodeVal.asFLSC
		];
	}
}