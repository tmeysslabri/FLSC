FLSC_Score {
	// le FLSC_Bus de sortie
	var <>outBus;
	// le dictionnaire [SynthDef.name: SynthDef] des définitions employées
	var <>defDict;
	// la liste des FLSC_Bus employés dans ce sous-graphe
	var <>busList;
	// la liste des FLSC_MsgPair contenus dans ce support temporel
	var <>bundle;
	// la liste des FLSC_Bundle des sous supports temporels
	var <>bundleList;
	// les dates de début et de fin
	var <>start, <>end;
	// le rang du sous-graphe
	var <>rank;

	*new {|out, defs (Dictionary()), busses (List()), msgs (List()), bundles (List()),
		t0 = inf, tf = 0, rank = 0|
		^super.new.scoreInit(out, defs, busses, msgs, bundles, t0, tf, rank);
	}

	scoreInit {|out, defs, busses, msgs, bundles, t0, tf, rankNum|
		outBus = out;
		defDict = defs;
		busList = busses;
		bundle = msgs;
		bundleList = bundles;
		start = t0;
		end = tf;
		rank = rankNum;
		^this;
	}

	add {|subScore|
		// on ajoute les bus, les définitions, les messages, les bundle
		busList.addAll(subScore.busList);
		defDict.putAll(subScore.defDict);
		bundle.addAll(subScore.bundle);
		bundleList.addAll(subScore.bundleList);
		// on recalcule le début, la fin et le rang
		start = min(start, subScore.start);
		end = max(end, subScore.end);
		rank = max(rank, subScore.rank);
	}

	pushBundle {
		if(bundle.notEmpty) {
			bundleList.add(FLSC_Bundle(start, end, bundle));
			bundle = List();
		};
	}

	asScorePair {
		// listes permettant l'allocation de Bus
		var startTimes, endTimes;
		// réserves de Bus pour l'allocation
		var busses = Dictionary.newFrom(['audio', List(), 'control', List()]);
		// indice d'itération sur endTimes
		var endIndex = 0;

		// les groupes utilisés pour le séquencement
		var groups = Array.fill(rank) {ParGroup.basicNew};
		var curGroup = nil;

		// Score résultante
		var scoreDict = Dictionary(), score = Score();

		// le serveur
		var server = Server.default;

		// allocation des Bus
		startTimes = busList.copy.sort {|a,b| a.start < b.start};
		endTimes = busList.copy.sort {|a,b| a.end < b.end};
		startTimes.do {|item|
			while({item.start >= endTimes[endIndex].end})
			{
				var endBus = endTimes[endIndex];
				busses[endBus.type].add(endBus.bus);
				endIndex = endIndex + 1;
			};
			item.bus = if(busses[item.type].notEmpty)
			{
				busses[item.type].pop;
			} {
				switch(item.type)
				{'audio'}   {Bus.audio}
				{'control'} {Bus.control}
			};
		};

		while({endIndex < endTimes.size})
		{
			var endBus = endTimes[endIndex];
			busses[endBus.type].add(endBus.bus);
			endIndex = endIndex + 1;
		};

		// création du Score

		// ajouter les messages de création des groupes, au début
		scoreDict[start] = groups.collect
		{|item|
			var msg = item.newMsg(curGroup, 'addAfter');
			curGroup = item;
			msg;
		};
		// l'ordre des bundles n'est pas signifiant
		// (puisque les messages sont classés dans les groupes par leur rang)
		// il faut tout de même concaténer les listes de messages de date start et end
		// de façon à ce que les groupes soient créés en premier et détruits en dernier
		// (de plus cela économise des éléments de Score, lorsque les dates sont les mêmes)
		bundleList.do
		{|item|
			var scorePair = item.asSCScorePair(server, groups);
			scorePair.do {|item|
				var key = item[0], value = item[1];
				// "%: %".format(key, value).postln;
				if(scoreDict[key].notNil)
				{ scoreDict[key] = scoreDict[key] ++ value }
				{ scoreDict[key] = value };
			}
		};

		// ajouter les messages de terminaison des groupes, à la fin
		scoreDict[end] = groups.collect {|item| item.freeMsg};

		scoreDict.keysValuesDo {|key, value|
			// "%: %".format(key, value).postln;
			score.add([key] ++ value)};
		score.sort;

		^[score, busses];
	}

	play {|doneAction = nil|
		var scorePair = this.asScorePair;
		var score = scorePair[0];
		var busses = scorePair[1];
		var server = Server.default;

		// exécution de la partition
		Routine({
			// redémarrer le serveur pour oublier les anciennes SynthDef
			server.bootSync;
			// charger les SynthDef
			defDict.do {|item| item.add };
			server.sync;
			// jouer la partition
			score.play;
			// attendre la fin
			(score.endTime + 0.1).wait;
			// supprimer les SynthDef
			defDict.do {|item| SynthDef.removeAt(item.name)};
			// supprimer les Bus
			busses.do {|list| list.do {|bus| bus.free}};
			// effectuer l'action demandée
			doneAction.value;
		}).play;
		^this;
	}

	recordNRT {|outFile, headerFormat = "WAV", sampleRate = 44100,
		sampleFormat = "int16", numChannels = 2, doneAction = nil|
		// récupérer la partition
		var scorePair = this.asScorePair;
		var score = scorePair[0];
		var busses = scorePair[1];
		var baseDir = Platform.userExtensionDir +/+ "FLSC/recordings";
		var fileName = if(outFile.notNil) {outFile}
		{baseDir +/+ "FLSC" ++ Date.getDate.stamp ++ "." ++ headerFormat};
		// créér les définitions
		defDict.do {|item| item.writeDefFile };
		score.recordNRT(baseDir +/+ "FLSC-osc", fileName, nil,
			sampleRate,	headerFormat, sampleFormat,
			ServerOptions.new.numOutputBusChannels_(numChannels),
			action:
			{
				defDict.do {|item| File.delete(Platform.userAppSupportDir +/+
					"synthdefs" +/+ item.name ++ ".scsyndef")};
				busses.do {|list| list.do {|bus| bus.free}};
				"Recording finished.".postln;
				doneAction.value;
			}
		);
		^this;
	}
}
