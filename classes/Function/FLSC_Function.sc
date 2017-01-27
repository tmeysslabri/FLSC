FLSC_Function : FLSC_Object {
	// description du type
	classvar <typeDesc;

	// la table de symboles à laquelle se réfère la fonction: un FLSC_Context
	var baseContext;
	// la liste des paramètres à associer: un Array de Symbol
	var funcParms;
	// l'opération à executer: une Function d'un FLSC_Context
	var function;

	*initClass {
		typeDesc = "Function";
	}

	*new {|context, parms, func|
		^super.new.funcInit(context, parms, func);
	}

	funcInit {|context, parms, func|
		baseContext = context;
		funcParms = parms;
		function = func;
		^this;
	}

	value {|args|
		var execContext;
		if(funcParms.notEmpty)
		{
			execContext = FLSC_Context(baseContext,
			[funcParms,
					(args ++ (FLSC_Nil()!(funcParms.size - args.size)))[..funcParms.size-1]
			].flop);
		} {
			execContext = baseContext;
		}
		^function.value(execContext);
	}

	// nécessaire pour ajouter la fonction dans son propre contexte en cas de letrec
	addContext {|keysValues|
		baseContext = FLSC_Context(baseContext, keysValues);
	}
}