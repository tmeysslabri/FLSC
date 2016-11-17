FLSC_If : FLSC_Conditional {
	// la fonction de test: un FLSC_SemanticNode
	var testFunc;
	*new {|test, then, else|
		^super.new([then, else], {|context|
			test.value(context).not.binaryValue;
		}).ifInit(test);
	}

	ifInit {|test|
		testFunc = test;
		^this;
	}

	asFLSC {
		^("(if " ++ testFunc.asFLSC ++ " " ++ nodeVal[0].asFLSC ++
			" " ++ nodeVal[1].asFLSC ++ ")");
	}
}