FLSC_Module : FLSC_FuncDef {
	*new {|parms, body|
		^super.new(parms, body);
	}

	value {|context|
		// on rajoute les FLSC_Control correspondants aux paramètres dans le contexte
		var baseContext = FLSC_Context(context, parmNames.collect
			{|item| [item, FLSC_Control(item.value)] });
		// ceci permet d'évaluer directement le corps et d'obtenir
		// une expression algorithmiquement invariante (de type FLSC_UGen)
		var uGenGraph = nodeVal.value(baseContext);

		// on retourne la FLSC_ModFunc qui produit cette famille de modules
		^FLSC_ModFunc(uGenGraph.varList, parmNames,
			{|varDict| uGenGraph.value(varDict)}, uGenGraph.timeControls);
	}

	asFLSC {
		var text = super.asFLSC;
		^("(module " ++ text[0] ++ text[1] ++ " " ++ text[2] ++ ")");
	}
}