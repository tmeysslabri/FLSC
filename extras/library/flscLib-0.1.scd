[
	['+', FLSC_RestFunc(nil, ['terms'], {|ctxt| FLSC_Var('terms').value(ctxt).inject(0, _+_)})],
	['*', FLSC_RestFunc(nil, ['terms'], {|ctxt| FLSC_Var('terms').value(ctxt).inject(1, _*_)})],
	['-', FLSC_RestFunc(nil, ['base', 'terms'],
		{|ctxt| FLSC_Var('terms').value(ctxt).inject(FLSC_Var('base').value(ctxt), _-_)})],
	['/', FLSC_RestFunc(nil, ['base', 'terms'],
		{|ctxt| FLSC_Var('terms').value(ctxt).inject(FLSC_Var('base').value(ctxt), _/_)})],
	['**', FLSC_Function(nil, ['a', 'b'],
		{|ctxt| FLSC_Var('a').value(ctxt) ** FLSC_Var('b').value(ctxt)})],
]