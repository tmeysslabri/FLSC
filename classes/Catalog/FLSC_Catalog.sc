FLSC_Catalog {
	// la chaîne FLSC caractéristique du noeud
	var flscString;

	// champs pour le rendu (nil sauf à la racine du catalogue)
	// le répertoire du catalogue
	var baseDir;
	// la liste des paquets requis
	var packages;
	// la liste des paires [chemin, expression]
	var pairList;
	// l'interpréteur employé
	var interp;
	// la file de rendus en attente
	var renderPipe;
	// gestion des tâches
	var activeJobs;
	// chronométrage du rendu
	var startTime;
	var nbJobs;
	var status;

	*newFrom {|expr|
		case
		// terminal: symbole
		{expr.isString} {^FLSC_CatSymb(expr)}
		// terminal: nombre
		{expr.isNumber} {^FLSC_CatNum(expr)}
		// expression composite (appel de fonction)
		// [selecteur, [N listes de sous-expressions], ?[N préfixes]]
		{^FLSC_CatCall(expr)};
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
		^this.newFrom(content[1]).catalogFileInit(path.dirname, content[0]);
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

	build {|maxJobs = 1|
		// compiler les paires [chemin, expression]
		this.makePairList;
		// écrire le code source
		this.writeSrc(baseDir +/+ "src");
		// initialiser l'interpréteur
		interp = FLSC_Interpreter.new.baseDir_(baseDir);
		packages.do {|pkg| interp.loadPackage("pkgs" +/+ pkg)};
		// créer la file de rendu
		renderPipe = pairList.collectAs({|pair|
			{interp.read(pair[1]).subRecordNRT(baseDir +/+ "build" +/+ pair[0] ++ ".WAV",
				0.2, 0.2, doneAction: {this.jobEnded})}
		}, List);
		// lancer le rendu
		startTime = Date.getDate.rawSeconds.asInteger;
		activeJobs =  0;
		nbJobs = 0;
		status = 0;
		FLSC_Score.setUp;
		maxJobs.do {if (renderPipe.notEmpty) {
			var job = renderPipe.pop;
			nbJobs = nbJobs + 1;
			activeJobs = activeJobs + 1;
			while {job.().isKindOf(FLSC_Score).not} {status = status + 1};
			}
		};
	}

	jobEnded {
		if (renderPipe.notEmpty)
		{
			var job = renderPipe.pop;
			nbJobs = nbJobs + 1;
			while {job.().isKindOf(FLSC_Score).not} {status = status + 1};
		}
		{
			activeJobs = activeJobs - 1;
			if (activeJobs == 0) {
				var endTime = Date.getDate.rawSeconds.asInteger;
				"Rendering finished (% jobs took %s, % were rerun).".format(nbJobs,
					endTime - startTime, status).postln;
				FLSC_Score.cleanUp;
			}
		}
	}
}