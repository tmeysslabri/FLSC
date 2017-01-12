FLSC_Require : FLSC_SemanticNode {
	var fileName;

	*new {|fileName, body|
		^super.new(body).requireInit(fileName);
	}

	requireInit {|file|
		fileName = file;
		^this;
	}

	value {|context, library, baseDir (Platform.userExtensionDir +/+ "FLSC" +/+ "extras")|
		var path = if(fileName[0] == $/)
		{ fileName }
		{ baseDir +/+ fileName };
		var file = File(path, "r");
		var package, newContext;
		if(file.isOpen) {
			package = FLSC_Interpreter.getTree(file.readAllString);
			file.close;
		} {
			FLSC_Error("Cannot open file: %".format(path)).throw;
		};
		if(package.isKindOf(FLSC_ErrNode))
		{ FLSC_Error("Error in file %: %".format(path, package.asFLSC)).throw };
		// on évalue les paquetages dans le contexte de la bibliothèque uniquement
		// les paquetages ne doivent pas interférer (sauf quand c'est explicite)
		newContext = package.value(library, library, path.dirname);
		if(newContext.isKindOf(FLSC_Context).not)
		{ FLSC_Error("Not a valid package file: %:".format(path)).throw };
		// on effectue le chaînage du paquetage sur le contexte courant
		newContext.refContext = context;
		// on évalue l'expression en passant les paramètres supplémentaires
		// pour un require suivant éventuel
		^nodeVal.value(newContext, library, baseDir);
	}

	asFLSC {
		^("(require \"" ++ fileName.asString ++ "\")\n" ++ nodeVal.asFLSC);
	}
}