{
	[
		// CONSTANTES
		['false', false],
		['true', true],
		['fl', false],
		['tr', true],
		['inf', inf],
		// FONCTIONS NUMERIQUES:
		// generateur aléatoire
		// attention, si l'argument est un entier, random produira des entiers
		['random', FLSC_Function(nil, ['max'],
			{|ctxt|
				var max = FLSC_Var('max').value(ctxt);
				if(max.isFLSCNil) {max = 1.0};
				max.rand;})],
		// opérateurs unaires:
		['neg', FLSC_UGenFunc(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).neg})],
		['abs', FLSC_UGenFunc(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).abs})],
		['sgn', FLSC_UGenFunc(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).sign})],
		// opérateurs arithmétiques:
		['+', FLSC_RestUGenFunc(nil, ['terms'],
			{|ctxt| FLSC_Var('terms').value(ctxt).reduce('+')})],
		['*', FLSC_RestUGenFunc(nil, ['terms'],
			{|ctxt| FLSC_Var('terms').value(ctxt).reduce('*')})],
		['-', FLSC_RestUGenFunc(nil, ['base', 'terms'],
			{|ctxt| FLSC_Var('terms').value(ctxt).inject(FLSC_Var('base').value(ctxt), _-_)})],
		['/', FLSC_RestUGenFunc(nil, ['base', 'terms'],
			{|ctxt| FLSC_Var('terms').value(ctxt).inject(FLSC_Var('base').value(ctxt), _/_)})],
		['**', FLSC_UGenFunc(nil, ['a', 'b'],
			{|ctxt| FLSC_Var('a').value(ctxt) ** FLSC_Var('b').value(ctxt)})],
		['mod', FLSC_UGenFunc(nil, ['a', 'b'],
			{|ctxt| FLSC_Var('a').value(ctxt) % FLSC_Var('b').value(ctxt)})],
		// fonctions usuelles:
		['sin', FLSC_UGenFunc(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).sin})],
		['cos', FLSC_UGenFunc(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).cos})],
		['tan', FLSC_UGenFunc(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).tan})],
		['log2', FLSC_UGenFunc(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).log2})],
		['floor', FLSC_UGenFunc(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).floor})],
		['ceil', FLSC_UGenFunc(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).ceil})],
		['round', FLSC_UGenFunc(nil, ['a', 'b'],
			{|ctxt|
				var step = FLSC_Var('b').value(ctxt);
				if (step.isFLSCNil) { step = 1 };
				FLSC_Var('a').value(ctxt).round(step)})],
		['min', FLSC_RestUGenFunc(nil, ['terms'],
			{|ctxt| FLSC_Var('terms').value(ctxt).reduce(\min)})],
		['max', FLSC_RestUGenFunc(nil, ['terms'],
			{|ctxt| FLSC_Var('terms').value(ctxt).reduce(\max)})],
		['clip', FLSC_UGenFunc(nil, ['val', 'lo', 'hi'],
			{|ctxt| FLSC_Var('val').value(ctxt).clip(
				FLSC_Var('lo').value(ctxt),FLSC_Var('hi').value(ctxt))})],
		['fold', FLSC_UGenFunc(nil, ['val', 'lo', 'hi'],
			{|ctxt| FLSC_Var('val').value(ctxt).fold(
				FLSC_Var('lo').value(ctxt),FLSC_Var('hi').value(ctxt))})],
		['wrap', FLSC_UGenFunc(nil, ['val', 'lo', 'hi'],
			{|ctxt| FLSC_Var('val').value(ctxt).wrap(
				FLSC_Var('lo').value(ctxt),FLSC_Var('hi').value(ctxt))})],

		// BOOLEENS:
		// opérateurs:
		['not', FLSC_Function(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).not})],
		['and', FLSC_RestFunc(nil, ['terms'],
			{|ctxt| FLSC_Var('terms').value(ctxt).inject(true, _&&_)})],
		['or', FLSC_RestFunc(nil, ['terms'],
			{|ctxt| FLSC_Var('terms').value(ctxt).inject(false, _||_)})],
		['xor', FLSC_RestFunc(nil, ['terms'],
			{|ctxt| FLSC_Var('terms').value(ctxt).inject(false, _.xor(_))})],
		// tests de type:
		['?', FLSC_Function(nil, ['obj'], {|ctxt| FLSC_Var('obj').value(ctxt).notFLSCNil})],
		['nil?', FLSC_Function(nil, ['obj'], {|ctxt| FLSC_Var('obj').value(ctxt).isFLSCNil})],
		['boolean?', FLSC_Function(nil, ['obj'],
			{|ctxt| FLSC_Var('obj').value(ctxt).isKindOf(Boolean)})],
		['number?', FLSC_Function(nil, ['obj'], {|ctxt| FLSC_Var('obj').value(ctxt).isNumber})],
		['function?', FLSC_Function(nil, ['obj'],
			{|ctxt| FLSC_Var('obj').value(ctxt).isKindOf(FLSC_Function)})],
		['list?', FLSC_Function(nil, ['obj'], {|ctxt| FLSC_Var('obj').value(ctxt).isArray})],
		['empty?', FLSC_Function(nil, ['obj'],
			{|ctxt|
				var val = FLSC_Var('obj').value(ctxt);
				val.isArray && (val.size == 0)})],
		['sig?', FLSC_Function(nil, ['obj'],
			{|ctxt| FLSC_Var('obj').value(ctxt).isFLSCScoreSpec})],
		['audio?', FLSC_Function(nil, ['obj'],
			{|ctxt| FLSC_Var('obj').value(ctxt).rate == 'audio'})],
		['control?', FLSC_Function(nil, ['obj'],
			{|ctxt| FLSC_Var('obj').value(ctxt).rate == 'control'})],
		// comparaisons:
		['eq?', FLSC_RestFunc(nil, ['first', 'rest'],
			{|ctxt|
				var first = FLSC_Var('first').value(ctxt);
				var rest = FLSC_Var('rest').value(ctxt);
				rest.inject(true, {|acc, val| acc && (first == val)})})],
		['neq?', FLSC_Function(nil, ['a', 'b'],
			{|ctxt| FLSC_Var('a').value(ctxt) != FLSC_Var('b').value(ctxt)})],
		['gt?', FLSC_RestFunc(nil, ['first', 'rest'],
			{|ctxt|
				var cur = FLSC_Var('first').value(ctxt);
				var rest = FLSC_Var('rest').value(ctxt);
				rest.inject(true, {|acc, val|
					var res = acc && (cur > val);
					cur = val;
					res})})],
		['lt?', FLSC_RestFunc(nil, ['first', 'rest'],
			{|ctxt|
				var cur = FLSC_Var('first').value(ctxt);
				var rest = FLSC_Var('rest').value(ctxt);
				rest.inject(true, {|acc, val|
					var res = acc && (cur < val);
					cur = val;
					res})})],
		['gte?', FLSC_RestFunc(nil, ['first', 'rest'],
			{|ctxt|
				var cur = FLSC_Var('first').value(ctxt);
				var rest = FLSC_Var('rest').value(ctxt);
				rest.inject(true, {|acc, val|
					var res = acc && (cur >= val);
					cur = val;
					res})})],
		['lte?', FLSC_RestFunc(nil, ['first', 'rest'],
			{|ctxt|
				var cur = FLSC_Var('first').value(ctxt);
				var rest = FLSC_Var('rest').value(ctxt);
				rest.inject(true, {|acc, val|
					var res = acc && (cur <= val);
					cur = val;
					res})})],

		// LISTES:
		// constructeurs:
		['list', FLSC_RestFunc(nil, ['terms'], {|ctxt| FLSC_Var('terms').value(ctxt)})],
		['cons', FLSC_Function(nil, ['a', 'b'],
			{|ctxt| [FLSC_Var('a').value(ctxt)] ++ FLSC_Var('b').value(ctxt).asArray})],
		['add', FLSC_Function(nil, ['a', 'b'],
			{|ctxt| FLSC_Var('a').value(ctxt).asArray ++ [FLSC_Var('b').value(ctxt)]})],
		['insert', FLSC_Function(nil, ['list', 'elt', 'i'],
			{|ctxt|
				var index = FLSC_Var('i').value(ctxt);
				var list = FLSC_Var('list').value(ctxt).asArray;
				list[..index-1] ++
				[FLSC_Var('elt').value(ctxt)] ++
				list[index..]})],
		['append', FLSC_RestFunc(nil, ['terms'],
			{|ctxt| FLSC_Var('terms').value(ctxt).collect(_.asArray).inject([], _++_)})],
		['range', FLSC_Function(nil, ['a', 'b'],
			{|ctxt| (FLSC_Var('a').value(ctxt)..FLSC_Var('b').value(ctxt))})],
		['reverse', FLSC_Function(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).reverse})],
		['repeat', FLSC_Function(nil, ['elt', 'num'],
			{|ctxt|
				var elt = FLSC_Var('elt').value(ctxt);
				Array.fill(FLSC_Var('num').value(ctxt), {elt})})],
		// accesseurs:
		['elt', FLSC_Function(nil, ['list', 'i'],
			{|ctxt| FLSC_Var('list').value(ctxt)[FLSC_Var('i').value(ctxt)]})],
		['subseq', FLSC_Function(nil, ['list', 'i', 'j'],
			{|ctxt| FLSC_Var('list').value(ctxt)
				[FLSC_Var('i').value(ctxt)..FLSC_Var('j').value(ctxt)-1]})],
		['length', FLSC_Function(nil, ['list'], {|ctxt| FLSC_Var('list').value(ctxt).size})],
		['position', FLSC_Function(nil, ['list', 'elt'],
			{|ctxt| FLSC_Var('list').value(ctxt).indexOf(FLSC_Var('elt').value(ctxt))})],
		// opérations sur les listes:
		['flop', FLSC_Function(nil, ['list'],
			{|ctxt| FLSC_Var('list').value(ctxt).flop})],
		// itération:
		['mapcar', FLSC_RestFunc(nil, ['func', 'lists'],
			{|ctxt|
				var func = FLSC_Var('func').value(ctxt);
				var lists = FLSC_Var('lists').value(ctxt);
				lists.flop.collect({|item|
					func.value(item)})
		})],
		['select', FLSC_Function(nil, ['func', 'list'],
			{|ctxt|
				var func = FLSC_Var('func').value(ctxt);
				var list = FLSC_Var('list').value(ctxt);
				list.select({|item, i| func.value([item, i])});
		})],
		['reduce', FLSC_Function(nil, ['func', 'list', 'init'],
			{|ctxt|
				var func = FLSC_Var('func').value(ctxt);
				var list = FLSC_Var('list').value(ctxt);
				var init = FLSC_Var('init').value(ctxt);
				if(init.notFLSCNil,
					{
						list.inject(init, {|a,b| func.value([a, b])})
					}, {
						list.reduce({|a,b| func.value([a, b])})
				});
		})],
		['apply', FLSC_Function(nil, ['func', 'arglist'],
			{|ctxt| FLSC_Var('func').value(ctxt).value(FLSC_Var('arglist').value(ctxt))})],

		// GENERATEURS DE SIGNAL:
		// constante
		['const', FLSC_Function(nil, ['num'],
			{|ctxt|
				FLSC_UGen.newFromClass([FLSC_Var('num').value(ctxt)],
					DC, ['in'])})],
		// oscillateur sinusoïdal
		['osc', FLSC_Function(nil, ['freq', 'phi'],
			{|ctxt|
				var phi = FLSC_Var('phi').value(ctxt);
				if(phi.isFLSCNil) {phi = nil};
				FLSC_UGen.newFromClass([FLSC_Var('freq').value(ctxt), phi],
				SinOsc, ['freq', 'phase'], minRate: 'audio')})],
		// segment d'enveloppe
		['seg', FLSC_Function(nil, ['start', 'end'],
			{|ctxt|
				FLSC_UGen.newFromClass([FLSC_Var('start').value(ctxt),
					FLSC_Var('end').value(ctxt), FLSC_TimeControl(1)],
					Line, ['start', 'end', 'dur'],
					minRate: 'control')})],
		// enveloppe à nombre de points fixe
		['env', FLSC_Function(nil, ['levels'],
			{|ctxt|
				var levels = FLSC_Var('levels').value(ctxt);
				FLSC_UGen([levels,
				FLSC_TimeControl(levels.size - 1)],
				{|subs|
					EnvGen.kr(Env(subs[0], subs[1]));
				})
		})],
		// modulateurs:
		// sinusoïdal
		['lfo', FLSC_Function(nil, ['freq', 'phi'],
			{|ctxt|
				var phi = FLSC_Var('phi').value(ctxt);
				if(phi.isFLSCNil) {phi = nil};
				FLSC_UGen.newFromClass([FLSC_Var('freq').value(ctxt), phi],
				SinOsc, ['freq', 'phase'], minRate: 'control')})],
		// triangle
		['tri', FLSC_Function(nil, ['freq', 'phi'],
			{|ctxt|
				var phi = FLSC_Var('phi').value(ctxt);
				if(phi.isFLSCNil) {phi = nil};
				FLSC_UGen.newFromClass([FLSC_Var('freq').value(ctxt), phi],
				LFTri, ['freq', 'iphase'], minRate: 'control')})],
		// carré
		['squ', FLSC_Function(nil, ['freq', 'phi', 'width'],
			{|ctxt|
				var phi = FLSC_Var('phi').value(ctxt);
				var width = FLSC_Var('width').value(ctxt);
				if(phi.isFLSCNil) {phi = nil};
				if(width.isFLSCNil) {width = nil};
				FLSC_UGen.newFromClass([FLSC_Var('freq').value(ctxt), phi, width],
					LFPulse, ['freq', 'iphase', 'width'], minRate: 'control')})],
		// générateurs HF:
		// segment d'enveloppe
		['hfseg', FLSC_Function(nil, ['start', 'end'],
			{|ctxt|
				FLSC_UGen.newFromClass([FLSC_Var('start').value(ctxt),
					FLSC_Var('end').value(ctxt), FLSC_TimeControl(1)],
					Line, ['start', 'end', 'dur'],
					minRate: 'audio')})],
		// enveloppe à nombre de points fixe
		['hfenv', FLSC_Function(nil, ['levels'],
			{|ctxt|
				var levels = FLSC_Var('levels').value(ctxt);
				FLSC_UGen([levels,
				FLSC_TimeControl(levels.size - 1)],
				{|subs|
					EnvGen.ar(Env(subs[0], subs[1]));
				})
		})],
		// triangle
		['hftri', FLSC_Function(nil, ['freq', 'phi'],
			{|ctxt|
				var phi = FLSC_Var('phi').value(ctxt);
				if(phi.isFLSCNil) {phi = nil};
				FLSC_UGen.newFromClass([FLSC_Var('freq').value(ctxt), phi],
					LFTri, ['freq', 'iphase'], minRate: 'audio')})],
		// carré
		['hfsqu', FLSC_Function(nil, ['freq', 'phi', 'width'],
			{|ctxt|
				var phi = FLSC_Var('phi').value(ctxt);
				var width = FLSC_Var('width').value(ctxt);
				if(phi.isFLSCNil) {phi = nil};
				if(width.isFLSCNil) {width = nil};
				FLSC_UGen.newFromClass([FLSC_Var('freq').value(ctxt), phi, width],
					LFPulse, ['freq', 'iphase', 'width'], minRate: 'audio')})],

		// générateurs aléatoires:
		// générateur par paliers
		['rand', FLSC_Function(nil, ['max', 'min', 'freq'],
			{|ctxt|
				var max = FLSC_Var('max').value(ctxt);
				var min = FLSC_Var('min').value(ctxt);
				var freq = FLSC_Var('freq').value(ctxt);
				if(max.isFLSCNil) {max = 1};
				if(min.isFLSCNil) {min = 0};
				if(freq.isFLSCNil) {freq = 500};
				FLSC_UGen([max, min, freq],
					{|subs|
						var max = subs[0];
						var min = subs[1];
						var freq = subs[2];
						if(max.isNil) {max = 1};
						if(min.isNil) {min = 0};
						if(freq.isNil) {freq = 500};
						if((max.rate == 'audio') || (min.rate == 'audio'))
						{LFNoise0.ar(freq, max - min, min)}
						{LFNoise0.kr(freq, max - min, min)};
					}
		)})],
		// générateur de ligne brisée
		['crand', FLSC_Function(nil, ['max', 'min', 'freq'],
			{|ctxt|
				var max = FLSC_Var('max').value(ctxt);
				var min = FLSC_Var('min').value(ctxt);
				var freq = FLSC_Var('freq').value(ctxt);
				if(max.isFLSCNil) {max = 1};
				if(min.isFLSCNil) {min = 0};
				if(freq.isFLSCNil) {freq = 500};
				FLSC_UGen([max, min, freq],
					{|subs|
						var max = subs[0];
						var min = subs[1];
						var freq = subs[2];
						if(max.isNil) {max = 1};
						if(min.isNil) {min = 0};
						if(freq.isNil) {freq = 500};
						if((max.rate == 'audio') || (min.rate == 'audio'))
						{LFNoise1.ar(freq, max - min, min)}
						{LFNoise1.kr(freq, max - min, min)};
					}
		)})],
		// conversions:
		// contrôle vers audio
		['k2a', FLSC_Function(nil, ['input'],
			{|ctxt|
				FLSC_UGen([FLSC_Var('input').value(ctxt)],
					{|subs|
						var input = subs[0];
						if(input.rate != 'control')
						{FLSC_Error("Non-control in k2a: %".format(input)).throw};
						K2A.ar(input)
				});
		})],

		// ALTERATIONS DE SIGNATURE:
		// durée du support temporel courant
		['dur', {|t|t}],
		// liste de temps relatifs à la durée
		['relt', FLSC_Function(nil, ['list'],
			{|ctxt| FLSC_Var('list').value(ctxt).collect(_*{|t|t})})],
		['negt', FLSC_Function(nil, ['list'],
			{|ctxt| FLSC_Var('list').value(ctxt).collect({|t|t}-_)})],
		['rnt', FLSC_Function(nil, ['list'],
			{|ctxt| FLSC_Var('list').value(ctxt).collect {|e|{|t|t}*(1-e)}})],
		// retard
		['delay', FLSC_Function(nil, ['theta', 'sig'],
			{|ctxt|
				var dt = FLSC_Var('theta').value(ctxt);
				var sig = FLSC_Var('sig').value(ctxt).asFLSCScoreSpec(true);
				var warp = FLSC_WarpSpec({|t|
					// vérifier que la valeur est non-signée
					if(t.size > 1)
					{FLSC_Error("Signed time value in delay: %".format(t)).throw};
					[t.first + dt]
				}, sig);
				if(sig.rate != 'audio')
				{FLSC_Error("Non-audio in delay: %".format(sig)).throw};
				FLSC_GlobalTimeSpec('audio', warp);
		})],
		// definition de support
		['base', FLSC_Function(nil, ['base', 'sig'],
			{|ctxt|
				var base = FLSC_Var('base').value(ctxt);
				var start = base[0], end = base[1];
				var sig = FLSC_Var('sig').value(ctxt).asFLSCScoreSpec;
				var warp = FLSC_WarpSpec({|t|
					// vérifier que la valeur n'est pas signée
					if(t.size > 1)
					{FLSC_Error("Signed time value in unsigned base: %".format(t)).throw};
					// situer la valeur dans le support temporel courant
					[start + t.first.(end - start)];
				}, sig, {|new, old|
						// vérifier l'inclusion du support temporel
						// dans le support englobant
						// ainsi que l'ordre des bornes
						var newStart = new.([0]);
						var oldStart = old.([0]);
						var newEnd = new.([{|t|t}]);
						var oldEnd = old.([{|t|t}]);
						if(newStart > newEnd)
						{FLSC_Error("Anachronism in timebase: duration(%) < 0".format(
							newEnd - newStart)).throw};
						if((newStart < oldStart) || (newEnd > oldEnd))
						{FLSC_Error("Timebase out of bounds: % not in %".format(
							[newStart, newEnd] - oldStart, [0, oldEnd - oldStart])).throw};
					}
				);
				if(sig.rate != 'audio')
				{FLSC_Error("Non-audio in base: %".format(sig)).throw};
				FLSC_LocalTimeSpec('audio', warp);
		})],
		// signatures:
		// definition de signature
		['sign', FLSC_Function(nil, ['signs', 'sig'],
			{|ctxt|
				var signs = FLSC_Var('signs').value(ctxt);
				var sig = FLSC_Var('sig').value(ctxt).asFLSCScoreSpec;
				var warp = FLSC_WarpSpec({|t|
					if(t.size > 1) {
						var indexes = t[1..];
						var subsign = signs;
						while {(indexes.size > 1) && subsign.notFLSCNil}
						{
							subsign = subsign[indexes.first];
							if(subsign.isNil)
							{FLSC_Error("No defined signature at index %".format(
								t[1..])).throw};
							indexes = indexes[1..];
						};
						if(subsign.notFLSCNil)
						{
							var index = indexes.first;
							var selected = [0] ++ subsign ++ [{|t|t}];
							var start = selected[index];
							var end = selected[index+1];
							if(selected.maxDepth != 1)
							{FLSC_Error("Multiple signature selected: depth(%)"
								.format(selected.maxDepth)).throw};
							if(index > (selected.size-2))
							{FLSC_Error("Signature index(%) is undefined".format(index)).throw};
							[start + t.first.(end - start)];
						} {
							[t.first] ++ indexes;
						};
					} {
						// la valeur peut provenir d'un module, d'une durée non-signée
						// ou d'une signature de remplacement
						// dans ce cas, ne rien faire
						t
					}
				}, sig, {|new, old|
						// vérifier l'ordre des signatures
						var check = {|signs|
							case
							{signs.isFLSCNil} {}
							{signs.maxDepth > 1} {signs.do(check)}
							{signs.maxDepth == 1}
							{
								var timeVals = ([[0]] ++ signs.collect([_]) ++
									[[{|t|t}]]).collect(old);
								if(timeVals.differentiate.minItem < 0)
								{FLSC_Error("Signature not sorted: %".format(
									timeVals - timeVals[0])).throw}
							}
						};
						check.(signs);
					}
				);
				FLSC_LocalTimeSpec(sig.rate, warp);
		})],
		// découpage de signature
		['ssplit', FLSC_Function(nil, ['index', 'time', 'sig'],
			{|ctxt|
				var sindex = FLSC_Var('index').value(ctxt);
				var stime = FLSC_Var('time').value(ctxt);
				var sig = FLSC_Var('sig').value(ctxt).asFLSCScoreSpec;
				var warp = FLSC_WarpSpec({|t|
					if(t.size > 1) {
						var index = t[1..];
						var time = t.first;
						// on ne gère pas le cas des découpages sur signatures multiples
						if(index.size > 1)
						{FLSC_Error("Split on multiple indexes: %".format(index)).throw};
						index = index.first;
						case
						{index < sindex}        {[time, index]}
						{index == sindex}       {[time.(stime), sindex]}
						{index == (sindex + 1)} {[stime + time.({|t|t} - stime), sindex]}
						{index > (sindex + 1)}  {[time, index - 1]}
					} {
						// la valeur peut provenir d'un module, d'une durée non-signée
						// ou d'une signature de remplacement
						// dans ce cas, ne rien faire
						t
					}
				}, sig, {|new, old|
						// vérifier que le temps est compris dans le segment
						var timeVals =
						[[0], [0, sindex], [stime, sindex], [{|t|t}, sindex]].collect(old);
						if(timeVals.differentiate.minItem < 0)
						{FLSC_Error("Signature split is out of bounds: % not in %".format(
							timeVals[2] - timeVals[0],
							[timeVals[1], timeVals[3]] - timeVals[0])).throw}
					}
				);
				FLSC_LocalTimeSpec(sig.rate, warp);
		})],
		// selection de signature
		['ssel', FLSC_Function(nil, ['indexes', 'sig'],
			{|ctxt|
				var indexList = FLSC_Var('indexes').value(ctxt).asArray;
				var sig = FLSC_Var('sig').value(ctxt).asFLSCScoreSpec;
				var warp = FLSC_WarpSpec({|t|
					if(t.size > 1) {
						var indexes = t[1..];
						var time = t.first;
						// ajouter les nouveaux indices au début de la séquence
						[time] ++ indexList ++ indexes;
					} {
						// la valeur peut provenir d'un module, d'une durée non-signée
						// ou d'une signature de remplacement
						// dans ce cas, ne rien faire
						t
					}
				}, sig);
				FLSC_LocalTimeSpec(sig.rate, warp);
		})],
		// compositions:
		// sequentielle
		['seq', FLSC_Function(nil, ['sigs'],
			{|ctxt|
				var sigs = FLSC_Var('sigs').value(ctxt).collect(_.asFLSCScoreSpec);
				var rate = sigs[0].rate;
				var warps = sigs.collect {|sig, i|
					if(sig.rate != rate)
					{FLSC_Error("Non-homogenous rate in seq: %".format(sig.rate)).throw};
					FLSC_WarpSpec({|t|
						// vérifier que la valeur n'est pas signée
						if(t.size > 1)
						{FLSC_Error("Signed time value in unsigned base: %".format(t)).throw};
						t ++ [i]
					}, sig)
				};
				FLSC_CompSpec(rate, warps, {|warp|
					// vérifier que la fin de la séquence est bien la fin du support
					var seqEnd = warp.([{|t|t}, sigs.size - 1]);
					var baseEnd = warp.([{|t|t}]);
					if(seqEnd != baseEnd)
					{FLSC_Error("End of seq(%) is different from base end(%)"
						.format(seqEnd, baseEnd)).throw}
				});
		})],

		// FONCTIONS DIVERSES
		// affichage de messages
		['post', FLSC_RestFunc(nil, ['expr', 'fmt', 'args'],
			{|ctxt|
				FLSC_Var('fmt').(ctxt).performList(\format, FLSC_Var('args').(ctxt)).postln;
				FLSC_Var('expr').(ctxt);
			}
		)],
	]
}.value();
