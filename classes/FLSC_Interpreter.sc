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
			FLSC_Error("Cannot open file: %".format(path)).throw;
		}
		^this;
	}

	loadPackage {|fileName|
		this.readFile(fileName);
		this.evaluate;
		if(treeValue.isKindOf(FLSC_Context))
		{ curContext = treeValue.refContext_(curContext) }
		{ FLSC_Error("Not a valid package file: %".format(fileName)).throw }
		^this;
	}

	reset { curContext = library }

	processError {|error|
		if(error.isKindOf(FLSC_LocError))
		{
			inputString.split($\n)[error.start-1..error.end-1].do(_.postln);
			"[%-%]: %".format(error.start, error.end, error.what).postln;
		} {error.throw}
	}

	evaluate {
		try {
			if(semanticTree.isKindOf(FLSC_ErrNode))
			{treeValue = semanticTree.asFLSC}
			{treeValue = semanticTree.value(curContext, library, workingDir)};
		} {|error|
			this.processError(error)
		}
		^treeValue;
	}

	asFLSCScore {|before = 0, after = 0|
		if(treeValue.isNil) {this.evaluate};
		try {
			if(treeValue.isFLSCScoreSpec ||
				(treeValue.isArray && treeValue.isString.not))
			{scoreValue = treeValue.asFLSCScoreSpec(true).asFLSCScore(before, after)}
			{scoreValue = treeValue};
		} {|error|
			this.processError(error);
		}
		^scoreValue;
	}

	play {|before = 0, after = 0, doneAction = nil|
		if(scoreValue.isNil) {this.asFLSCScore(before, after)};
		if(scoreValue.isKindOf(FLSC_Score))
		{^scoreValue.play(doneAction)}
		{if(doneAction.notNil)
			{scoreValue.postln; doneAction.value}
			{^scoreValue}};
	}

	recordNRT {|outFile, before = 0, after = 0, headerFormat = "WAV", sampleRate = 44100,
		sampleFormat = "int16", numChannels = 2, doneAction = nil|
		if(scoreValue.isNil) {this.asFLSCScore(before, after)};
		if(scoreValue.isKindOf(FLSC_Score))
		{^scoreValue.recordNRT(outFile, headerFormat, sampleRate,
			sampleFormat, numChannels, doneAction)}
		{if(doneAction.notNil)
			{scoreValue.postln; doneAction.value}
			{^scoreValue}};
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

	performDir {|selector, subDir ("examples" +/+ "tutorial"), args = #[],
		recursive = false, recordDir = nil, interactive = false|
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
				[if(interactive)
					{{{this.getKey(
						{if(rest.notEmpty) {rec.value(rest)} {"Done.".postln}},
						{rec.value(list)},
						{"Exit.".postln}
					)}.defer}}
					{{if(rest.notEmpty) {rec.value(rest)} {"Done.".postln}}}
				]
			)
		};

		rec.value(list);
	}

	playDir {|subDir ("examples" +/+ "tutorial"), before = 0.5, after = 0.5,
		recursive = false, interactive = false|
		this.performDir(\play, subDir, [before, after], recursive, interactive: interactive);
	}

	recordDir{|subDir = ("examples" +/+ "catalog"), before = 1, after = 1,
		headerFormat = "WAV", sampleRate = 44100, sampleFormat = "int16",
		numChannels = 2, recursive = true,
		recordDir = (Platform.userExtensionDir +/+ "FLSC" +/+ "recordings")|
		this.performDir(\recordNRT, subDir, [before, after,
		headerFormat, sampleRate, sampleFormat,
		numChannels], recursive, recordDir, false);
	}

	getKey {|return, space, escape|
		var window = Window("User Interaction", Rect(500, 300, 200, 60));
		var view = View(window, Rect(0, 0, 200, 60));
		var text = StaticText(view, Rect(0, 0, 200, 60)).string =
		"Press\t<RET> to continue\n" +
		"\t<SPACE> to repeat\n" +
		"\t<ESC> to quit";
		view.keyDownAction = {|view, char, mod, uni, code, key|
			switch(key)
			{16777220} {return.value}
			{32}       {space.value}
			{16777216} {escape.value};
			window.close;
		};
		window.front;
		view.focus;
	}

	asFLSC {
		if(semanticTree.notNil)
		{^semanticTree.asFLSC}
		{^nil};
	}
}
