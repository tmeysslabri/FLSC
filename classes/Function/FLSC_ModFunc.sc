FLSC_ModFunc : FLSC_Function {
	// l'identifiant unique de la ModFunc
	var funcId;
	// les couples [UID, FLSC_Time] référencés par les FLSC_TimeUGen du graphe
	var timeControls;

	*new {|context, parms, func, times|
		^super.new(context, parms, func).modFuncInit(times);
	}

	modFuncInit {|times|
		funcId = FLSC_UID.next;
		timeControls = times;
		^this;
	}

	value {|args|
		// la SynthDef engendrée
		var synthDef;
		// le suffixe de cette variante de la SynthDef
		var suffix;
		// les arguments à la SynthDef
		var argDict = Dictionary();
		// la liste des variables référencées par les arguments de type ScoreSpec
		var specVarList = List();
		// le dictionnaire des valeurs référencées par les FLSC_VarUGen
		var varDict = IdentityDictionary();
		// le uGenGraph résultant de l'évaluation des FLSC_UGen
		var uGenGraph;

		// on créée la varList de la ModSpec
		args.collect {|item| if((item.rate == 'audio') || (item.rate == 'control'))
			{ specVarList = specVarList ++ item.varList }
		};

		// on créée le dictionnaire des arguments
		[
			funcParms,
			(args ++ (FLSC_Nil()!(funcParms.size - args.size)))[..funcParms.size-1]
		].flop.do {|item| argDict.put(item[0], item[1]) };

		// on ajoute les timeControls aux arguments
		timeControls.do {|item| argDict.put(item[0], item[1]) };

		// on l'ajoute à varDict, pour que les FLSC_Control puissent trouver leur argument
		varDict.putAll(argDict);

		// on calcule le suffixe
		suffix = args.inject("_") {|acc, it| acc ++
			switch(it.rate)
			{'scalar'}  {"n"}
			{'control'} {"c"}
			{'audio'}   {"a"}
		};

		// on peut maintenant calculer le graphe de UGen et donc la SynthDef
		synthDef = SynthDef((funcId ++ suffix).asSymbol, {|out|
			// on ajoute le résultat de l'évaluation de la varList des UGen
			// (nommée baseContext ici, en référence à FLSC_Function)
			baseContext.do {|item| varDict.put(item, item.value(varDict)) };
			uGenGraph = function.value(varDict);
			switch(uGenGraph.rate)
			{'audio'}   {Out.ar(out, uGenGraph)}
			{'control'} {Out.kr(out, uGenGraph)}
		});

		// on peut finalement renvoyer le résultat: une FLSC_ModSpec
		^FLSC_ModSpec(uGenGraph.rate, specVarList , synthDef, argDict);
	}
}