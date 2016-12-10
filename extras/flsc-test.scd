// exemple de fonction récursive (la factorielle)
(
FLSC_Interpreter(
	"(letrec ((fact (lambda (n)" +
	"                 (if (gt? n 1)" +
	"                     (* n (fact (- n 1)))" +
	"                     1))))" +
	"  (fact 6))"
).evaluate.treeValue;
)

// exemples de synthèse sonore

// application de patch
(
x = FLSC_Interpreter(
	"((patch (freq) [0.5 2]" +
	"   (m* (seq [1] [(mseg 0 0.1) (mseg 0.1 0)])" +
	"     (mosc freq 0))) 220)"
).evaluate.asFLSCScore.play;
)

// dédoublement d'un patch avec let
(
x = FLSC_Interpreter(
	"(let ((myvar (patch (freq) [0.5 2]" +
	"                (m* (seq [1] [(mseg 0 0.1) (mseg 0.1 0)])" +
	"                  (mosc freq 0)))))" +
	"  [(delay 0 (myvar 220)) (delay 1 (myvar 330))])"
).evaluate.asFLSCScore.play;
)

// dédoublement d'une application de patch avec let
(
x = FLSC_Interpreter(
	"(let ((myvar ((patch (freq) [0.5 2]" +
	"                (m* (seq [1] [(mseg 0 0.1) (mseg 0.1 0)])" +
	"                  (mosc freq 0))) 220)))" +
	"  [(delay 0 myvar) (delay 1 myvar)])"
).evaluate.asFLSCScore.play;
)

// dédoublement d'un module
(
x = FLSC_Interpreter(
	"((patch (freq) [0.5 1 1.5 2 3]" +
	"   (let ((myvar " +
	"           (m* (seq [1] [(mseg 0 0.1) (mseg 0.1 0)])" +
	"             (mosc freq 0))))" +
	"     (par [[0 4] [2 5]] [myvar myvar]))) 220)"
).evaluate.asFLSCScore.play;
)

// somme de partiels
(
x = FLSC_Interpreter(
	"(let ((dyn (seq [1] [(mseg 0 0.5) (mseg 0.5 0)])))" +
	"  ((patch (freq) [0.5 2]" +
	"    (mapcar (lambda (i)" +
	"              (m* (/ 0.1 i) (m* dyn (mosc (* i freq) 0))))" +
	"      (range 1 8))) 110))"
).evaluate.asFLSCScore.play;
)
