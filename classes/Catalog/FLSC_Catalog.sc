FLSC_Catalog {
	// la chaîne FLSC caractéristique du noeud
	var flscString;

	// champs pour le rendu (nil sauf à la racine du catalogue)
	// la liste des sous-expressions
	var exprList;
	// le répertoire du catalogue
	var baseDir;
	// la liste des paquets requis
	var packages;
	// la liste des paires [chemin, expression]
	var pairList;
	// les interpréteurs employés
	var interps;
	// la file de rendus en attente
	var renderPipe;
	// gestion des tâches
	var activeJobs;
	// chronométrage du rendu
	var startTime;
	var totalJobs;
	var nbJobs;
	var status;
	// fonction d'arrêt prématuré
	var abortFunc;

	*newFrom {|expr|
		case
		// terminal: symbole
		{expr.isString} {^FLSC_CatSymb(expr)}
		// terminal: nombre
		{expr.isNumber} {^FLSC_CatNum(expr)}
		// expression composite (appel de fonction)
		// [selecteur, [N listes de sous-expressions], ?[N préfixes]]
		{expr[0].isKindOf(Symbol)} {^FLSC_CatCall(expr)}
		// tableau: [elt, ...]
		{^FLSC_CatArr(expr)};
	}

	*readFile {|path|
		var file = File.open(path, "r");
		var content = file.readAllString.interpret;
		^content;
	}

	*newFromFile {|path|
		// lire le fichier
		var content = this.readFile(path);
		// créer la structure de données
		// ^this.newFrom(content[1]).catalogFileInit(path.dirname, content[0]);
		^super.new.exprListInit(content[1]).catalogFileInit(path.dirname, content[0]);
	}

	exprListInit {|list|
			exprList = list.collect {|e| this.class.newFrom(e)};
	}

	catalogFileInit {|dir, pkgs|
		baseDir = dir;
		packages = pkgs;
	}

	writeSrc {|srcDir = "src"|
		pairList.do
		{|it|
			var path = if(srcDir.notNil)
			{srcDir +/+ it[0] ++ ".flsc"}
			{it[0] ++ ".flsc"};
			var dir = path.dirname;
			var file;
			var old = "";
			// vérifier si il existe un fichier identique
			if (File.exists(path)) {
				file = File.open(path, "r");
				old = file.readAllString;
				file.close;
			};
			// si le fichier est inexistant ou différent, l'écrire
			if (old != it[1])
			{
				File.mkdir(dir);
				file = File.open(path, "w");
				file.putString(it[1]);
				file.close;
			}
		}
	}

	makePairList {
		pairList = this.asPathExprPairList;
	}

	asPathExprPairList {
		var padding = exprList.size.asString.size;
		^exprList.collect {|it, n| it.asPathExprPairList(n.asString.padLeft(padding, "0"))}
		/*
		^exprList.collect {|it, n| it.asPathExprPairList
			.collect{|e| [n.asString.padLeft(padding, "0") ++ "-" ++  e[0], e[1]]}}
		*/
		.reduce('++');
	}

	build {|maxJobs = 1, rewrite = false|
		var pkgDirMTime = File.mtime(baseDir +/+ "pkgs");
		// compiler les paires [chemin, expression]
		this.makePairList;
		// écrire le code source
		this.writeSrc(baseDir +/+ "src");
		// initialiser l'interpréteur
		interps = FLSC_Interpreter.new ! maxJobs;
		// if (baseDir.notNil) {interp.baseDir = baseDir};
		interps.do {|it|
			it.baseDir = baseDir;
			packages.do {|pkg| it.loadPackage("pkgs" +/+ pkg)};
		};

		// créer la file de rendu
		renderPipe = pairList.collectAs({|pair|
			var outFile = baseDir +/+ "build" +/+ pair[0] ++ ".WAV";
			var srcFile = baseDir +/+ "src" +/+ pair[0] ++ ".flsc";
			if ((File.exists(outFile).not) or:
				{File.mtime(outFile) < File.mtime(srcFile)} or:
				{rewrite && (File.mtime(outFile) <  pkgDirMTime)})
			{
				{|interp|
					interp.read(pair[1]).subRecordNRT(outFile,
						0.2, 0.2, doneAction: {this.jobEnded(interp, outFile, thisFunction)})
				}
			}
			{nil};
		}, List).select(_.notNil);
		// lancer le rendu
		CmdPeriod.add(abortFunc = {"Aborting.".postln; renderPipe = List();};);
		startTime = Date.getDate.rawSeconds.asInteger;
		activeJobs =  0;
		totalJobs = renderPipe.size;
		nbJobs = 0;
		status = 0;
		FLSC_Score.setUp;
		interps.do {|interp|
			if (renderPipe.notEmpty)
			{
				var job = renderPipe.pop;
				nbJobs = nbJobs + 1;
				"Starting job %/%".format(nbJobs, totalJobs).postln;
				activeJobs = activeJobs + 1;
				job.(interp);
			}
			{
				if (activeJobs == 0) {
					var endTime = Date.getDate.rawSeconds.asInteger;
					CmdPeriod.remove(abortFunc);
					{"Rendering finished (% jobs took %s, % were rerun).".format(nbJobs,
						endTime - startTime, status).postln}.defer(1);
					FLSC_Score.cleanUp;
					activeJobs = -1;
				}
			}
		};
	}

	jobEnded {|interp, outFile, jobFunc|
		if (File.exists(outFile).not)
		{
			"Rerunning %".format(outFile).postln;
			status = status + 1;
			jobFunc.(interp);
		}
		{
			if (renderPipe.notEmpty)
			{
				var job = renderPipe.pop;
				nbJobs = nbJobs + 1;
				"Starting job %/%".format(nbJobs, totalJobs).postln;
				job.(interp);
			}
			{
				activeJobs = activeJobs - 1;
				if (activeJobs == 0) {
					var endTime = Date.getDate.rawSeconds.asInteger;
					CmdPeriod.remove(abortFunc);
					{"Rendering finished (% jobs took %s, % were rerun).".format(nbJobs,
						endTime - startTime, status).postln}.defer(1);
					FLSC_Score.cleanUp;
				}
			}
		}
	}
}
