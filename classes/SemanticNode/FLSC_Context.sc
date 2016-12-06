FLSC_Context : Dictionary {
	// contexte de référence: un FLSC_Context
	var refContext;

	*new {|context, keysValues|
		^super.new.contextInit(context, keysValues);
	}

	*library {|version = "0.1", libName = "flscLib"|
		var path = Platform.userExtensionDir +/+
		"FLSC/extras/library" +/+ libName ++ "-" ++ version ++ ".scd";
		var content = "[]";
		if(File.exists(path), {
			content = File.open(path, "r").readAllString;
		});
		^this.new(nil, content.interpret);
	}

	contextInit {|context, keysValues|
		refContext = context;
		keysValues.do({|item|
			var key = item[0];
			var value = item[1];
			// encapsuler les FLSC_UGen avec des FLSC_VarUGen
			if(value.isFLSCUGen) { value = value.encapsulate };
			this.put(key, value);
		});
		^this;
	}

	at {|key|
		var value = super.at(key);

		if(value.isNil) {
			if(refContext.notNil)
			{ value = refContext.at(key) }
			{ Error("Variable % not found.".format(key)).throw }
		};

		^value;
	}
}