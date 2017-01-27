FLSC_Object {
	classvar types;

	*initClass {
		types = Dictionary.newFrom([
			SimpleNumber, "Number",
			FLSC_SemanticNode, "Expression",
			FLSC_Function, "Function",
			FLSC_UGen, "Primitive",
			FLSC_ScoreSpec, "Signal"]);
	}

	doesNotUnderstand {|selector ... args|
		var respondingTypes = List();
		types.keysValuesDo
		{|class, desc|
			if(class.findRespondingMethodFor(selector).notNil)
			{respondingTypes.add(desc)}
		};
		if(respondingTypes.notEmpty)
		{FLSC_Error("Type % found where expecting types %"
			.format(this.class.typeDesc, respondingTypes.asArray)).throw}
		{DoesNotUnderstandError(this, selector, args).throw};
	}
}