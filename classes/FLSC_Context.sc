FLSC_Context : Dictionary {
	// contexte de référence: un FLSC_Context
	var refContext;

	*new {|context, keysValues|
		^super.new.contextInit(context, keysValues);
	}

	contextInit {|context, keysValues|
		refContext = context;
		keysValues.do({|item| this.put(item[0], item[1])});
		^this;
	}

	at {|key|
		var value = super.at(key);

		if(value.isNil,
			{ if(refContext.notNil, { value = refContext.at(key) }) }
		);

		^value;
	}
}