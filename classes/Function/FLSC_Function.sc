FLSC_Function {
	// la table de symboles à laquelle se réfère la fonction: un FLSC_Context
	var baseContext;
	// la liste des paramètres à associer: un Array de Symbol
	var funcParms;
	// l'opération à executer: une Function d'un FLSC_Context
	var function;
	// la fonction peut-elle appeler plusieurs fois la même variable ?
	var nonUnique;

	*new {|context, parms, func, nonUnique = false|
		^super.new.funcInit(context, parms, func, nonUnique);
	}

	funcInit {|context, parms, func, nUnq|
		baseContext = context;
		funcParms = parms;
		function = func;
		nonUnique = nUnq;
		^this;
	}

	value {|args|
		var execContext = FLSC_Context(baseContext,
			[funcParms,
				(args ++ (FLSC_Nil()!(funcParms.size - args.size)))[..funcParms.size-1]
		].flop, nonUnique);
		^function.value(execContext);
	}

	// nécessaire pour ajouter la fonction dans son propre contexte en cas de letrec
	addContext {|keysValues|
		baseContext = FLSC_Context(baseContext, keysValues, true);
	}
}