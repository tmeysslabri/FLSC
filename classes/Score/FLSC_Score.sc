FLSC_Score : FLSC_Object {
	// le répertoire de stockage des SynthDefs
	classvar defsDir;

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

	*initClass {
		defsDir = Platform.userExtensionDir +/+ "FLSC" +/+ "synthdefs";
	}

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
		// on recalcule le début, la fin, et le rang
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

	checkTimes {
		case
		{end == inf}
		{FLSC_Error("Local specification in global time referential").throw}
		{start < end}  {^this}
		{start == end} {^FLSC_Score(nil, t0: start, tf: end)}
		{start > end}
		{FLSC_Error("Anachronism in Score: start(%) > end(%)".format(start, end)).throw}
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
		var scoreDict = Dictionary(), msgList = List(), score = Score();

		// le serveur
		var server = Server.default;

		// variables pour le comptage des Bus et Node
		var numAudioBusses = 0;
		var numControlBusses = 0;

		var numNodes = 0;
		var activeNodes = 0;

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
				busses[item.type].pop
				/*
				var bus = busses[item.type].pop;
				if (item.type == 'control')
				{
					scoreDict[item.start] = scoreDict[item.start] ++
					[[\c_set, bus, 0]];
				};
				bus;
				*/
			} {
				switch(item.type)
				{'audio'}
				{
					// retourner l'indice courant et l'incrémenter
					numAudioBusses <! (numAudioBusses = numAudioBusses + 1);
				}
				{'control'}
				{
					// retourner l'indice courant et l'incrémenter
					numControlBusses <! (numControlBusses = numControlBusses + 1);
				}
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
		scoreDict[start] = scoreDict[start] ++ groups.collect
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
				scoreDict[key] = scoreDict[key] ++ value;
			}
		};

		// ajouter les messages de terminaison des groupes, à la fin
		scoreDict[end] = scoreDict[end] ++ groups.collect {|item| item.freeMsg};

		scoreDict.keysValuesDo {|key, value|
			msgList.add([key, value])};
		msgList.sort({|a,b| a[0] < b[0]});
		msgList.do {|item|
			var time = item[0];
			var msgs = item[1];

			// comptage du maximum de noeuds actifs simultanément
			var add = 0, sub = 0;
			msgs.do {|m|
				switch (m[0])
				{9}   {add = add + 1}
				{63}  {add = add + 1}
				{21}  {add = add + 1}
				{11}  {sub = sub + 1}
			};
			activeNodes = activeNodes + add;
			if (activeNodes > numNodes) {numNodes = activeNodes};
			activeNodes = activeNodes - sub;

			while {msgs.notEmpty}
			{
				score.add([time] ++ msgs[..63]);
				msgs = msgs[64..];
			};
		};

		^[score, numAudioBusses, numControlBusses, numNodes]
	}

	play {|doneAction = nil|
		var scorePair = this.asScorePair;
		var score = scorePair[0];
		var numAudioBusses = scorePair[1];
		var numControlBusses = scorePair[2];
		var numNodes = scorePair[3];
		var busses;
		var server = Server.default;
		var restart = false;

		// exécution de la partition
		Routine({
			// vérifier que les ressources sont suffisantes
			// on ajoute le nombre de Bus système (16)
			if (server.options.numAudioBusChannels < (numAudioBusses + 16))
			{
			server.options.numAudioBusChannels = 2 ** log2(numAudioBusses).ceil;
			restart = true;
			};
			if (server.options.numControlBusChannels < numControlBusses)
			{
			server.options.numControlBusChannels = 2 ** log2(numControlBusses).ceil;
			restart = true;
			};
			if (server.options.maxNodes < numNodes)
			{
				server.options.maxNodes = 2 ** log2(numAudioBusses + numControlBusses).ceil;
				restart = true;
			};
			if (restart) { server.quit; server.sync; };
			// démarrer le serveur
			server.bootSync;
			// allouer les Bus
			busses = Dictionary.newFrom([
				audio: {Bus.audio} ! numAudioBusses,
				control: {Bus.control} ! numControlBusses
			]);
			busList.do {|it| it.bus = busses[it.type][it.bus] };
			// charger les SynthDef
			defDict.do {|item| item.add };
			server.sync;
			// jouer la partition
			score.play;
			// attendre la fin
			(score.endTime + 0.1).wait;
			// supprimer les SynthDef -> géré par cleanUp
			// defDict.do {|item| SynthDef.removeAt(item.name)};
			// supprimer les Bus
			busses.do (_.do(_.free)); //{|list| list.do {|bus| bus.free}};
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
		var numAudioBusses = scorePair[1];
		var numControlBusses = scorePair[2];
		var numNodes = scorePair[3];
		var busses;
		// var restart = false;
		var baseDir = Platform.userExtensionDir +/+ "FLSC" +/+ "recordings";
		var fileName = (if(outFile.notNil) {outFile}
			{baseDir +/+ "FLSC" ++ Date.getDate.stamp}).splitext[0] ++ "." ++ headerFormat;
		var options = ServerOptions.new.numOutputBusChannels_(numChannels);
		// vérifier que les ressources sont suffisantes
		// on ajoute le nombre de Bus système (16)
		if (options.numAudioBusChannels < (numAudioBusses + 16))
		{
			options.numAudioBusChannels = 2 ** log2(numAudioBusses + 16).ceil.postln;
			// restart = true;
		};
		if (options.numControlBusChannels < numControlBusses)
		{
			options.numControlBusChannels = 2 ** log2(numControlBusses).ceil;
			// restart = true;
		};
		if (options.maxNodes < numNodes)
		{
			options.maxNodes = 2 ** log2(numAudioBusses + numControlBusses).ceil;
			// restart = true;
		};
		// DEBUG
		"Control: %/%, Audio: %/%, Nodes: %/%".format(
			numControlBusses, options.numControlBusChannels,
			numAudioBusses, options.numAudioBusChannels,
			numNodes, options.maxNodes
		).postln;
		/*
		if (restart)
		{ Server.default.waitForBoot({Server.default.quit}) };
		*/
		// allouer les Bus
		busses = Dictionary.newFrom([
			audio: {Bus.audio} ! numAudioBusses,
			control: {Bus.control} ! numControlBusses
		]);
		busList.do {|it| it.bus = busses[it.type][it.bus] };
		// créer les répertoires, si ils n'existent pas
		// baseDir.mkdir;
		fileName.dirname.mkdir;
		// créér les définitions
		defDict.do {|item|
			if (File.exists(defsDir+/+item.name++".scsyndef").not)
			{item.writeDefFile(defsDir)}
		};
		score.recordNRT(fileName++".osc", fileName, nil,
			sampleRate,	headerFormat, sampleFormat,
			options, " > /home/tmeysson/SC/nrt.log",
			action:
			{
				File.delete(fileName++".osc");
				busses.do {|list| list.do {|bus| bus.free}};
				"Recording finished.".postln;
				doneAction.value;
			}
		);
		^this;
	}

	*setUp {
		defsDir.mkdir;
		"SC_SYNTHDEF_PATH".setenv(defsDir);
	}

	*cleanUp {
		"SC_SYNTHDEF_PATH".setenv(Platform.userAppSupportDir+/+"synthdefs");
		"rm -R '%'".format(defsDir).unixCmd;
	}
}
