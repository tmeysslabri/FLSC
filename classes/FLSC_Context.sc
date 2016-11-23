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
		keysValues.do({|item| this.put(item[0], item[1])});
		^this;
	}

	atFail {|key|
		^super.atFail(key) {
			if(refContext.notNil)
			{ refContext.atFail(key) }
			{ Error("Variable % not found.".format(key)).throw }
		};
	}
}