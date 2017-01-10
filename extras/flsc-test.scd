// exemples de base
FLSC_Interpreter().asFLSC;
FLSC_Interpreter().read("(+ 1 2)").evaluate;
FLSC_Interpreter().read("((lambda () 42))").evaluate;
// génération aléatoire
FLSC_Interpreter().read("(random)").evaluate;
// erreur de syntaxe
FLSC_Interpreter().read(")").play;


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

// exemples de lecture de fichier
FLSC_Interpreter().readFile.evaluate;
FLSC_Interpreter().readFile("examples/tutorial/07-sous-signature.flsc").play;

// exemple de lecture de répertoire (extras/examples/tutorial)
FLSC_Interpreter().playDir;

// créer le catalogue (extras/examples/catalog) dans recordings (recordings/catalog)
FLSC_Interpreter().recordDir;

// exemple de lecture d'un paquetage
FLSC_Interpreter().loadPackage("packages/testpkg.flsc").read("(???)").evaluate;

// création d'un module
FLSC_Interpreter().read("(control? (mn* 1 2 3))").evaluate;

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
	"   (mn* (m- 1 (m* (menv [0 1 1 0]) (msqu modfreq)))\n" +
	"     (menv [0 0.1 0.1 0])\n" +
	"     (mosc freq)))\n" +
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
	// idem avec composition hybride
	"((patch (freq) [0.5 1 1.004 1.008 2]\n" +
	"   (m* (hybrid [1 2 3 4]\n" +
	"          [(mseg 0 0.1) (mseg 0.1 0.1)\n" +
	"           (mhfseg 0.1 0.2) (mhfseg 0.2 0.1) (mseg 0.1 0)])\n" +
	"       (mosc freq 0)))\n" +
	" 220)",
	// idem avec enveloppe hybride
	"((patch (freq) [0.5 1 1.004 1.008 2]\n" +
	"   (m* (mhybenv [0 0.1 0.1 0.2 0.1 0]\n" +
	"         [fl fl tr tr fl])\n" +
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
	// exemple de nowarp (distribution de signal)
	"((patch (freq) [1 2 3 4]\n" +
	"   (nowarp ((freq (mseg freq (* 4 freq))))\n" +
	"     (let ((sound (lambda (pitch)\n" +
	"                    (m* (menv [0 0.1 0]) (mosc (m* pitch freq))))))\n" +
	"       (par [[0 2] [1 3] [2 4]]\n" +
	"         [(sound 1) (sound 0.75) (sound 0.5)])))) 220)",
	// itération avec signsel
	"((patch (freq dt num) (+ (repeat [0 0.5 2] num) (* dt (range 0 (- num 1))))\n" +
	"   (mapcar (lambda (i)\n" +
	"             (signsel i\n" +
	"               (m* (mosc (* freq (+ 1 (* 0.3333 i)))) (menv [0 0.1 0]))))\n" +
	"     (range 0 (- num 1))))\n" +
	" 120 0.5 10)",
	// idem avec withsigns
	"((patch (freq dt num) (+ (repeat [0 0.5 2] num) (* dt (range 0 (- num 1))))\n" +
	"   (withsigns\n" +
	"     (mapcar (lambda (i)\n" +
	"                 (m* (mosc (* freq (+ 1 (* 0.3333 i)))) (menv [0 0.1 0])))\n" +
	"       (range0 num))))\n" +
	" 120 0.5 10)",
	// combinaison nowarp/signsel
	"((patch (freq dt num) (+ (repeat [0 0.2 2] num) (* dt (range 0 (- num 1))))\n" +
	"   (nowarp ((freq (mseg freq (* 4 freq))))\n" +
	"     (mapcar (lambda (i)\n" +
	"               (signsel i\n" +
	"                 (m* (mosc (m* freq (elt [1 0.75 0.5] (mod i 3))))\n" +
	"                   (menv [0 0.1 0]))))\n" +
	"       (range 0 (- num 1)))))\n" +
	" 120 0.5 10)",
];

var play = {
	var next = programs.first;
	"Executing:\n%".format(next).postln;
	interpreter.read(next).play({
		programs = programs[1..];
		if(programs.notEmpty) {play.value} {"Done.".postln};
	})
};

play.value;
)

// application de patch
(
FLSC_Interpreter().read(
	"((patch (freq) [0.5 2]" +
	"   (m* (seq [1] [(mseg 0 0.1) (mseg 0.1 0)])" +
	"     (mosc freq 0))) 220)"
).play;
)

// dédoublement d'un patch avec let
(
FLSC_Interpreter().read(
	"(let ((myvar (patch (freq) [0.5 2]" +
	"                (m* (seq [1] [(mseg 0 0.1) (mseg 0.1 0)])" +
	"                  (mosc freq 0)))))" +
	"  [(delay 0 (myvar 220)) (delay 1 (myvar 330))])"
).play;
)

// dédoublement d'une application de patch avec let
(
FLSC_Interpreter().read(
	"(let ((myvar ((patch (freq) [0.5 2]" +
	"                (m* (seq [1] [(mseg 0 0.1) (mseg 0.1 0)])" +
	"                  (mosc freq 0))) 220)))" +
	"  [(delay 0 myvar) (delay 1 myvar)])"
).play;
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
).play;
)

// dédoublement d'une liste
(
FLSC_Interpreter().read(
	"(let* ((synth (patch (freq) [0.5 2]" +
	"                (m* (seq [1] [(mseg 0 0.1) (mseg 0.1 0)])" +
	"                  (mosc freq 0))))" +
	"       (lst [(delay 0 (synth 220)) (delay 1 (synth 330))]))" +
	"  [lst (delay 2 lst)])"
).play;
)

// somme de partiels
(
FLSC_Interpreter().read(
	"(let ((dyn (seq [1] [(mseg 0 0.5) (mseg 0.5 0)])))" +
	"  ((patch (freq) [0.5 2]" +
	"    (mapcar (lambda (i)" +
	"              (m* (/ 0.1 i) (m* dyn (mosc (* i freq) 0))))" +
	"      (range 1 8))) 110))"
).play;
)

// sinusoïde bruitée
(
FLSC_Interpreter().read(
	"((patch (freq) [0.5 2]" +
	"   (m* (seq [1] [(mseg 0 0.1) (mseg 0.1 0)])" +
	"     (mosc (m* (m+ (m* 0.2 (mrand)) 0.9) freq) 0))) 220)"
).play;
)

// idem avec mrand sans paramètres (moins économique)
(
FLSC_Interpreter().read(
	"((patch (freq) [0.5 2]" +
	"   (m* (seq [1] [(mseg 0 0.1) (mseg 0.1 0)])" +
	"     (mosc (m* (m+ (m* 0.2 (mrand)) 0.9) freq) 0))) 220)"
).play;
)

// modulateur carré
// noter les paramètres absents (phase, largeur d'impulsion)
(
FLSC_Interpreter().read(
	"((patch (freq modfreq) [0.5 1.5 2]" +
	"   (mn* (m- 1 (m* (menv [0 1 1 0]) (msqu modfreq)))" +
	"     (menv [0 0.1 0.1 0])" +
	"     (mosc freq)))" +
	" 220 64)"
).play;
)

// générateur d'enveloppe (! à éviter)
(
FLSC_Interpreter().read(
	"((patch (freq) [0.5 2]" +
	"   (m* (lmenv [0 0.1 0] [1])" +
	"     (mosc freq 0))) 220)"
).play;
)

// enveloppe à nombre de points variable
(
FLSC_Interpreter().read(
	"((patch (freq) [0.5 2]" +
	"   (m* (menv [0 0.1 0])" +
	"     (mosc freq 0))) 220)"
).play;
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
).play;
)

// itération, forme standard
// sur une gamme à 8 tons égaux
(
FLSC_Interpreter().read(
	"((patch (num) (* 0.25 (range 1 (+ 3 num)))" +
	"   (apply par" +
	"     (flop" +
	"       (mapcar" +
	"         (lambda (i)" +
	"           [[i (+ 3 i)]" +
	"            (m* (subsign [1] (menv [0 0.1 0]))" +
	"                (mosc (* 200 (** 2 (/ i 8))) 0))])" +
	"         (range 0 (- num 1)))))) 17)"
).play;
)

// enveloppe HF
(
FLSC_Interpreter().read(
	"((patch (freq) [0.5 1 1.004 1.008 2]" +
	"   (m* (mhfenv [0 0.1 0.1 0.2 0.1 0])" +
	"       (mosc freq 0)))" +
	" 220)"
).play
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
).play
)

// idem avec enveloppe hybride
(
FLSC_Interpreter().read(
	"((patch (freq) [0.5 1 1.004 1.008 2]" +
	"   (m* (mhybenv [0 0.1 0.1 0.2 0.1 0]" +
	"         [fl fl tr tr fl])" +
	"       (mosc freq 0)))" +
	" 220)"
).play
)

// variable non-unique
// sans signalement
(
FLSC_Interpreter().read(
	"((patch (freq) [0.5 2]" +
	"   (let ((myvar (m* (menv [0 0.1 0]) (mosc freq 0))))" +
	"     [myvar (m* (menv [0 0.1 0]) (mosc (m* (m+ 1 (m* 40 myvar)) freq) 0))]))" +
	" 220)"
).play;
)
// avec signalement, plus économique
(
FLSC_Interpreter().read(
	"((patch (freq) [0.5 2]" +
	"   (let ((!myvar (m* (menv [0 0.1 0]) (mosc freq 0))))" +
	"     [myvar (m* (menv [0 0.1 0]) (mosc (m* (m+ 1 (m* 40 myvar)) freq) 0))]))" +
	" 220)"
).play;
)

// contrôle non-unique
// pas de bug, il est possible d'appeler plusieurs fois un même Symbol.ir
(
FLSC_Interpreter().read(
	"((patch (shape) [0.5 2]" +
	"   ((module (shape) (* 0.1 shape (osc (* 220 (+ 1 shape)) 0))) shape))" +
	" (menv [0 1 0]))"
).play;
)

(
FLSC_Interpreter().read(
	"(let ((dual (module (!shape) (* 0.1 shape (osc (* 220 (+ 1 shape)) 0)))))" +
	"  ((patch (shape) [0.5 2]" +
	"     (dual shape))" +
	"   (menv [0 1 0])))",
).play;
)

// exemple de nowarp (distribution de signal)
(
FLSC_Interpreter().read(
	"((patch (freq) [1 2 3 4]" +
	"   (nowarp ((freq (mseg freq (* 4 freq))))" +
	"     (let ((sound (lambda (pitch)" +
	"                    (m* (menv [0 0.1 0]) (mosc (m* pitch freq))))))" +
	"       (par [[0 2] [1 3] [2 4]]" +
	"         [(sound 1) (sound 0.75) (sound 0.5)])))) 220)"
).play;
)

// itération avec signsel
(
FLSC_Interpreter().read(
	"((patch (freq dt num) (+ (repeat [0 0.5 2] num) (* dt (range 0 (- num 1))))" +
	"   (mapcar (lambda (i)" +
	"             (signsel i" +
	"               (m* (mosc (* freq (+ 1 (* 0.3333 i)))) (menv [0 0.1 0]))))" +
	"     (range 0 (- num 1))))" +
	" 120 0.5 10)"
).play;
)

// idem avec withsigns
(
FLSC_Interpreter().read(
	"((patch (freq dt num) (+ (repeat [0 0.5 2] num) (* dt (range 0 (- num 1))))" +
	"   (withsigns" +
	"     (mapcar (lambda (i)" +
	"                 (m* (mosc (* freq (+ 1 (* 0.3333 i)))) (menv [0 0.1 0])))" +
	"       (range0 num))))" +
	" 120 0.5 10)"
).play;
)

// combinaison nowarp/signsel
(
FLSC_Interpreter().read(
	"((patch (freq dt num) (+ (repeat [0 0.2 2] num) (* dt (range 0 (- num 1))))" +
	"   (nowarp ((freq (mseg freq (* 4 freq))))" +
	"     (mapcar (lambda (i)" +
	"               (signsel i" +
	"                 (m* (mosc (m* freq (elt [1 0.75 0.5] (mod i 3))))" +
	"                   (menv [0 0.1 0]))))" +
	"       (range 0 (- num 1)))))" +
	" 120 0.5 10)"
).play;
)

// gabarit à copier-coller
(
FLSC_Interpreter().read(
	""
).play;
)

// exemple d'enregistrement
(
FLSC_Interpreter().read(
	"((patch (num) (* 0.25 (range 1 (+ 3 num)))" +
	"   (apply par" +
	"     (flop" +
	"       (mapcar" +
	"         (lambda (i)" +
	"           [[i (+ 3 i)]" +
	"            (m* (subsign [1] (menv [0 0.1 0]))" +
	"                (mosc (* 200 (** 2 (/ i 8))) 0))])" +
	"         (range 0 (- num 1)))))) 17)"
).recordNRT(nil, 1, 1);
)
