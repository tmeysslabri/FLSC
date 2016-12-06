
(
FLSC_Interpreter("
	(+ 2 3)
").evaluate;
)

// et en plus, il chante !!! (avec un peu de bricolage)
// exemple de module
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
(
var timeWarp = {|t| #[0, 0.5, 2][t]};
z = Synth(y.def.name, ['out', 0] ++ y.args.collect({|item|
	item.value(timeWarp);
}).asKeyValuePairs);
)
z.free;

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

// uGenGraph.postln;

x = FLSC_ModFunc(uGenGraph.varList, parmNames,
			{|varDict| uGenGraph.value(varDict)}, uGenGraph.timeControls);
)

y = x.value([]);
y.def.add;
z = Synth(y.def.name, ['out', 0]);
z.free;


