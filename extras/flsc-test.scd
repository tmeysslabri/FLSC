(
x = FLSC_Lambda(['x'],
	FLSC_Call(
		FLSC_Var('+'),[
			FLSC_Num(5),
			FLSC_Var('x')
	])
);
)

(
x = FLSC_Patch(['sign','object'],
	FLSC_Var('sign'),
	FLSC_Var('object')
);
)

(
x = FLSC_LetStar([
	['x',FLSC_Num(5)],
	['y',FLSC_Call(
		FLSC_Var('+'),[
			FLSC_Var('x'),
			FLSC_Num(4)
	])]
	],
	FLSC_Var('y')
)
)

(
x = FLSC_Call(
	FLSC_Var('insert'),[
		FLSC_Call(
			FLSC_Var('list'),[
				FLSC_Num(1),
				FLSC_Num(2),
				FLSC_Num(3),
				FLSC_Num(4)
		]),
		FLSC_Num(10),
		FLSC_Num(2)
])
)

(
x = FLSC_Call(
	FLSC_Var('+'),[
		FLSC_Num(0.5pi),
		FLSC_Num(5),
		FLSC_Num(6)
])
)reverse

(
x = FLSC_Call(
	FLSC_Var('mapcar'),[
		FLSC_Lambda(['x'],
			FLSC_Call(
				FLSC_Var('*'),[
					FLSC_Num(2),
					FLSC_Var('x')
			])
		),
		FLSC_Call(
			FLSC_Var('range'),[
				FLSC_Num(1),
				FLSC_Num(4)
		])
])
)

(
x = FLSC_If(
	FLSC_Call(
		FLSC_Var('lt?'),[
			FLSC_Num(4),
			FLSC_Num(5)
	]),
	FLSC_Var('true'),
	FLSC_Var('false')
)
)

(
x = FLSC_Cond([
	[FLSC_Call(
		FLSC_Var('eq?'),[
			FLSC_Num(1),
			FLSC_Num(0)
	]),FLSC_Num(10)],
	[FLSC_Call(
		FLSC_Var('gte?'),[
			FLSC_Num(200),
			FLSC_Num(300)
	]),FLSC_Num(11)],
	[FLSC_Call(
		FLSC_Var('not'),[
			FLSC_Var('true')
	]),FLSC_Num(12)]
])
)

(
x = FLSC_LetRec([
	['x',FLSC_Var('false')],
	['y',FLSC_Nil()]
	],
	FLSC_If(
		FLSC_Call(
			FLSC_Var('?'),[
				FLSC_Var('x')
		]),
		FLSC_Call(
			FLSC_Var('not'),[
				FLSC_Var('x')
		]),
		FLSC_Var('inf')
	)
)
)

(
x = FLSC_LetRec([
	['fact',FLSC_Lambda(['n'],
		FLSC_If(
			FLSC_Call(
				FLSC_Var('eq?'),[
					FLSC_Var('n'),
					FLSC_Num(1)
			]),
			FLSC_Num(1),
			FLSC_Call(
				FLSC_Var('*'),[
					FLSC_Var('n'),
					FLSC_Call(
						FLSC_Var('fact'),[
							FLSC_Call(
								FLSC_Var('-'),[
									FLSC_Var('n'),
									FLSC_Num(1)
							])
					])
			])
		)
	)]
	],
	FLSC_Call(
		FLSC_Var('fact'),[
			FLSC_Num(10)
	])
)
)

(
x = FLSC_Call(
	FLSC_Var('apply'),[
		FLSC_Var('*'),
		FLSC_Call(
			FLSC_Var('list'),[
				FLSC_Num(3),
				FLSC_Num(4),
				FLSC_Num(5),
				FLSC_Num(6)
		])
])
)

(
x = FLSC_LetRec([
	['rec',FLSC_Lambda(['i'],
		FLSC_Call(
			FLSC_Var('list'),[
				FLSC_Var('i'),
				FLSC_Lambda([],
					FLSC_Call(
						FLSC_Var('rec'),[
							FLSC_Call(
								FLSC_Var('+'),[
									FLSC_Num(1),
									FLSC_Var('i')
							])
					])
				)
		])
	)]
	],
	FLSC_Call(
		FLSC_Var('elt'),[
			FLSC_Call(
				FLSC_Call(
					FLSC_Var('elt'),[
						FLSC_Call(
							FLSC_Call(
								FLSC_Var('elt'),[
									FLSC_Call(
										FLSC_Var('rec'),[
											FLSC_Num(1)
									]),
									FLSC_Num(1)
							]),[
						]),
						FLSC_Num(1)
				]),[
			]),
			FLSC_Num(0)
	])
)
)

(
x = FLSC_Cond([
	[FLSC_Call(
		FLSC_Var('eq?'),[
			FLSC_Num(1),
			FLSC_Num(2)
	]),FLSC_Var('false')],
	[FLSC_Else(),FLSC_Var('inf')]
])
)

(
x = FLSC_Call(
	FLSC_Var('+'),[
		FLSC_Var('nimp'),
		FLSC_Num(1)
])
)

(
x = FLSC_Let([
	['x',FLSC_Nil()],
	['y',FLSC_Nil()]
	],
	FLSC_Call(
		FLSC_Var('eq?'), [
			FLSC_Var('x'),
			FLSC_Var('y')
	])
)
)

(
x = FLSC_Call(
	FLSC_Var('mapcar'),[
		FLSC_Lambda(['x'],
			FLSC_Call(
				FLSC_Var('*'),[
					FLSC_Var('x'),
					FLSC_Num(2)
			])
		),
		FLSC_List([
			FLSC_Num(1),
			FLSC_Num(2),
			FLSC_Num(3)
		])
])
)

(
x = FLSC_Call(
	FLSC_Lambda(['x'],
		FLSC_Call(
			FLSC_Var('nil?'),[
				FLSC_Var('x'),
				FLSC_Nil()
		])
	),[
])
)

(
x = FLSC_Call(
	FLSC_Var('nil?'),[
		FLSC_Call(
			FLSC_Var('-'),[
				FLSC_Num(3)
		])
])
)

(
x = FLSC_Call(
	FLSC_Var('add'),[
		FLSC_List([FLSC_Num(2)]),
		FLSC_Num(42)
])
)

(
x = FLSC_Call(
	FLSC_Var('reduce'),[
		FLSC_Lambda(['x','y'],
			FLSC_Call(
				FLSC_Var('*'),[
					FLSC_Var('x'),
					FLSC_Var('y')
			])
		),
		FLSC_List([
			FLSC_Num(1),
			FLSC_Num(2),
			FLSC_Num(3),
			FLSC_Num(4)
		]),
		// FLSC_Num(0.5)
])
)

x.asFLSC;
x.value(FLSC_Context.library);

FLSC_Interpreter("(let ((a 5)) (+ 3 a 4))").evaluate;
