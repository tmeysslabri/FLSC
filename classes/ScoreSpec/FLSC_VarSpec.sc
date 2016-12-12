FLSC_VarSpec : FLSC_ScoreSpec {
	// la définition d'un raccord
	classvar pipeAr, pipeKr;
	// la ScoreSpec à laquelle on se réfère
	var subSpec;

	*initClass {
		pipeAr = SynthDef('pipeAr', {|out, in| Out.ar(out, In.ar(in))});
		pipeKr = SynthDef('pipeKr', {|out, in| Out.kr(out, In.kr(in))});
	}

	*new {|spec|
		^super.new(spec.rate, spec.varList).listSpecInit(spec);
	}

	listSpecInit {|spec|
		subSpec = spec;
		varList.add(subSpec);
		^this;
	}

	value {|outBus, timeWarp, varDict|
		// on va chercher la référence dans le varDict
		var sub = varDict[subSpec];
		// si outBus n'est pas nil, créer un raccord
		if(outBus.notNil)
		{
			var pipe = switch(subSpec.rate)
			{'audio'}   {pipeAr}
			{'control'} {pipeKr};
			^FLSC_Score(outBus, Dictionary.newFrom([pipe.name, pipe]), List(),
				List.newFrom([FLSC_MsgPair(pipe.name,
					Dictionary.newFrom(['in', sub.outBus, 'out', outBus]),
					sub.rank)]), List(), sub.start, sub.end, sub.rank + 1
			);
		} {
			// toutes les informations ont déjà été ajoutées,
			// seul le bus de sortie nous intéresse
			^FLSC_Score(sub.outBus, Dictionary(), List(), List(), List(),
				sub.start, sub.end, sub.rank);
		};
	}

	encapsulate { ^this; }
}
