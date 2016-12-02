FLSC_Module : FLSC_FuncDef {
	*new {|parms, body|
		^super.new(parms, body);
	}

	value {|context|
		var baseContext = FLSC_Context(context, parmNames.collect
			{|item| [item, FLSC_VarUGen(FLSC_Control(item))] });
		var uGenGraph = nodeVal.value(baseContext);

		^FLSC_ModFunc(uGenGraph.varList, parmNames,
			{|varDict| uGenGraph.value(varDict)}, uGenGraph.timeControls);
	}

	asFLSC {
		var text = super.asFLSC;
		^("(module " ++ text[0] ++ text[1] ++ " " ++ text[2] ++ ")");
	}
}