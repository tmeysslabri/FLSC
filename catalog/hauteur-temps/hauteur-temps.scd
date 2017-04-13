[
	// paquetages requis
	[
		"sintrap.flscpkg"
	],
	// description des expressions
	[
		[
			// sélecteur
			\sintrap,
			// listes d'options
			[
				// fréquence
				(40 * (2 ** (0, 0.5..8))).trunc,
				// durée
				(0.01 * (2 **(0, 0.5..9))).trunc(0.001)
			],
			// préfixes
			[
				"freq",
				"dur"
			]
		]
	]
]
