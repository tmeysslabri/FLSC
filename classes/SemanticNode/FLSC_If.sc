FLSC_If : FLSC_Conditional {
	// la fonction de test: un FLSC_SemanticNode
	var testFunc;
	*new {|test, then, else|
		^super.new([then, else], {|context|
			var testValue = test.value(context);
			if(testValue.isKindOf(Boolean).not)
			{FLSC_Error("Test value is not a Boolean: %".format(testValue)).throw};
			testValue.not.binaryValue;
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