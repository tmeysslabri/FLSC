[
	// CONSTANTES
	// ['nil', nil], // remplacé par FLSC_Nil
	['false', false],
	['true', true],
	['inf', inf],
	// FONCTIONS NUMERIQUES:
	// opérateurs unaires:
	['neg', FLSC_Function(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).neg})],
	['abs', FLSC_Function(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).abs})],
	['sign', FLSC_Function(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).sign})],
	// opérateurs arithmétiques:
	['+', FLSC_RestFunc(nil, ['terms'], {|ctxt| FLSC_Var('terms').value(ctxt).inject(0, _+_)})],
	['*', FLSC_RestFunc(nil, ['terms'], {|ctxt| FLSC_Var('terms').value(ctxt).inject(1, _*_)})],
	['-', FLSC_RestFunc(nil, ['base', 'terms'],
		{|ctxt| FLSC_Var('terms').value(ctxt).inject(FLSC_Var('base').value(ctxt), _-_)})],
	['/', FLSC_RestFunc(nil, ['base', 'terms'],
		{|ctxt| FLSC_Var('terms').value(ctxt).inject(FLSC_Var('base').value(ctxt), _/_)})],
	['**', FLSC_Function(nil, ['a', 'b'],
		{|ctxt| FLSC_Var('a').value(ctxt) ** FLSC_Var('b').value(ctxt)})],
	['mod', FLSC_Function(nil, ['a', 'b'],
		{|ctxt| FLSC_Var('a').value(ctxt) % FLSC_Var('b').value(ctxt)})],
	// fonctions usuelles:
	['sin', FLSC_Function(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).sin})],
	['cos', FLSC_Function(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).cos})],
	['tan', FLSC_Function(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).tan})],
	['log2', FLSC_Function(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).log2})],
	['floor', FLSC_Function(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).floor})],
	['ceil', FLSC_Function(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).ceil})],
	['round', FLSC_Function(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).round})],
	['min', FLSC_RestFunc(nil, ['terms'],
		{|ctxt| FLSC_Var('terms').value(ctxt).reduce(\min)})],
	['max', FLSC_RestFunc(nil, ['terms'],
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
	// tests:
	['?', FLSC_Function(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).notFLSCNil})],
	['nil?', FLSC_Function(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).isFLSCNil})],
	['boolean?', FLSC_Function(nil, ['a'],
		{|ctxt| FLSC_Var('a').value(ctxt).isKindOf(Boolean)})],
	['number?', FLSC_Function(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).isNumber})],
	['function?', FLSC_Function(nil, ['a'],
		{|ctxt| FLSC_Var('a').value(ctxt).isKindOf(FLSC_Function)})],
	['list?', FLSC_Function(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).isArray})],
	['empty?', FLSC_Function(nil, ['a'],
		{|ctxt|
			var val = FLSC_Var('a').value(ctxt);
			val.isArray && (val.size == 0)})],
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
		{|ctxt| [FLSC_Var('a').value(ctxt)] ++ FLSC_Var('b').value(ctxt)})],
	['add', FLSC_Function(nil, ['a', 'b'],
		{|ctxt| FLSC_Var('a').value(ctxt) ++ [FLSC_Var('b').value(ctxt)]})],
	['insert', FLSC_Function(nil, ['a', 'b', 'c'],
		{|ctxt|
			var index = FLSC_Var('c').value(ctxt);
			FLSC_Var('a').value(ctxt)[..index-1] ++
			[FLSC_Var('b').value(ctxt)] ++
			FLSC_Var('a').value(ctxt)[index..]})],
	['append', FLSC_RestFunc(nil, ['terms'],
		{|ctxt| FLSC_Var('terms').value(ctxt).inject([], _++_)})],
	['range', FLSC_Function(nil, ['a', 'b'],
		{|ctxt| (FLSC_Var('a').value(ctxt)..FLSC_Var('b').value(ctxt))})],
	['reverse', FLSC_Function(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).reverse})],
	// accesseurs:
	['elt', FLSC_Function(nil, ['a', 'b'],
		{|ctxt| FLSC_Var('a').value(ctxt)[FLSC_Var('b').value(ctxt)]})],
	['subseq', FLSC_Function(nil, ['a', 'b', 'c'],
		{|ctxt| FLSC_Var('a').value(ctxt)
			[FLSC_Var('b').value(ctxt)..FLSC_Var('c').value(ctxt)-1]})],
	['length', FLSC_Function(nil, ['a'], {|ctxt| FLSC_Var('a').value(ctxt).size})],
	['position', FLSC_Function(nil, ['a', 'b'],
		{|ctxt| FLSC_Var('a').value(ctxt).indexOf(FLSC_Var('b').value(ctxt))})],
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
]