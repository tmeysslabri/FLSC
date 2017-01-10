FLSC_Interpreter {
	// la bibliothèque: un FLSC_Context
	var library;
	// le contexte d'évaluation courant
	var curContext;
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
	// le répertoire dans lequel se trouve le fichier courant
	var workingDir;

	*getTree {|string|
		var escapeString = string.escapeChar($\\).escapeChar($").escapeChar($$).escapeChar($`);
		var cmd = ("echo \"" ++ escapeString ++ "\" | " ++
		Platform.userExtensionDir +/+ "FLSC" +/+ "extras" +/+ "FLSC2SC" +/+
			"build" +/+ "flsc2sc");
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
		curContext = library;
		baseDir = Platform.userExtensionDir +/+ "FLSC" +/+ "extras";
		workingDir = baseDir;
		^this;
	}

	read {|string|
		inputString = string;
		semanticTree = this.class.getTree(string);
		// réinitialiser le répertoire de travail,
		// dans le cas où on lit une chaîne directement
		workingDir = baseDir;
		// réinitialiser les valeurs éventuellement calculées
		treeValue = nil;
		scoreValue = nil;
		^this;
	}

	readFile {|fileName ("examples" +/+ "test.flsc")|
		var path = if(fileName[0] == $/)
		{ fileName }
		{ baseDir +/+ fileName };
		var file = File(path, "r");
		if(file.isOpen) {
			this.read(file.readAllString);
			file.close;
			workingDir = path.dirname;
		} {
			Error("Cannot open file: %".format(path)).throw;
		}
		^this;
	}

	evaluate {
		if(semanticTree.isKindOf(FLSC_Error))
		{^treeValue = semanticTree.asFLSC}
		{^treeValue = semanticTree.value(curContext, library, workingDir)};
	}

	loadPackage {|fileName|
		this.readFile(fileName);
		this.evaluate;
		if(treeValue.isKindOf(FLSC_Context))
		{ curContext = treeValue.refContext_(curContext) }
		{ Error("Not a valid package file: %".format(fileName)).throw }
		^this;
	}

	reset { curContext = library }

	asFLSCScore {|before = 0, after = 0|
		if(treeValue.isNil) {this.evaluate};
		if(treeValue.isFLSCScoreSpec ||
			(treeValue.isArray && treeValue.isString.not))
		{^scoreValue = treeValue.asFLSCScoreSpec(true).asFLSCScore(before, after)}
		{^scoreValue = treeValue};
	}

	play {|before = 0, after = 0, doneAction = nil|
		if(scoreValue.isNil) {this.asFLSCScore(before, after)};
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

	getFileList {|subDir ("."), recursive = false|
		var cmd = if(recursive)
		{
			"find" + baseDir +/+ subDir + "-name *.flsc | sort |" +
			"sed -e 's/^" ++ baseDir.escapeChar($/) ++ "\\/\\(.*\\)/\\1/'"
		} {
			"ls" + baseDir +/+ subDir +/+ "*.flsc |" +
			"sed -e 's/^" ++ baseDir.escapeChar($/) ++ "\\/\\(.*\\)/\\1/'"
		};
		var list = cmd.unixCmdGetStdOut.split($\n);
		^list.[..list.size-2];
	}

	performDir {|selector, subDir ("examples" +/+ "tests"), args = #[],
		recursive = false, recordDir = nil|
		var list = this.getFileList(subDir, recursive);
		var rec = {|list|
			var next = list.first;
			var rest = list[1..];
			"Executing: %".format(next.split.last).postln;
			this.readFile(next);
			this.inputString.postln;
			this.performList(selector, (if(recordDir.notNil)
				{
					var fileName = recordDir +/+ next;
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

	playDir {|subDir ("examples" +/+ "tests"), before = 0.5, after = 0.5, recursive = false|
		this.performDir(\play, subDir, [before, after], recursive);
	}

	recordDir{|subDir = ("examples" +/+ "catalog"), before = 1, after = 1,
		headerFormat = "WAV", sampleRate = 44100, sampleFormat = "int16",
		numChannels = 2, recursive = true,
		recordDir = (Platform.userExtensionDir +/+ "FLSC" +/+ "recordings")|
		this.performDir(\recordNRT, subDir, [before, after,
		headerFormat, sampleRate, sampleFormat,
		numChannels], recursive, recordDir);
	}

	asFLSC {
		if(semanticTree.notNil)
		{^semanticTree.asFLSC}
		{^nil};
	}
}
