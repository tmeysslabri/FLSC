FLSC_Interpreter {
	// la chaîne de caractères en entrée
	var <inputString;
	// l'arbre sémantique associé
	var <semanticTree;
	// la valeur calculée par l'arbre sémantique
	var <treeValue;
	// la FLSC_Score résultante
	var <scoreValue;

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
		// réinitialiser les UID
		FLSC_UID.reset;
		^treeValue = semanticTree.value(FLSC_Context.library);
	}

	evaluateLibrary {|context|
		^semanticTree.value(context);
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
