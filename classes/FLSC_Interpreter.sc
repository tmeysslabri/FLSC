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
		Platform.userExtensionDir +/+ "FLSC" +/+ "extras" +/+ "FLSC2SC" +/+
		"build" +/+ "flsc2sc";
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

	readFile {|name = "test.flsc",
		dir (Platform.userExtensionDir +/+ "FLSC" +/+ "extras" +/+ "examples")|
		var file = File(dir +/+ name, "r");
		this.read(file.readAllString);
		file.close;
		^this;
	}

	evaluate {
		if(semanticTree.isKindOf(FLSC_Error))
		{^treeValue = semanticTree.asFLSC}
		{^treeValue = semanticTree.value(library)};
	}

	asFLSCScore {|before = 0, after = 0|
		if(treeValue.isNil) {this.evaluate};
		if(treeValue.isFLSCScoreSpec ||
			(treeValue.isArray && treeValue.isString.not))
		{^scoreValue = treeValue.asFLSCScoreSpec.asFLSCScore(before, after)}
		{^scoreValue = treeValue};
	}

	play {|doneAction = nil|
		if(scoreValue.isNil) {this.asFLSCScore};
		if(scoreValue.isKindOf(FLSC_Score))
		{^scoreValue.play(doneAction)}
		{^scoreValue};
	}

	recordNRT {|outFile, before = 0, after = 0, headerFormat = "WAV", sampleRate = 44100,
		sampleFormat = "int16", numChannels = 2, doneAction = nil|
		if(scoreValue.isNil) {this.asFLSCScore(before, after)};
		if(scoreValue.isKindOf(FLSC_Score))
		{^scoreValue.recordNRT(outFile, headerFormat, sampleRate,
			sampleFormat, numChannels, doneAction)}
		{^scoreValue};
	}

	getFileList {|subDir ("."),
		baseDir (Platform.userExtensionDir +/+ "FLSC" +/+ "extras" +/+ "examples")|
		var list = ("find" + baseDir +/+ subDir + "-name *.flsc").unixCmdGetStdOut.split($\n);
		^list.[..list.size-2];
	}

	playDir {|subDir, baseDir|
		var list = this.getFileList(subDir, baseDir).postln;
		var rec = {|list|
			var next = list.first;
			var rest = list[1..];
			"Executing: %".format(next.split.last).postln;
			this.readFile(next, "");
			this.inputString.postln;
			this.play({
				if(rest.notEmpty) {rec.value(rest)} {"Done.".postln};
			})
		}.value(list);
	}

	asFLSC {
		if(semanticTree.notNil)
		{^semanticTree.asFLSC}
		{^nil};
	}
}
