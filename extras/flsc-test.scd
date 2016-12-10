// exemple de fonction récursive (la factorielle)
(
FLSC_Interpreter("
	(letrec ((fact (lambda (n)
	                 (if (gt? n 1)
	                     (* n (fact (- n 1)))
	                     1))))
	  (fact 6))
").evaluate;
)

// exemples de synthèse sonore

// application de patch
(
x = FLSC_Interpreter(
	"((patch (freq) [0.5 2]" +
	"   (m* (seq [1] [(mseg 0 0.5) (mseg 0.5 0)])" +
	"     (mosc freq 0))) 220)"
);
)

// dédoublement d'un patch avec let
(
x = FLSC_Interpreter(
	"(let ((myvar (patch (freq) [0.5 2]" +
	"                (m* (seq [1] [(mseg 0 0.5) (mseg 0.5 0)])" +
	"                  (mosc freq 0)))))" +
	"  [(delay 0 (myvar 220)) (delay 1 (myvar 330))])"
);
)

// dédoublement d'une application de patch avec let
(
x = FLSC_Interpreter(
	"(let ((myvar ((patch (freq) [0.5 2]" +
	"                (m* (seq [1] [(mseg 0 0.5) (mseg 0.5 0)])" +
	"                  (mosc freq 0))) 220)))" +
	"  [(delay 0 myvar) (delay 1 myvar)])"
);
)

// dédoublement d'un module
(
x = FLSC_Interpreter(
	"((patch (freq) [0.5 1 1.5 2 3]" +
	"   (let ((myvar " +
	"           (m* (seq [1] [(mseg 0 0.5) (mseg 0.5 0)])" +
	"             (mosc freq 0))))" +
	"     (par [[0 4] [2 5]] [myvar myvar]))) 220)"
);
)

// somme de partiels
(
x = FLSC_Interpreter(
	"(let ((dyn (seq [1] [(mseg 0 0.5) (mseg 0.5 0)])))" +
	"  ((patch (freq) [0.5 2]" +
	"    (mapcar (lambda (i)" +
	"              (m* (/ 1 i) (m* dyn (mosc (* i freq) 0))))" +
	"      (range 1 8))) 110))"
);
)

x.evaluate;
x.asFLSCScore;
x.play;

// exemple de calcul d'un FLSC_Score
(
var res;
var scoreSpec = FLSC_Interpreter(
	"(let ((myvar ((patch (freq) [0 0.5 2]" +
	"                (m* (seq [1] [(mseg 0 0.5) (mseg 0.5 0)])" +
	"                  (mosc freq 0))) 220)))" +
	"  [(delay 0 myvar) (delay 1 myvar)])"
).evaluate;
// il est nécessaire d'encapsuler dans une FLSC_WarpSpec neutre
// dans le but de résoudre le contexte actuel (varList, bundle)
// scoreSpec = FLSC_SignSpec(scoreSpec.rate, FLSC_WarpSpec({|t| t}, scoreSpec));
res = scoreSpec.asFLSCScoreSpec.value(0, {|t| t}, Dictionary()).postln;
res.outBus.postln;
res.defDict.postln;
res.busList.postln;
res.bundle.postln;
res.bundleList.postln;
"THE END :-)";
)

// exemple d'appel à des modules arithmétiques produisant un simple nombre
(
var synth;
var bus = Bus.control;
var test = FLSC_Interpreter(
	"(m+ 1 3)"
).evaluate.postln;
test.rate.postln;
test.def.name.postln;
Routine {
	test.def.add;
	bus.set(0);
	s.sync;
	synth = Synth(test.def.name, ['out', bus] ++ test.args.asKeyValuePairs);
	1.wait;
	bus.getSynchronous.postln;
	synth.free;
}.play;
)

// exemple d'appel à un patch
(
x = FLSC_Interpreter("
	((patch (freq) [0 0.5 2] (m* (seq [1] [(mseg 0 0.5) (mseg 0.5 0)]) (mosc freq 0))) 220)
").evaluate;
)
// pour l'instant il n'est pas possible de récupérer l'information
// pour jouer l'objet audionumérique résultant

// exemple de delay sur une liste récursive (à 2 niveaux et 1 élément)
(
x = FLSC_Interpreter(
	"(delay 0.2" +
	"  [[((patch (freq) [0 0.5 2]" +
	"    (m* (seq [1] [(mseg 0 0.5) (mseg 0.5 0)])" +
	"      (mosc freq 0))) 220)]])"
).evaluate;
)

// exemple de FLSC_VarSpec
(
x = FLSC_Interpreter(
	"(let ((myvar ((patch (freq) [0 0.5 2]" +
	"                (m* (seq [1] [(mseg 0 0.5) (mseg 0.5 0)])" +
	"                  (mosc freq 0))) 220)))" +
	"  myvar)"
).evaluate;
)

x.varList;
x.rate;

// et en plus, il chante !!! (avec un peu de bricolage)

// exemples de module
(
y = FLSC_Interpreter("
	((module (freq) (osc freq 0)) 240)
").evaluate;
)

// les éléments de la ModSpec permettent effectivement d'obtenir un Synth
y.def.add;
z = Synth(y.def.name, ['out', 0] ++ y.args.asKeyValuePairs);
z.free;

// idem avec une enveloppe
(
y = FLSC_Interpreter("
	((module (freq) (* (env [0 0.5 0] [0 1 2]) (osc freq 0))) 240)
").evaluate;
)

y.def.add;
// il faut effectuer artificiellement la distorsion temporelle
(
var timeWarp = {|t| #[0, 0.5, 2][t]};
z = Synth(y.def.name, ['out', 0] ++ y.args.collect({|item|
	item.value(timeWarp);
}).asKeyValuePairs);
)
z.free;

// idem avec un modulateur carré
// à noter que le troisième paramètre de largeur d'impulsion est optionnel
(
y = FLSC_Interpreter("
	((module (freq) (* (env [0 0.5 0] [0 1 2]) (osc freq 0) (squ 64 0 0.2))) 240)
").evaluate;
)
y.def.add;
(
var timeWarp = {|t| #[0, 0.5, 2][t]};
z = Synth(y.def.name, ['out', 0] ++ y.args.collect({|item|
	item.value(timeWarp);
}).asKeyValuePairs);
)
z.free;

// exemples plus anciens utilisant directement les primitives
// premier exemple de ModFunc, avec des paramètres complètement fabriqués
x = FLSC_ModFunc(List(), [], { SinOsc.ar(mul: 0.5) }, []);

y = x.value([]);

y.def.add;
z = Synth(y.def.name, ['out', 0]);
z.free;

// exemple de FLSC_UGen
x = FLSC_UGen.newFromClass([220, 0], SinOsc, ['freq', 'phase'], minRate: 'audio');

y = x.value(IdentityDictionary());

y.rate;

{ x.value(IdentityDictionary()) }.play;

// exemple de FLSC_ModFunc avec FLSC_UGen en argument
(
// fonction sans paramètres
var parmNames = [];
var uGenGraph = FLSC_UGen.newFromClass([220, 0], SinOsc, ['freq', 'phase'], minRate: 'audio');
x = FLSC_ModFunc(uGenGraph.varList, parmNames,
			{|varDict| uGenGraph.value(varDict)}, uGenGraph.timeControls);
)

y = x.value([]);
y.def.add;
z = Synth(y.def.name, ['out', 0]);
z.free;

// même chose avec des contrôles
(
var parmNames = ['frequency'];
var baseContext = FLSC_Context(nil, [['frequency', FLSC_Control('frequency')]]);
var uGenGraph = FLSC_UGen.newFromClass([FLSC_Var('frequency').value(baseContext), 0],
	SinOsc, ['freq', 'phase'], minRate: 'audio');
x = FLSC_ModFunc(uGenGraph.varList, parmNames,
			{|varDict| uGenGraph.value(varDict)}, uGenGraph.timeControls);
)

// les deux appels produisent la même SynthDef, avec des arguments différents
y = x.value([330]);
y = x.value([260]);
y.def.add;
z = Synth(y.def.name, ['out', 0] ++ y.args.asKeyValuePairs);
z.free;

// exemple de UGenFunc
(
var parmNames = [];
// on appelle '+' sur deux oscillateurs
var uGenGraph = FLSC_Call(FLSC_Var('+'), [
	{FLSC_UGen.newFromClass([220, 0], SinOsc, ['freq', 'phase'], minRate: 'audio')},
	{FLSC_UGen.newFromClass([330, 0], SinOsc, ['freq', 'phase'], minRate: 'audio')}
]).value(FLSC_Context.library);

x = FLSC_ModFunc(uGenGraph.varList, parmNames,
			{|varDict| uGenGraph.value(varDict)}, uGenGraph.timeControls);
)

y = x.value([]);
y.def.add;
z = Synth(y.def.name, ['out', 0]);
z.free;


