FLSC_Cond : FLSC_Conditional {
	// la liste des fonctions de test: un Array de FLSC_SemanticNode
	var testFuncs;

	*new {|clauses|
		var unflopped = clauses.flop;
		var tests = unflopped[0];
		var exprs = unflopped[1];
		^super.new(exprs, {|context|
			var index = 0;
			while { index < tests.size and: {
				var testValue = tests[index].value(context);
				if(testValue.isKindOf(Boolean).not)
				{FLSC_Error("Test value is not a Boolean: %".format(testValue)).throw};
				testValue.not}
			}
			{ index = index + 1 };
			index;
		}).condInit(tests);
	}

	condInit {|tests|
		testFuncs = tests;
		^this;
	}

	asFLSC {
		var flopped = [testFuncs, nodeVal].flop;
		^("(cond" ++ flopped.inject("",
			{|acc,item| acc ++ " (" ++ item[0].asFLSC ++
				" " ++ item[1].asFLSC ++ ")"}) ++ ")");
	}
}