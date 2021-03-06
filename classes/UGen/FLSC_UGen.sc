FLSC_UGen : FLSC_SubsUGen {
	// conversion rate <-> entier
	classvar rateToInt, intToRate;
	// la Function qui produit le UGen associé à partir des UGen des sous-graphes
	var uGenFunc;

	*initClass {
		rateToInt = Dictionary.newFrom([audio: 0, control:1, scalar: 2]);
		intToRate = ['audio', 'control', 'scalar'];
	}

	*new {|subs, func|
		^super.new(subs).uGenInit(func);
	}

	*newFromClass {|subs, class, argNames, defaults = #[], minRate = 'control'|
		var func = {|subs|
			var rate = subs.inject(rateToInt[minRate])
			{|acc, item|
				var rate = item.rate;
				if(rate.notNil)
				{min(acc,rateToInt[rate])}
				{acc}
			};
			var args = [argNames,subs].flop.inject(List())
			{|acc, item| acc.addAll(item) };
			args.addAll(defaults);
			switch(rate)
			{0} {class.performKeyValuePairs(\ar, args)}
			{1} {class.performKeyValuePairs(\kr, args)};
		};
		^this.new(subs,func);
	}

	uGenInit {|func|
		uGenFunc = func;
		^this;
	}

	// FLSC_UGen.value produit un uGenGraph qui peut être intégré dans une SynthDef
	value {|varDict|
		// on obtient les résultats de l'évaluation des sous-graphes
		var subs = subGraphs.collect(_.value(varDict));
		// on évalue la fonction avec ces résultats comme arguments
		^uGenFunc.value(subs);
	}
}
