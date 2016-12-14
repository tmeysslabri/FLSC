[
	// MODULES
	[
		// modules arithmétiques
		['m+', "(module (!a !b) (+ a b))"],
		['m-', "(module (!a !b) (- a b))"],
		['m*', "(module (!a !b) (* a b))"],
		['m/', "(module (!a !b) (/ a b))"],

		// modules générateurs de signaux
		['mosc', "(module (!freq !phi) (osc freq phi))"],
		['mseg', "(module (!start !end) (seg start end))"],
		['mlfo', "(module (!freq !phi) (lfo freq phi))"],
		['mtri', "(module (!freq !phi) (tri freq phi))"],
		['msqu', "(module (!freq !phi !width) (squ freq phi width))"],
		['mrand', "(module (!min !max) (rand min max))"],
		['mcrand', "(module (!min !max) (crand min max))"],

		// modules HF
		['mhfseg', "(module (!start !end) (hfseg start end))"],
		['mhftri', "(module (!freq !phi) (hftri freq phi))"],
		['mhfsqu', "(module (!freq !phi !width) (hfsqu freq phi width))"],

		// modules de conversion
		['mk2a', "(module (!sig) (k2a sig))"],

		// env ne peut pas être directement transformé en module
		// parce que levels et times sont des tableaux de taille indéfinie
		// on peut utiliser une fonction qui génère un module
		// mais c'est sous-optimal (un module créé par appel !)
		['lmenv', "(lambda (!levels !times) ((module () (env levels times))))"],
	],
	// FONCTIONS SUR LES MODULES
	[
		// la fonction menv produit une enveloppe de taille variable
		// en assemblant des mseg avec seq
		// les indices sont sytématiquement les entiers successifs
		// (utiliser subsign pour sélectionner les points souhaités)
		['menv',
			"(lambda (levels)" +
			"  (let ((len (length levels)))" +
			"    (seq (range 1 (- len 2))" +
			"      (mapcar (lambda (start end) (mseg start end))" +
			"        (subseq levels 0 (- len 1)) (subseq levels 1 len)))))"],
		// idem avec des segments HF
		['mhfenv',
			"(lambda (levels)" +
			"  (let* ((len (length levels))" +
			"         (!contents" +
			"           (flop" +
			"             (mapcar (lambda (time start end) [time (mhfseg start end)])" +
			"               (flop [(range 0 (- len 2)) (range 1 (- len 1))])" +
			"               (subseq levels 0 (- len 1)) (subseq levels 1 len)))))" +
			"    (par (elt contents 0) (elt contents 1))))"
		],
		// la composition hybride permet de faire une composition séquentielle
		// sur des signaux de type contrôle ou audio
		['hybrid',
			"(lambda (breaks !sigs)" +
			"  (let ((segs (flop [(cons 0 breaks) (add breaks end)]))" +
			"        (!audio" +
			"          (mapcar" +
			"            (lambda (!sig) (if (audio? sig) sig (mk2a sig)))" +
			"            sigs)))" +
			"    (par segs audio)))"
		],
		/*
		['hybrid',
			"(lambda (breaks !sigs)" +
			"  (let* ((segs (flop [(cons 0 breaks) (add breaks end)]))" +
			"         (!contents" +
			"           (mapcar" +
			"             (lambda (!sig i)" +
			"               (cons (elt segs i)" +
			"                 (if (audio? sig) [sig (mseg 0 0)] [nil sig])))" +
			"             sigs (range 0 (- (length sigs) 1))))" +
			"         (!audio" +
			"           (subseq (flop" +
			"                     (select (lambda (!item) (? (elt item 1))) contents)) 0 2))" +
			"         (!control (elt (flop contents) 2)))" +
			"    (m+ (apply par audio) (seq breaks control))))"
		],
		*/
	],
]