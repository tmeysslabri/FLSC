FLSC_Context : Dictionary {
	// contexte de référence: un FLSC_Context
	var refContext;

	*new {|context, keysValues|
		^super.new.contextInit(context, keysValues);
	}

	*library {|version = "0.1", libName = "flscLib"|
		var path = Platform.userExtensionDir +/+
		"FLSC/extras/library" +/+ libName ++ "-" ++ version;
		var scLib = path ++ ".scd";
		var flscLib = path ++ ".flsc.scd";
		var content;
		var lib;
		// lecture de la bibliothèque native
		if(File.exists(scLib))
		{
			var file = File(scLib, "r");
			content = file.readAllString;
			file.close;
		}
		{content = "[]"};
		lib = this.new(nil, content.interpret);
		// lecture de la bibliothèque FLSC
		if(File.exists(flscLib))
		{
			var file = File(flscLib, "r");
			content = file.readAllString;
			file.close;
		}
		{content = "[]"};
		content.interpret.collect {|batch| content = batch.collect
			{|item| [item[0], FLSC_Interpreter(item[1]).evaluateLibrary(lib)]};
			lib = this.new(lib, content);
		};
		^lib;
	}

	contextInit {|context, keysValues|
		refContext = context;
		keysValues.do({|item|
			var key = item[0];
			var value = item[1];
			// encapsuler les FLSC_UGen avec des FLSC_VarUGen
			// et les FLSC_ScoreSpec avec des FLSC_VarSpec
			if(value.isFLSCUGen || value.isFLSCScoreSpec || value.isArray)
			{ value = value.encapsulate };
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
