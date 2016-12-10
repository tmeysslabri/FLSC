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
		// la ScoreSpec associée
		var spec = treeValue.asFLSCScoreSpec;
		// distorsion temporelle par défaut
		var timeWarp = {|t| t};
		// définitions, bus, messages, bundle accumulés
		var defs = Dictionary();
		var busses = List();
		var msgs = List();
		var bundles = List();
		// et varDict pour l'interprétation des FLSC_Score
		var varDict = Dictionary();

		// évaluer la varList
		// (il est possible que des variables ne soient pas encore évaluées)
		spec.varList.do {|item|
			var value = item.value(0, timeWarp, varDict);
			defs.putAll(value.defDict);
			busses.addAll(value.busList);
			msgs.addAll(value.bundle);
			bundles.addAll(value.bundleList);
			varDict.put(item, value);
		};
		scoreValue = spec.value(0, timeWarp, varDict);
		bundles.addAll(scoreValue.bundleList);
		if(scoreValue.bundle.notEmpty) {
			Error("DEBUG: non-empty bundle in top-level FLSC_Score: %".format(
				scoreValue.bundle)).throw;
			// jamais atteint, supprimer
			bundles.add(FLSC_Bundle(
			timeWarp.value(0), timeWarp.value('end'), msgs.addAll(scoreValue.bundle)))
		};
		scoreValue = FLSC_Score(scoreValue.outBus, defs.putAll(scoreValue.defDict),
			busses.addAll(scoreValue.busList), List(), bundles,
			scoreValue.start, scoreValue.end);
		^this;
	}

	play {
		// scoreValue est une FLSC_Score

		// listes permettant l'allocation de Bus
		var startTimes, endTimes;
		// réserves de Bus pour l'allocation
		var busses = Dictionary.newFrom(['audio', List(), 'control', List()]);
		// indice d'itération sur endTimes
		var endIndex = 0;

		// Score résultante
		var scoreDict = Dictionary(), score = Score();

		// le serveur
		var server = Server.default;

		// allocation des Bus
		startTimes = scoreValue.busList.sort {|a,b| a.start < b.start};
		endTimes = scoreValue.busList.sort {|a,b| a.end < b.end};
		startTimes.do {|item|
			while({item.start > endTimes[endIndex].end})
			{
				var endBus = endTimes[endIndex];
				busses[endBus.type].add(endBus.bus);
				endIndex = endIndex + 1;
			};
			item.bus = if(busses[item.type].notEmpty)
			{
				var busList = busses[item.type];
				busList.take(busList.first);
			} {
				switch(item.type)
				{'audio'}   {Bus.audio}
				{'control'} {Bus.control}
			}
		};

		// création du Score
		// l'ordre des bundles est signifiant, le conserver
		// en concaténant les listes de messages de même date
		scoreValue.bundleList.do
		{|item|
			var scorePair = item.asSCScorePair(server);
			scorePair.do {|item|
				var key = item[0], value = item[1];
				// "%: %".format(key, value).postln;
				if(scoreDict[key].notNil)
				{ scoreDict[key] = scoreDict[key] ++ value }
				{ scoreDict[key] = value };
			}
		};
		scoreDict.keysValuesDo {|key, value|
			// "%: %".format(key, value).postln;
			score.add([key] ++ value)};
		score.sort;

		// exécution de la partition
		Routine({
			server.bootSync;
			scoreValue.defDict.do {|item| item.add };
			server.sync;
			score.play;
		}).play;
		^this;
	}

	asFLSC {
		^semanticTree.asFLSC;
	}
}

	