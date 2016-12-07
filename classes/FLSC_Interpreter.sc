FLSC_Interpreter {
	// la chaîne de caractères en entrée
	var inputString;
	// l'arbre sémantique associé
	var semanticTree;

	*new {|string|
		^super.new.interpreterInit(string);
	}

	interpreterInit{|string|
		var cmd = "echo '" ++ string ++ "' | " ++
		Platform.userExtensionDir +/+ "FLSC/extras/FLSC2SC/build/flsc2sc";
		inputString = string;
		semanticTree = cmd.unixCmdGetStdOut.interpret;
		^this;
	}

	evaluate {
		^semanticTree.value(FLSC_Context.library);
	}

	evaluateLibrary {|context|
		^semanticTree.value(context);
	}

	asFLSC {
		^semanticTree.asFLSC;
	}
}

	