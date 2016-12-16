FLSC_GlobalScoreSpec : FLSC_ScoreSpec {
		makeOut {|outBus, timeWarp|
		// le Bus de sortie est celui demandé, ou un nouveau Bus en son absence
		// dans le deuxième cas on doit le créér, puis calculer le début et la fin
		// suivant le résultat des sous-spécifications
		if(outBus.notNil) {^outBus}
		{
			var bus = FLSC_Bus(rate, nil, nil);
			score.busList.add(bus);
			^bus;
		}
	}
}
