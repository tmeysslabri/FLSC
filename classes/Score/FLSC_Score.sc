FLSC_Score : FLSC_Object {
	// le répertoire de stockage des SynthDefs
	classvar defsDir;
	// les Bus réservés (audio et contrôle)
	classvar resvBusses;
	// le verrou de synchronisation
	classvar lock;
	// le serveur employé
	classvar program;

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
		resvBusses = Dictionary.newFrom([audio: List(), control: List()]);
		lock = Semaphore(1);
		program = \scsynth;
		// PRODUIT DES ERREURS:
		// program = \supernova;
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

	asScorePair {|realtime = false|
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
		var options;

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

		// comptage du maximum de noeuds actifs simultanément
		msgList.do {|item|
			var time = item[0];
			var msgs = item[1];
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
		};

		// options du serveur
		options = server.options.copy;

		// vérifier que les ressources sont suffisantes
		if (options.maxNodes < numNodes)
		{
			var num = 2 ** log2(numNodes).ceil;
			options.maxNodes = num;
			options.memSize = num * 8;
		};
		// on ajoute le nombre de Bus audio système
		if (options.numAudioBusChannels < (numAudioBusses +
			options.numOutputBusChannels + options.numInputBusChannels))
		{
			options.numAudioBusChannels = 2 ** log2(numAudioBusses +
				options.numOutputBusChannels + options.numInputBusChannels).ceil;
		};
		if (options.numControlBusChannels < numControlBusses)
		{
			options.numControlBusChannels = 2 ** log2(numControlBusses).ceil;
		};
		// démarrer le serveur si nous sommes en temps réel
		if (realtime) {
			// modifier les options
			server.options = options;
			// démarrer et attendre d'avoir fini
			server.bootSync;
			// allouer les Bus supplémentaires nécessaires
			({Bus.audio} ! (numAudioBusses - resvBusses['audio'].size)).do
			{|bus| resvBusses['audio'].add(bus)};
			({Bus.control} ! (numControlBusses - resvBusses['control'].size)).do
			{|bus| resvBusses['control'].add(bus)};
			busList.do {|it| it.bus = resvBusses[it.type][it.bus] };
		}
		// version hors temps réel
		{
			// allouer les Bus supplémentaires nécessaires
			var allocators = Dictionary.newFrom([
				audio:   ContiguousBlockAllocator(options.numAudioBusChannels,
					options.numOutputBusChannels + options.numInputBusChannels),
				control: ContiguousBlockAllocator(options.numControlBusChannels)
			]);
			var addresses;
			{allocators['audio'].alloc} ! numAudioBusses;
			{allocators['control'].alloc} ! numControlBusses;
			addresses = allocators.collect {|alc|
				alc.blocks.collect(_.address);
			};
			busList.do {|it|
				it.bus = Bus.new(it.type, addresses[it.type][it.bus], 1);
			};
		};

		// remplacer les FLSC_Bus par leurs indices
		msgList.do {|item|
			var time = item[0];
			var msgs = item[1].collect {|m| m.collect {|e|
				if (e.isKindOf(FLSC_Bus))
				{e.value.index}
				{e}
			} };

			// découper les messages en blocs pour éviter les erreurs
			while {msgs.notEmpty}
			{
				score.add([time] ++ msgs[..63]);
				msgs = msgs[64..];
			};
		};

		// Donner des informations sur les ressources utilisées
		/*
		"Control: %/%, Audio: %/%, Nodes: %/%".format(
			numControlBusses, options.numControlBusChannels,
			numAudioBusses, options.numAudioBusChannels,
			numNodes, options.maxNodes
		).postln;
		*/

		^[score, options]
	}

	play {|doneAction = nil|
		// exécution de la partition
		Routine({
			// section vérouillée
			// eviter plusieurs exécutions RT parallèles
			protect {
				var scorePair = this.asScorePair(true);
				var score = scorePair[0];
				var options = scorePair[1];
				var server = Server.default;
				var restart = false;

				lock.wait;

				// charger les SynthDef
				defDict.do {|item| item.add };
				server.sync;
				// jouer la partition
				score.play;
				// attendre la fin
				(score.endTime + 0.1).wait;
				// effectuer l'action demandée
				doneAction.value;
				// quitter le serveur
				server.quit;

				// déverouiller
			} {lock.signal};
		}).play;
		^this;
	}

	recordNRT {|outFile, headerFormat = "WAV", sampleRate = 44100,
		sampleFormat = "int16", numChannels = 2, doneAction = nil|
		// résultat de l'exécution
		var result;
		// récupérer la partition
		var scorePair = this.asScorePair(false);
		var score = scorePair[0];
		var options = scorePair[1];
		var baseDir = Platform.userExtensionDir +/+ "FLSC" +/+ "recordings";
		var fileName = (if(outFile.notNil) {outFile}
			{baseDir +/+ "FLSC" ++ Date.getDate.stamp}).splitext[0] ++ "." ++ headerFormat;

		// créer les répertoires, si ils n'existent pas
		// baseDir.mkdir;
		fileName.dirname.mkdir;
		// créér les définitions
		defDict.do {|item|
			if (File.exists(defsDir+/+item.name++".scsyndef").not)
			{item.writeDefFile(defsDir)}
		};

		score.write(fileName++".osc");

		result = ("if % -N % _ % % % %"
			.format(program, fileName++".osc", fileName,
				sampleRate, headerFormat, sampleFormat)
			+ "-o % -a % -c % -m % -n %"
			.format(numChannels,
				options.numAudioBusChannels, options.numControlBusChannels,
				options.memSize, options.maxNodes) +
			"; then echo SUCCESS; else echo FAILURE; fi")
		.unixCmdGetStdOutLines.select {|line|
			[
				"ERROR", "Exception in World_New", "FAILURE IN SERVER",
				"Couldn't open non real time command file.",
				"FAILURE"
			].inject(false)
			{|a,i| a || i.matchRegexp(line)};
		}.size;

		if (result == 0)
		{"Success: %".format(fileName).postln}
		{"Failure: %".format(fileName).postln};

		File.delete(fileName++".osc");
		doneAction.(result);

		^this;
	}

	*setUp {
		Server.perform(program);
		defsDir.mkdir;
		"SC_SYNTHDEF_PATH".setenv(defsDir);
	}

	*cleanUp {
		resvBusses.do(_.do(_.free));
		resvBusses = Dictionary.newFrom([audio: List(), control: List()]);
		"SC_SYNTHDEF_PATH".setenv(Platform.userAppSupportDir+/+"synthdefs");
		"rm -R '%'".format(defsDir).unixCmd;
	}
}
