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
		if(semanticTree.isKindOf(FLSC_Error))
		{^treeValue = semanticTree.asFLSC}
		{^treeValue = semanticTree.value(library)};
	}

	asFLSCScore {|before, after|
		if(treeValue.isNil) {this.evaluate};
		if(treeValue.isFLSCScoreSpec ||
			(treeValue.isArray && treeValue.isString.not))
		{^scoreValue = treeValue.asFLSCScoreSpec.asFLSCScore(before, after)}
		{^scoreValue = treeValue};
	}

	play {
		if(scoreValue.isNil) {this.asFLSCScore};
		if(scoreValue.isKindOf(FLSC_Score))
		{^scoreValue.play}
		{^scoreValue};
	}

	recordNRT {|before, after|
		if(scoreValue.isNil) {this.asFLSCScore(before, after)};
		if(scoreValue.isKindOf(FLSC_Score))
		{^scoreValue.recordNRT}
		{^scoreValue};
	}

	asFLSC {
		if(semanticTree.notNil)
		{^semanticTree.asFLSC}
		{^nil};
	}
}
