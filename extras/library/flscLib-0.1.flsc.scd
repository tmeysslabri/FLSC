[
	[
		// identité
		['id', "(lambda (o) o)"],
		// incrémentation et décrémentation
		['++', "(lambda (num) (+ num 1))"],
		['--', "(lambda (num) (- num 1))"],
		// inverse
		['//', "(lambda (num) (/ 1 num))"],
		// générateurs de séquences
		['range0', "(lambda (num) (range 0 (- num 1)))"],
		['range1', "(lambda (num) (range 1 num))"],
		// fonctions de base sur les listes
		['car', "(lambda (list) (elt list 0))"],
		['cdr', "(lambda (list) (subseq list 1 (length list)))"],
		['last', "(lambda (list) (elt list (- (length list) 1)))"],
		['start', "(lambda (list) (subseq list 0 (- (length list) 1)))"],
		// accès au n-ième élément
		['e0', "(lambda (list) (elt list 0))"],
		['e1', "(lambda (list) (elt list 1))"],
		['e2', "(lambda (list) (elt list 2))"],
		['e3', "(lambda (list) (elt list 3))"],
		['e4', "(lambda (list) (elt list 4))"],

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
		['mmel2hz', "(module (a) (mel2hz a))"],
		['mhz2mel', "(module (a) (hz2mel a))"],
		['msin', "(module (a) (sin a))"],
		['mcos', "(module (a) (cos a))"],
		['mtan', "(module (a) (tan a))"],
		['mlog2', "(module (a) (log2 a))"],
		['mfloor', "(module (a) (floor a))"],
		['mceil', "(module (a) (ceil a))"],
		['mround', "(module (a) (round a))"],
		['mmin', "(module (a b) (min a b))"],
		['mmax', "(module (a b) (max a b))"],
		['mclip', "(module (val lo hi) (clip val lo hi))"],
		['mfold', "(module (val lo hi) (fold val lo hi))"],
		['mwrap', "(module (val lo hi) (wrap val lo hi))"],

		// modules générateurs de signaux:
		// valeur nulle
		['m0', "(module () (const 0))"],
		['mc', "(module (num) (const num))"],
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
		// parce que levels est tableau de taille indéfinie
		// on peut utiliser une fonction qui génère un module
		// mais c'est sous-optimal (un module créé par appel !)
		['lmenv', "(lambda (levels) ((module () (env levels))))"],

		// DEBOGUAGE
		['pfunc',
			"(lambda (expr func)" +
			"  (post expr \"%\" (func expr)))"
		],
	], [
		// générateurs de séquence (suite)
		['step',
			"(lambda (num step start)" +
			"  (+ (if (? start) start 0)" +
			"    (* (if (? step) step 1)" +
			"      (range0 num))))"
		],
		// fonctions sur le support / la signature
		['basemap',
			"(lambda (bases sigs)" +
			"    (mapcar (lambda (sub sig) (base sub sig))" +
			"      bases sigs))"
		],
		// FONCTIONS SUR LES MODULES
		// somme, produit, min et max itérés
		['mn+', "(lambda (& terms) (reduce m+ terms))"],
		['mn*', "(lambda (& terms) (reduce m* terms))"],
		['mnmin', "(lambda (& terms) (reduce mmin terms))"],
		['mnmax', "(lambda (& terms) (reduce mmax terms))"],
		// la fonction menv produit une enveloppe de taille variable
		// en assemblant des mseg avec seq
		['menv',
			"(lambda (levels)" +
			"  (let ((len (length levels)))" +
			"    (seq" +
			"      (mapcar (lambda (start end) (mseg start end))" +
			"        (subseq levels 0 (- len 1)) (subseq levels 1 len)))))"
		],
		// idem avec des segments HF
		['mhfenv',
			"(lambda (levels)" +
			"  (let ((len (length levels)))" +
			"    (seq" +
			"      (mapcar (lambda (start end) (mhfseg start end))" +
			"        (subseq levels 0 (- len 1)) (subseq levels 1 len)))))"
		],
		// la composition hybride permet de faire une composition séquentielle
		// sur des signaux de type contrôle ou audio
		['hybrid',
			"(lambda (sigs)" +
			"  (seq (mapcar" +
			"         (lambda (sig) (if (audio? sig) sig (mk2a sig)))" +
			"            sigs)))"
		],
	], [
		// enveloppe hybride, composée de segments contrôle ou audio
		['mhybenv',
			"(lambda (levels types)" +
			"  (let ((len (length levels)))" +
			"    (hybrid" +
			"      (mapcar (lambda (start end type)" +
			"        (if type (mhfseg start end)" +
			"                 (mseg start end)))" +
			"        (subseq levels 0 (- len 1))" +
			"        (subseq levels 1 len)" +
			"        types))))"
		],
	],
]
