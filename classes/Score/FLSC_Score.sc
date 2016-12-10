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

	*new {|out, defs, busses, msgs, bundles, t0, tf|
		^super.new.scoreInit(out, defs, busses, msgs, bundles, t0, tf);
	}

	scoreInit {|out, defs, busses, msgs, bundles, t0, tf|
		outBus = out;
		defDict = defs;
		busList = busses;
		bundle = msgs;
		bundleList = bundles;
		start = t0;
		end = tf;
		^this;
	}

	play {
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
		startTimes = busList.sort {|a,b| a.start < b.start};
		endTimes = busList.sort {|a,b| a.end < b.end};
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
		bundleList.do
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
			defDict.do {|item| item.add };
			server.sync;
			score.play;
		}).play;
		^this;
	}

}