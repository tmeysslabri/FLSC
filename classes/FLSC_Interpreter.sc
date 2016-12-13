FLSC_Interpreter {
	// la bibliothèque: un FLSC_Context
	var library;
	// la chaîne de caractères en entrée
	var <inputString;
	// l'arbre sémantique associé
	var <semanticTree;
	// la valeur calculée par l'arbre sémantique
	var <treeValue;
	// la FLSC_Score résultante
	var <scoreValue;

	*getTree {|string|
		var cmd = "echo '" ++ string ++ "' | " ++
		Platform.userExtensionDir +/+ "FLSC/extras/FLSC2SC/build/flsc2sc";
		^cmd.unixCmdGetStdOut.interpret;
	}

	*evaluateLibrary {|context, string|
		^this.getTree(string).value(context);
	}

	*new {
		^super.new.interpreterInit;
	}

	interpreterInit{
		library = FLSC_Context.library;
		^this;
	}

	read {|string|
		inputString = string;
		semanticTree = this.class.getTree(string);
		// réinitialiser les valeurs éventuellement calculées
		treeValue = nil;
		scoreValue = nil;
		^this;
	}

	evaluate {
		^treeValue = semanticTree.value(library);
	}

	asFLSCScore {
		^scoreValue = treeValue.asFLSCScoreSpec.asFLSCScore;
	}

	play {
		^scoreValue.play;
	}

	evaluateAndPlay {
		var res;
		if(semanticTree.isKindOf(FLSC_Error)) {^semanticTree.asFLSC};
		res = this.evaluate;
		if(treeValue.isFLSCScoreSpec || treeValue.isArray)
		{this.asFLSCScore.play}
		{treeValue.postln};
		^this;
	}

	asFLSC {
		^semanticTree.asFLSC;
	}
}
