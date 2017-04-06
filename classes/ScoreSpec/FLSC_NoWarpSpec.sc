FLSC_NoWarpSpec : FLSC_ScoreSpec {
	// la définition d'un raccord
	classvar pipeAr, pipeKr;
	// la ScoreSpec à laquelle on se réfère
	var subSpec;

	*initClass {
		pipeAr = SynthDef('pipeAr', {|out, in| Out.ar(out, In.ar(in))});
		pipeKr = SynthDef('pipeKr', {|out, in| Out.kr(out, In.kr(in))});
	}

	*new {|spec|
		^super.new(spec.rate, spec.varList).noWarpSpecInit(spec);
	}

	noWarpSpecInit {|spec|
		subSpec = spec;
		^this;
	}

	scoreValue {|outBus, timeWarp, varDict, noWarpDict|
		// on va chercher la référence dans le noWarpDict
		var sub = noWarpDict[subSpec];
		// les dates de début et de fin doivent correspondre à l'appelant
		var start = timeWarp.value([0]);
		var end = timeWarp.value([{|t|t}]);
		// si outBus n'est pas nil, créer un raccord
		if(outBus.notNil)
		{
			var pipe = switch(subSpec.rate)
			{'audio'}   {pipeAr}
			{'control'} {pipeKr};
			^FLSC_Score(outBus, Dictionary.newFrom([pipe.name, pipe]), List(), List(),
				[FLSC_Bundle(start, end, [FLSC_MsgPair(pipe.name,
					Dictionary.newFrom(['in', sub.outBus, 'out', outBus]),
					sub.rank)])], start, end, sub.rank + 1).numNodes_(1).checkTimes;
		} {
			// toutes les informations ont déjà été ajoutées,
			// seul le bus de sortie nous intéresse
			^FLSC_Score(sub.outBus, Dictionary(), List(), List(), List(),
				start, end, sub.rank);
		};
	}

	encapsulate { ^this; }
}
