FLSC_Conditional : FLSC_SemanticNode {
	// la fonction de décision
	var choiceFunc;

	*new {|exprs, func|
		^super.new(exprs).conditionalInit(func);
	}

	conditionalInit {|func|
		choiceFunc = func;
		^this;
	}

	semanticValue {|context|
		^nodeVal[choiceFunc.value(context)].value(context);
	}
}