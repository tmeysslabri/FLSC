FLSC_Module : FLSC_FuncDef {
	*new {|parms, body|
		^super.new(parms, body);
	}

	asFLSC {
		var text = super.asFLSC;
		^("(module " ++ text[0] ++ text[1] ++ " " ++ text[2] ++ ")");
	}
}