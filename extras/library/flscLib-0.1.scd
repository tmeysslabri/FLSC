{
	[
		// CONSTANTES
		['false', false],
		['true', true],
		['fl', false],
		['tr', true],
		['inf', inf],
		['dur', {|t|t}], // durée du support temporel courant
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
			{|ctxt| FLSC_Var('terms').value(ctxt).inject(0, _+_)})],
		['*', FLSC_RestUGenFunc(nil, ['terms'],
			{|ctxt| FLSC_Var('terms').value(ctxt).inject(1, _*_)})],
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
			{|ctxt| FLSC_Var('elt').value(ctxt) ! FLSC_Var('num').value(ctxt)})],
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
						{Error("Non-control in k2a: %".format(input)).throw};
						K2A.ar(input)
				});
		})],

		// ALTERATIONS DE SIGNATURE:
		// retard
		['delay', FLSC_Function(nil, ['theta', 'sig'],
			{|ctxt|
				var dt = FLSC_Var('theta').value(ctxt);
				var sig = FLSC_Var('sig').value(ctxt).asFLSCScoreSpec(true);
				var warp = FLSC_WarpSpec({|t| t + dt}, sig);
				if(sig.rate != 'audio')
				{Error("Non-audio in delay: %".format(sig)).throw};
				FLSC_GlobalTimeSpec('audio', warp);
		})],
		// definition de support
		['base', FLSC_Function(nil, ['start', 'end', 'sig'],
			{|ctxt|
				var start = FLSC_Var('start').value(ctxt);
				var end = FLSC_Var('end').value(ctxt);
				var sig = FLSC_Var('sig').value(ctxt).asFLSCScoreSpec;
				var warp = FLSC_WarpSpec({|t|
					start + t.(end - start);
				}, sig, {|new, old|
						if((new.(0) < old.(0)) || (new.({|t|t}) > old.({|t|t})))
						{Error("Timebase out of bounds").throw}
					}
				);
				if(sig.rate != 'audio')
				{Error("Non-audio in base: %".format(sig)).throw};
				FLSC_LocalTimeSpec('audio', warp);
		})],
		// definition de signature
		['sign', FLSC_Function(nil, ['times', 'sig'],
			{|ctxt|
				var times = [0] ++ FLSC_Var('times').value(ctxt) ++ [{|t|t}];
				var sig = FLSC_Var('sig').value(ctxt).asFLSCScoreSpec;
				var warp = FLSC_WarpSpec({|t|
					t;
					if(t.isArray) {
						var index = t[0];
						var start = times[index];
						(start + t[1].(times[index+1] - start))
					} {
						t
					}
				}, sig, {|new, old|
						if(times.collect(old).differentiate.minItem < 0)
						{Error("Signature not sorted").throw}
					}
				);
				FLSC_LocalTimeSpec(sig.rate, warp);
		})],
		/*
		// sous-signature
		['ssub', FLSC_Function(nil, ['sign', 'sig'],
			{|ctxt|
				var sign = FLSC_Var('sign').value(ctxt);
				var sig = FLSC_Var('sig').value(ctxt).asFLSCScoreSpec;
				var warp = FLSC_WarpSpec({|pair|
					var t = pair[0];
					[
						case
						{t == 0}      {0}
						{t == 'end'}  {'end'}
						{t.isInteger} {sign[t-1]}
						{true}        {Error("Non-integer signature index").throw},
						pair[1]
					]
				}, sig);
				FLSC_LocalSignSpec(sig.rate, warp);
		})],
		// sélection de signature
		['ssel', FLSC_Function(nil, ['indexes', 'sig'],
			{|ctxt|
				var indexes = FLSC_Var('indexes').value(ctxt).asArray;
				var sig = FLSC_Var('sig').value(ctxt).asFLSCScoreSpec;
				var warp;
				if(sig.rate != 'audio')
				{Error("Non-audio in ssel: %".format(sig)).throw};
				warp = FLSC_WarpSpec({|pair|
					var i = pair[1];
					[
						pair[0],
						case
						{i.isNumber}  {indexes[i]}
						{i.isArray}   {i.collect(indexes[_])}
						{i.isNil}     {indexes}
						{true}        {Error("Non-compatible signature number").throw}
					]
				}, sig);
				FLSC_LocalSignSpec('audio', warp);
		})],
		// combinaison de sous-signature et de sélection
		['ssubsel', FLSC_Function(nil, ['specs', 'sig'],
			{|ctxt|
				var specs = FLSC_Var('specs').value(ctxt).collect
				{|item| [item[0], item[1].asArray]};
				var sig = FLSC_Var('sig').value(ctxt).asFLSCScoreSpec;
				var warp = if(sig.rate == 'audio')
				{
					FLSC_WarpSpec({|pair|
						var t = pair[0];
						var i = pair[1];
						if(t == 'end')  { t = specs.size - 1 };
						if(t.isInteger)
						{[
							specs[t][0],
							case
							{i.isNumber}  {specs[t][1][i]}
							{i.isArray}   {i.collect(specs[t][1][_])}
							{i.isNil}     {specs[t][1]}
							{true}        {Error("Non-compatible signature number").throw}
						]}
						{Error("Non-integer signature index").throw}
					}, sig)
				} {
					FLSC_WarpSpec({|pair|
						var t = pair[0];
						var i = pair[1];
						case
						{t == 0}      {[0, nil]}
						{t == 'end'}  {['end', nil]}
						{t.isInteger}
						{[
							specs[t-1][0],
							case
							{i.isNumber}  {specs[t-1][1][i]}
							{i.isArray}   {i.collect(specs[t-1][1][_])}
							{i.isNil}     {specs[t-1][1]}
							{true}        {Error("Non-compatible signature number").throw}
						]}
						{true}        {Error("Non-integer signature index").throw}
					}, sig)
				};
				FLSC_LocalSignSpec(sig.rate, warp);
		})],
		*/
		// compositions:
		// sequentielle
		['seq', FLSC_Function(nil, ['sigs'],
			{|ctxt|
				var sigs = FLSC_Var('sigs').value(ctxt);
				var rate = sigs[0].rate;
				var warps = sigs.collect {|sig, i|
					if(sig.rate != rate)
					{Error("Non-homogenous rate in seq: %".format(sig.rate)).throw};
					FLSC_WarpSpec({|t|
						[i, t]
					}, sig)
				};
				FLSC_CompSpec(rate, warps);
		})],
		/*
		// sequentielle
		['seq', FLSC_Function(nil, ['breaks', 'sigs'],
			{|ctxt|
				var breaks = FLSC_Var('breaks').value(ctxt);
				var sigs = FLSC_Var('sigs').value(ctxt);
				var segs = [[0]++breaks,breaks++['end']].flop;
				var warps = [segs, sigs].flop.collect {|item|
					var sig = item[1];
					if(sig.rate != 'control')
					{Error("Non-control in seq: %".format(sig)).throw};
					FLSC_WarpSpec({|pair|
						var t = pair[0];
						[
							case
							{t == 'end'}  {item[0][1]}
							{t.isInteger} {item[0][0] + t}
							{true}        {Error("Non-integer signature index").throw},
							pair[1]
						]
					}, sig)
				};
				FLSC_CompSpec('control', warps);
		})],
		// parallèle
		['par', FLSC_Function(nil, ['subs', 'sigs'],
			{|ctxt|
				var subs = FLSC_Var('subs').value(ctxt);
				var sigs = FLSC_Var('sigs').value(ctxt);
				var warps;
				if(subs.isFLSCNil) {subs = [0,'end'] ! sigs.size};
				subs = subs.collect {|item| if(item.isFLSCNil) {[0,'end']} {item}};
				warps = [subs, sigs].flop.collect {|item|
					var sig = item[1].asFLSCScoreSpec;
					if(sig.rate != 'audio')
					{Error("Non-audio in par: %".format(sig)).throw};
					FLSC_WarpSpec({|pair|
						var t = pair[0];
						[
							case
							{t == 'end'}  {item[0][1]}
							{t.isInteger} {item[0][0] + t}
							{true}        {Error("Non-integer signature index").throw},
							pair[1]
						]
					}, sig)
				};
				FLSC_CompSpec('audio', warps);
		})],
		*/
	]
}.value();