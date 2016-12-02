
(
FLSC_Interpreter("
	((patch (x y)
	   [x y]
	   (+ x y))
	2 3)
").evaluate;
)

// et en plus, il chante !!! (avec un peu de bricolage)
x = FLSC_ModFunc(List(), [], { SinOsc.ar(mul: 0.5) }, []);

y = x.value([]);

y.def.add;
z = Synth(y.def.name);
z.free;