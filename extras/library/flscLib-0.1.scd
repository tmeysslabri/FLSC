[
	// CONSTANTES
	['nil', nil],
	['t', true],
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
	// constantes:
	// false - nil
	// true - t
	// comparaisons:
	// ==
	// !=
	// >
	// <
	// >=
	// <=
	// opérateurs:
	// not
	// &&
	// ||

	// LISTES:
	// constructeurs:
	['list', FLSC_RestFunc(nil, ['terms'], {|ctxt| FLSC_Var('terms').value(ctxt)})],
	['cons', FLSC_Function(nil, ['a', 'b'],
		{|ctxt| FLSC_Var('a').value(ctxt).addFirst(FLSC_Var('b').value(ctxt))})],
	['add', FLSC_Function(nil, ['a', 'b'],
		{|ctxt| FLSC_Var('a').value(ctxt).add(FLSC_Var('b').value(ctxt))})],
	['insert', FLSC_Function(nil, ['a', 'b', 'c'],
		{|ctxt| FLSC_Var('a').value(ctxt).insert(
			FLSC_Var('b').value(ctxt), FLSC_Var('c').value(ctxt))})],
	['rmvat', FLSC_Function(nil, ['a', 'b'],
		{|ctxt| var  tmp = FLSC_Var('a').value(ctxt);
			tmp.removeAt(FLSC_Var('b').value(ctxt));
			tmp;})],
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
			/*
			var maxSize = lists.collect({|a| a.size}).inject(0, {|a,b| max(a,b)});
			lists = lists.collect({|l| l ++ (nil!(maxSize - l.size))});
			*/
			lists.flop.collect({|item|
				func.value(item)})
	})],
	['reduce', FLSC_Function(nil, ['func', 'list', 'init'],
		{|ctxt|
			var func = FLSC_Var('func').value(ctxt);
			var list = FLSC_Var('list').value(ctxt);
			var init = FLSC_Var('init').value(ctxt);
			if(init.notNil,
				{
					list.inject(init, {|a,b| func.value([a, b])})
				}, {
					list.reduce({|a,b| func.value([a, b])})
			});
	})],

	// CONTROLE
	// if
	// switch
	// case
]