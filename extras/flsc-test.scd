
(
FLSC_Interpreter("
	((patch (x y)
	   [x y]
	   (+ x y))
	2 3)
").evaluate;
)

// et en plus, il chante !!! (avec un peu de bricolage)
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
// il n'y a pas de paramètres pour le moment, puisque FLSC_Control n'est pas implémenté
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


