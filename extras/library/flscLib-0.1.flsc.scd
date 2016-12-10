[
	// MODULES
	[
		// modules arithmétiques
		['m+', "(module (a b) (+ a b))"],
		['m-', "(module (a b) (- a b))"],
		['m*', "(module (a b) (* a b))"],
		['m/', "(module (a b) (/ a b))"],

		// modules générateurs de signaux
		['mosc', "(module (freq phi) (osc freq phi))"],
		['mseg', "(module (start end) (seg start end))"],
		['mlfo', "(module (freq phi) (lfo freq phi))"],
		['mtri', "(module (freq phi) (tri freq phi))"],
		['msqu', "(module (freq phi width) (squ freq phi width))"],
		['mrand', "(module (min max) (rand min max))"],
		['mcrand', "(module (min max) (crand min max))"],

		// env ne peut pas être directement transformé en module
		// parce que levels et times sont des tableaux de taille indéfinie
		// on peut utiliser une fonction qui génère un module
		// mais c'est sous-optimal (un module créé par appel !)
		['lmenv', "(lambda (levels times) ((module () (env levels times))))"],
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
	],
]