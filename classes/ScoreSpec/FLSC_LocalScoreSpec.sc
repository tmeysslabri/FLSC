FLSC_LocalScoreSpec : FLSC_ScoreSpec {
	makeOut {|outBus, timeWarp|
		// le Bus de sortie est celui demandé, ou un nouveau Bus en son absence
		// dans le deuxième cas on doit le créér, et calculer le début et la fin
		// suivant le timeWarp local
		if(outBus.notNil) {^outBus}
		{
			var bus = FLSC_Bus(rate, timeWarp.value([0]), timeWarp.value([{|t|t}]));
			// if(bus.end == inf)
			// {FLSC_Error("Local specification in global time referential").throw};
			score.busList.add(bus);
			^bus;
		}
	}
}
