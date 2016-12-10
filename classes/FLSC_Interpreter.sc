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
		treeValue = semanticTree.value(FLSC_Context.library);
		^this;
	}

	evaluateLibrary {|context|
		^semanticTree.value(context);
	}

	asFLSCScore {
		scoreValue = treeValue.asFLSCScoreSpec.asFLSCScore;
		^this;
	}

	play {
		scoreValue.play;
		^this;
	}

	evaluateAndPlay {
		this.evaluate;
		case
		{treeValue.isKindOf(FLSC_Error)} {treeValue.asFLSC.postln}
		{treeValue.isFLSCScoreSpec || treeValue.isArray}
		{this.asFLSCScore.play}
		{true} {treeValue.postln};
		^this;
	}

	asFLSC {
		^semanticTree.asFLSC;
	}
}

	