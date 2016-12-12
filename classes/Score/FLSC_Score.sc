FLSC_Score {
	// le FLSC_Bus de sortie
	var <outBus;
	// le dictionnaire [SynthDef.name: SynthDef] des définitions employées
	var <defDict;
	// la liste des FLSC_Bus employés dans ce sous-graphe
	var <busList;
	// la liste des FLSC_MsgPair contenus dans ce support temporel
	var <bundle;
	// la liste des FLSC_Bundle des sous supports temporels
	var <bundleList;
	// les dates de début et de fin
	var <start, <end;
	// le rang du sous-graphe
	var <rank;

	*new {|out, defs, busses, msgs, bundles, t0, tf, rank|
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

	play {
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
		startTimes = busList.shallowCopy.sort {|a,b| a.start < b.start};
		endTimes = busList.shallowCopy.sort {|a,b| a.end < b.end};
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
		// l'ordre des bundles est signifiant, il faut l'inverser
		// (puisque les messages sont du plus profond au plus proche de la racine)
		// et concaténer les listes de messages de même date
		bundleList.reverse.do
		{|item|
			var scorePair = item.asSCScorePair(server, groups);
			scorePair.do {|item|
				// de même les listes de messages doivent être inversées
				var key = item[0], value = item[1].reverse;
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
			(score.endTime + 1).wait;
			// supprimer les SynthDef
			defDict.do {|item| SynthDef.removeAt(item.name)};
			// supprimer les Bus
			busses.do {|list| list.do {|bus| bus.free}};
			// quitter
			// server.quit;
		}).play;
		^this;
	}

}
