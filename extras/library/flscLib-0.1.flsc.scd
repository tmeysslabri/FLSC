[
	[
		// incrémentation et décrémentation
		['++', "(lambda (num) (+ num 1))"],
		['--', "(lambda (num) (- num 1))"],
		// générateurs de séquences
		['range0', "(lambda (num) (range 0 (- num 1)))"],
		['range1', "(lambda (num) (range 1 num))"],

		// MODULES
		// modules arithmétiques:
		// opérateurs unaires
		['mneg', "(module (a) (neg a))"],
		['mabs', "(module (a) (abs a))"],
		['msgn', "(module (a) (sgn a))"],
		// opérateurs binaires
		['m+', "(module (a b) (+ a b))"],
		['m-', "(module (a b) (- a b))"],
		['m*', "(module (a b) (* a b))"],
		['m/', "(module (a b) (/ a b))"],
		['m**', "(module (a b) (** a b))"],
		['mmod', "(module (a b) (mod a b))"],
		// fonctions usuelles
		['msin', "(module (a) (sin a))"],
		['mcos', "(module (a) (cos a))"],
		['mtan', "(module (a) (tan a))"],
		['mlog2', "(module (a) (log2 a))"],
		['mfloor', "(module (a) (floor a))"],
		['mceil', "(module (a) (ceil a))"],
		['mround', "(module (a) (round a))"],
		['mmin', "(module (a b) (min a b))"],
		['mmax', "(module (a b) (max a b))"],

		// modules générateurs de signaux:
		// modules standard
		['mosc', "(module (freq phi) (osc freq phi))"],
		['mseg', "(module (start end) (seg start end))"],
		['mlfo', "(module (freq phi) (lfo freq phi))"],
		['mtri', "(module (freq phi) (tri freq phi))"],
		['msqu', "(module (freq phi width) (squ freq phi width))"],
		['mrand', "(module (max min) (rand max min))"],
		['mcrand', "(module (max min) (crand max min))"],
		// modules HF
		['mhfseg', "(module (start end) (hfseg start end))"],
		['mhftri', "(module (freq phi) (hftri freq phi))"],
		['mhfsqu', "(module (freq phi width) (hfsqu freq phi width))"],
		// modules de conversion
		['mk2a', "(module (sig) (k2a sig))"],

		// env ne peut pas être directement transformé en module
		// parce que levels et times sont des tableaux de taille indéfinie
		// on peut utiliser une fonction qui génère un module
		// mais c'est sous-optimal (un module créé par appel !)
		// ['lmenv', "(lambda (levels times) ((module () (env levels times))))"],
	],
	[
		// générateurs de séquence (suite)
		['step',
			"(lambda (num step start)" +
			"  (+ (if (? start) start 0)" +
			"    (* (if (? step) step 1)" +
			"      (range0 num))))"],
		/*
		// fonctions sur la signature temporelle
		['smap',
			"(lambda (sigs)" +
			"  (mapcar (lambda (i sig) (ssel i sig))" +
			"    (range0 (length sigs)) sigs))"
		],
		*/
		// FONCTIONS SUR LES MODULES
		// somme et produit itérés
		['mn+', "(lambda (& terms) (reduce m+ terms))"],
		['mn*', "(lambda (& terms) (reduce m* terms))"],
		/*
		// la fonction menv produit une enveloppe de taille variable
		// en assemblant des mseg avec seq
		// les indices sont sytématiquement les entiers successifs
		// (utiliser ssub pour sélectionner les points souhaités)
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
			"         (contents" +
			"           (flop" +
			"             (mapcar (lambda (time start end) [time (mhfseg start end)])" +
			"               (flop [(range 0 (- len 2)) (range 1 (- len 1))])" +
			"               (subseq levels 0 (- len 1)) (subseq levels 1 len)))))" +
			"    (apply par contents)))"
		],
		// la composition hybride permet de faire une composition séquentielle
		// sur des signaux de type contrôle ou audio
		['hybrid',
			"(lambda (breaks sigs)" +
			"  (let ((segs (flop [(cons 0 breaks) (add breaks end)]))" +
			"        (audio" +
			"          (mapcar" +
			"            (lambda (sig) (if (audio? sig) sig (mk2a sig)))" +
			"            sigs)))" +
			"    (par segs audio)))"
		],
		// enveloppe hybride, composée de segments contrôle ou audio
		['mhybenv',
			"(lambda (levels type)" +
			"  (let* ((len (length levels))" +
			"         (contents" +
			"           (flop" +
			"             (mapcar (lambda (i start end)" +
			"                       [[i (+ i 1)]" +
			"                        (if (elt type i)" +
			"                            (mhfseg start end)" +
			"                            (mk2a (mseg start end)))])" +
			"               (range0 (-- len))" +
			"               (subseq levels 0 (-- len))" +
			"               (subseq levels 1 len)))))" +
			"    (apply par contents)))"
		],
		*/
	],
]