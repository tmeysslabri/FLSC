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
	// le répertoire de base pour l'accès à des fichiers
	var <>baseDir;

	*getTree {|string|
		var escapeString = string.escapeChar($").escapeChar($$).escapeChar($`).escapeChar($\\);
		var cmd = "echo \"" ++ escapeString ++ "\" | " ++
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
		baseDir = Platform.userExtensionDir +/+ "FLSC" +/+ "extras" +/+ "examples";
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

	readFile {|fileName = "test.flsc"|
		var file = File(baseDir +/+ fileName, "r");
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

	getFileList {|subDir (".")|
		var list = ("find" + baseDir +/+ subDir +
			"-name *.flsc | sort |" +
			"sed -e 's/^" ++ baseDir.escapeChar($/) ++
			"\\/\\(.*\\)/\\1/'").unixCmdGetStdOut.split($\n);
		^list.[..list.size-2];
	}

	performDir {|selector, subDir = "tests", args = #[], addFileName = false|
		var list = this.getFileList(subDir).postln;
		var rec = {|list|
			var next = list.first;
			var rest = list[1..];
			"Executing: %".format(next.split.last).postln;
			this.readFile(next);
			this.inputString.postln;
			this.performList(selector, (if(addFileName)
				{
					var fileName = Platform.userExtensionDir +/+ "FLSC" +/+
					"recordings" +/+ next;
					fileName.dirname.mkdir;
					[fileName] ++ args;
				}
				{args}) ++
				[{
				if(rest.notEmpty) {rec.value(rest)} {"Done.".postln};
				}]
			)
		};

		rec.value(list);
	}

	playDir {|subDir = "tests"|
		this.performDir(\play, subDir, []);
	}

	recordDir{|subDir = "catalog", before = 1, after = 1,
		headerFormat = "WAV", sampleRate = 44100, sampleFormat = "int16",
		numChannels = 2|
		this.performDir(\recordNRT, subDir, [before, after,
		headerFormat, sampleRate, sampleFormat,
		numChannels], true);
	}

	asFLSC {
		if(semanticTree.notNil)
		{^semanticTree.asFLSC}
		{^nil};
	}
}
