// exemples de base
FLSC_Interpreter().read("(+ 1 2)").evaluate;
FLSC_Interpreter().read("((lambda () 42))").evaluate;
// génération aléatoire
FLSC_Interpreter().read("(random)").evaluate;
// erreur de syntaxe
FLSC_Interpreter().read(")").evaluateAndPlay;


// exemple de fonction récursive (la factorielle)
(
FLSC_Interpreter().read(
	"(letrec ((fact (lambda (n)" +
	"                 (if (gt? n 1)" +
	"                     (* n (fact (- n 1)))" +
	"                     1))))" +
	"  (fact 6))"
).evaluate;
)

// exemples de synthèse sonore

// création d'un module
FLSC_Interpreter().read("(control? (m* 1 1))").evaluate;

// routine de test automatique
// les exemples individuels sont donnés plus bas
(
var interpreter = FLSC_Interpreter();
var programs = [
	// application de patch
	"((patch (freq) [0.5 2]\n" +
	"   (m* (seq [1] [(mseg 0 0.1) (mseg 0.1 0)])\n" +
	"     (mosc freq 0))) 220)",
	// dédoublement d'un patch avec let
	"(let ((myvar (patch (freq) [0.5 2]\n" +
	"                (m* (seq [1] [(mseg 0 0.1) (mseg 0.1 0)])\n" +
	"                  (mosc freq 0)))))\n" +
	"  [(delay 0 (myvar 220)) (delay 1 (myvar 330))])",
	// dédoublement d'une application de patch avec let
	"(let ((myvar ((patch (freq) [0.5 2]\n" +
	"                (m* (seq [1] [(mseg 0 0.1) (mseg 0.1 0)])\n" +
	"                  (mosc freq 0))) 220)))\n" +
	"  [(delay 0 myvar) (delay 1 myvar)])",
	// dédoublement d'un module
	"((patch (freq) [0.5 1 2 2.5 3]\n" +
	"   (let ((myvar \n" +
	"           (m* (seq [1] [(mseg 0 0.1) (mseg 0.1 0)])\n" +
	"             (mosc freq 0))))\n" +
	"     (par [[0 3] [2 5]]\n" +
	"          [(subsign [1] myvar)\n" +
	"           (subsign [2] myvar)]))) 220)",
	// dédoublement d'une liste
	"(let* ((synth (patch (freq) [0.5 2]\n" +
	"                (m* (seq [1] [(mseg 0 0.1) (mseg 0.1 0)])\n" +
	"                  (mosc freq 0))))\n" +
	"       (lst [(delay 0 (synth 220)) (delay 1 (synth 330))]))\n" +
	"  [lst (delay 2 lst)])",
	// somme de partiels
	"(let ((dyn (seq [1] [(mseg 0 0.5) (mseg 0.5 0)])))\n" +
	"  ((patch (freq) [0.5 2]\n" +
	"    (mapcar (lambda (i)\n" +
	"              (m* (/ 0.1 i) (m* dyn (mosc (* i freq) 0))))\n" +
	"      (range 1 8))) 110))",
	// sinusoïde bruitée
	"((patch (freq) [0.5 2]\n" +
	"   (m* (seq [1] [(mseg 0 0.1) (mseg 0.1 0)])\n" +
	"     (mosc (m* (mrand 1.1 0.9) freq) 0))) 220)",
	// modulateur carré
	// noter les paramètres absents (phase, largeur d'impulsion)
	"((patch (freq modfreq) [0.5 1.5 2]\n" +
	"   (m* (m- 1 (m* (menv [0 1 1 0]) (msqu modfreq)))\n" +
	"     (m* (menv [0 0.1 0.1 0])\n" +
	"       (mosc freq))))" +
	" 220 64)",
	// générateur d'enveloppe (! à éviter)
	"((patch (freq) [0.5 2]\n" +
	"   (m* (lmenv [0 0.1 0] [1])\n" +
	"     (mosc freq 0))) 220)",
	// enveloppe à nombre de points variable
	"((patch (freq) [0.5 2]\n" +
	"   (m* (menv [0 0.1 0])\n" +
	"     (mosc freq 0))) 220)",
	// itération avec calcul sur le temps
	// (on entend des battements, est-ce normal ?)
	"(let ((synth (patch (freq) [0.5 2]\n" +
	"               (m* (menv [0 0.1 0])\n" +
	"                 (mosc freq 0))))\n" +
	"      (freq (lambda (t) (* 100 (** 2 (/ t 4))))))\n" +
	"  (mapcar\n" +
	"    (lambda (i) (let ((num (+ i (random))))\n" +
	"                  (delay num (synth (freq num)))))\n" +
	"    (range 0 8)))",
	// itération, forme standard
	"((patch (num) (* 0.5 (range 1 (+ 3 num)))\n" +
	"   (apply par\n" +
	"     (flop\n" +
	"       (mapcar\n" +
	"         (lambda (i)\n" +
	"           [[i (+ 3 i)]\n" +
	"            (m* (subsign [1] (menv [0 0.1 0]))\n" +
	"                (mosc (* 100 (** 2 (/ i 4))) 0))])\n" +
	"         (range 0 (- num 1)))))) 9)",
	// enveloppe HF
	"((patch (freq) [0.5 1 1.004 1.008 2]\n" +
	"   (m* (mhfenv [0 0.1 0.1 0.2 0.1 0])\n" +
	"       (mosc freq 0)))\n" +
	" 220)",
	// composition hybride
	"((patch (freq) [0.5 1 1.004 1.008 2]\n" +
	"   (m* (hybrid [1 2 3 4]\n" +
	"          [(mseg 0 0.1) (mseg 0.1 0.1)\n" +
	"           (mhfseg 0.1 0.2) (mhfseg 0.2 0.1) (mseg 0.1 0)])\n" +
	"       (mosc freq 0)))\n" +
	" 220)",
	// variable non-unique
	// sans signalement
	"((patch (freq) [0.5 2]\n" +
	"   (let ((myvar (m* (menv [0 0.1 0]) (mosc freq 0))))\n" +
	"     [myvar (m* (menv [0 0.1 0]) (mosc (m* (m+ 1 (m* 40 myvar)) freq) 0))]))\n" +
	" 220)",
	// avec signalement, plus économique
	"((patch (freq) [0.5 2]\n" +
	"   (let ((!myvar (m* (menv [0 0.1 0]) (mosc freq 0))))\n" +
	"     [myvar (m* (menv [0 0.1 0]) (mosc (m* (m+ 1 (m* 40 myvar)) freq) 0))]))\n" +
	" 220)",
	// contrôle non-unique
	"(let ((dual (module (!shape)\n" +
	"              (* 0.1 shape (osc (* 220 (+ 1 shape)) 0)))))\n" +
	"  ((patch (shape) [0.5 2]\n" +
	"     (dual shape))\n" +
	"   (menv [0 1 0])))",
];

Routine({
	Server.default.bootSync;
	programs.do {|string|
		Server.default.sync;
		"Executing:".postln; string.postln;
		interpreter.read(string).evaluateAndPlay;
		if(interpreter.scoreValue.notNil)
		{(interpreter.scoreValue.end + 1).wait};
	};
	"DONE".postln;
}).play;
)

// application de patch
(
FLSC_Interpreter().read(
	"((patch (freq) [0.5 2]" +
	"   (m* (seq [1] [(mseg 0 0.1) (mseg 0.1 0)])" +
	"     (mosc freq 0))) 220)"
).evaluateAndPlay;
)

// dédoublement d'un patch avec let
(
FLSC_Interpreter().read(
	"(let ((myvar (patch (freq) [0.5 2]" +
	"                (m* (seq [1] [(mseg 0 0.1) (mseg 0.1 0)])" +
	"                  (mosc freq 0)))))" +
	"  [(delay 0 (myvar 220)) (delay 1 (myvar 330))])"
).evaluateAndPlay;
)

// dédoublement d'une application de patch avec let
(
FLSC_Interpreter().read(
	"(let ((myvar ((patch (freq) [0.5 2]" +
	"                (m* (seq [1] [(mseg 0 0.1) (mseg 0.1 0)])" +
	"                  (mosc freq 0))) 220)))" +
	"  [(delay 0 myvar) (delay 1 myvar)])"
).evaluateAndPlay;
)

// dédoublement d'un module
(
FLSC_Interpreter().read(
	"((patch (freq) [0.5 1 2 2.5 3]" +
	"   (let ((myvar " +
	"           (m* (seq [1] [(mseg 0 0.1) (mseg 0.1 0)])" +
	"             (mosc freq 0))))" +
	"     (par [[0 3] [2 5]]" +
	"          [(subsign [1] myvar)" +
	"           (subsign [2] myvar)]))) 220)"
).evaluateAndPlay;
)

// dédoublement d'une liste
(
FLSC_Interpreter().read(
	"(let* ((synth (patch (freq) [0.5 2]" +
	"                (m* (seq [1] [(mseg 0 0.1) (mseg 0.1 0)])" +
	"                  (mosc freq 0))))" +
	"       (lst [(delay 0 (synth 220)) (delay 1 (synth 330))]))" +
	"  [lst (delay 2 lst)])"
).evaluateAndPlay;
)

// somme de partiels
(
FLSC_Interpreter().read(
	"(let ((dyn (seq [1] [(mseg 0 0.5) (mseg 0.5 0)])))" +
	"  ((patch (freq) [0.5 2]" +
	"    (mapcar (lambda (i)" +
	"              (m* (/ 0.1 i) (m* dyn (mosc (* i freq) 0))))" +
	"      (range 1 8))) 110))"
).evaluateAndPlay;
)

// sinusoïde bruitée
(
FLSC_Interpreter().read(
	"((patch (freq) [0.5 2]" +
	"   (m* (seq [1] [(mseg 0 0.1) (mseg 0.1 0)])" +
	"     (mosc (m* (m+ (m* 0.2 (mrand)) 0.9) freq) 0))) 220)"
).evaluateAndPlay;
)

// idem avec mrand sans paramètres (moins économique)
(
FLSC_Interpreter().read(
	"((patch (freq) [0.5 2]" +
	"   (m* (seq [1] [(mseg 0 0.1) (mseg 0.1 0)])" +
	"     (mosc (m* (m+ (m* 0.2 (mrand)) 0.9) freq) 0))) 220)"
).evaluateAndPlay;
)

// modulateur carré
// noter les paramètres absents (phase, largeur d'impulsion)
(
FLSC_Interpreter().read(
	"((patch (freq modfreq) [0.5 1.5 2]" +
	"   (m* (m- 1 (m* (menv [0 1 1 0]) (msqu modfreq)))" +
	"     (m* (menv [0 0.1 0.1 0])" +
	"       (mosc freq))))" +
	" 220 64)"
).evaluateAndPlay;
)

// générateur d'enveloppe (! à éviter)
(
FLSC_Interpreter().read(
	"((patch (freq) [0.5 2]" +
	"   (m* (lmenv [0 0.1 0] [1])" +
	"     (mosc freq 0))) 220)"
).evaluateAndPlay;
)

// enveloppe à nombre de points variable
(
FLSC_Interpreter().read(
	"((patch (freq) [0.5 2]" +
	"   (m* (menv [0 0.1 0])" +
	"     (mosc freq 0))) 220)"
).evaluateAndPlay;
)

// itération avec calcul sur le temps
// (on entend des battements, est-ce normal ?)
(
FLSC_Interpreter().read(
	"(let ((synth (patch (freq) [0.5 2]" +
	"               (m* (menv [0 0.1 0])" +
	"                 (mosc freq 0))))" +
	"      (freq (lambda (t) (* 100 (** 2 (/ t 4))))))" +
	"  (mapcar" +
	"    (lambda (i) (let ((num (+ i (random))))" +
	"                  (delay num (synth (freq num)))))" +
	"    (range 0 8)))"
).evaluateAndPlay;
)

// itération, forme standard
// sur une gamme à 8 tons égaux
(
FLSC_Interpreter().read(
	"((patch (num) (* 0.25 (range 1 (+ 3 num)))\n" +
	"   (apply par\n" +
	"     (flop\n" +
	"       (mapcar\n" +
	"         (lambda (i)\n" +
	"           [[i (+ 3 i)]\n" +
	"            (m* (subsign [1] (menv [0 0.1 0]))\n" +
	"                (mosc (* 200 (** 2 (/ i 8))) 0))])\n" +
	"         (range 0 (- num 1)))))) 17)"
).evaluateAndPlay;
)

// enveloppe HF
(
FLSC_Interpreter().read(
	"((patch (freq) [0.5 1 1.004 1.008 2]" +
	"   (m* (mhfenv [0 0.1 0.1 0.2 0.1 0])" +
	"       (mosc freq 0)))" +
	" 220)"
).evaluateAndPlay
)

// composition hybride
// (même résultat, mais plus efficace)
(
FLSC_Interpreter().read(
	"((patch (freq) [0.5 1 1.004 1.008 2]" +
	"   (m* (hybrid [1 2 3 4]" +
	"          [(mseg 0 0.1) (mseg 0.1 0.1)" +
	"           (mhfseg 0.1 0.2) (mhfseg 0.2 0.1) (mseg 0.1 0)])" +
	"       (mosc freq 0)))" +
	" 220)"
).evaluateAndPlay
)

// variable non-unique
// sans signalement
(
FLSC_Interpreter().read(
	"((patch (freq) [0.5 2]" +
	"   (let ((myvar (m* (menv [0 0.1 0]) (mosc freq 0))))" +
	"     [myvar (m* (menv [0 0.1 0]) (mosc (m* (m+ 1 (m* 40 myvar)) freq) 0))]))" +
	" 220)"
).evaluateAndPlay;
)
// avec signalement, plus économique
(
FLSC_Interpreter().read(
	"((patch (freq) [0.5 2]" +
	"   (let ((!myvar (m* (menv [0 0.1 0]) (mosc freq 0))))" +
	"     [myvar (m* (menv [0 0.1 0]) (mosc (m* (m+ 1 (m* 40 myvar)) freq) 0))]))" +
	" 220)"
).evaluateAndPlay;
)

// contrôle non-unique
// pas de bug, il est possible d'appeler plusieurs fois un même Symbol.ir
(
FLSC_Interpreter().read(
	"((patch (shape1) [0.5 2]" +
	"   ((module (shape2) (* 0.1 shape2 (osc (* 220 (+ 1 shape2)) 0))) shape1))" +
	" (menv [0 1 0]))"
).evaluateAndPlay;
)

(
FLSC_Interpreter().read(
	"(let ((dual (module (!shape) (* 0.1 shape (osc (* 220 (+ 1 shape)) 0)))))\n" +
	"  ((patch (shape) [0.5 2]\n" +
	"     (dual shape))\n" +
	"   (menv [0 1 0])))",
).evaluateAndPlay;
)

// exemple d'enregistrement
(
FLSC_Interpreter().read(
	"((patch (num) (* 0.25 (range 1 (+ 3 num)))\n" +
	"   (apply par\n" +
	"     (flop\n" +
	"       (mapcar\n" +
	"         (lambda (i)\n" +
	"           [[i (+ 3 i)]\n" +
	"            (m* (subsign [1] (menv [0 0.1 0]))\n" +
	"                (mosc (* 200 (** 2 (/ i 8))) 0))])\n" +
	"         (range 0 (- num 1)))))) 17)"
).evaluateAndRecord;
)
