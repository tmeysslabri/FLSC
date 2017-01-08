FLSC_Require : FLSC_SemanticNode {
	var fileName;

	*new {|fileName, body|
		^super.new(body).requireInit(fileName);
	}

	requireInit {|file|
		fileName = file;
		^this;
	}

	value {|context, baseDir (Platform.userExtensionDir +/+ "FLSC" +/+ "extras")|
		var path = if(fileName[0] == $/)
		{ fileName }
		{ baseDir +/+ fileName };
		var file = File(path, "r");
		var package, newContext;
		if(file.isOpen) {
			package = FLSC_Interpreter.getTree(file.readAllString);
			file.close;
		} {
			Error("Cannot open file: %".format(path)).throw;
		};
		if(package.isKindOf(FLSC_Error))
		{ Error("Error in file %: %".format(path, package.asFLSC)).throw };
		newContext = package.value(context, path.dirname);
		if(newContext.isKindOf(FLSC_Context).not)
		{ Error("Not a valid package file: %:".format(path)).throw };
		^nodeVal.value(newContext, baseDir);
	}

	asFLSC {
		^("(require \"" ++ fileName.asString ++ "\")\n" ++ nodeVal.asFLSC);
	}
}