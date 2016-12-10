[
	// modules arithmétiques
	['m+', "(module (a b) (+ a b))"],
	['m-', "(module (a b) (- a b))"],
	['m*', "(module (a b) (* a b))"],
	['m/', "(module (a b) (/ a b))"],

	// modules générateurs de signaux
	['mosc', "(module (freq phi) (osc freq phi))"],
	['mseg', "(module (start end) (seg start end))"],
	// env ne peut pas être transformé en module
	// parce que levels et times sont des tableaux de taille indéfinie !!!
	// ['menv', "(module (levels times) (env levels times))"],
	['mlfo', "(module (freq phi) (lfo freq phi))"],
	['mtri', "(module (freq phi) (tri freq phi))"],
	['msqu', "(module (freq phi width) (squ freq phi width))"],
	['mrand', "(module (min max) (rand min max))"],
	['mcrand', "(module (min max) (crand min max))"],
]