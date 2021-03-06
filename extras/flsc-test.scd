// exemples de base
FLSC_Interpreter().read("(+ 1 2)").evaluate;
FLSC_Interpreter().read("((lambda () 42))").evaluate;
// génération aléatoire
FLSC_Interpreter().read("(random)").evaluate;
// erreur de syntaxe
FLSC_Interpreter().read(")").play;

FLSC_Interpreter().read("dur").evaluate - 0.5;

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
FLSC_Interpreter().playDir();
// lecture interactive
FLSC_Interpreter().playDir(interactive: true);

// créer le catalogue (extras/examples/catalog) dans recordings (recordings/catalog)
FLSC_Interpreter().recordDir;
// idem avec le tutoriel
FLSC_Interpreter().recordDir("examples/tutorial");

// exemple de lecture d'un paquetage
FLSC_Interpreter().loadPackage("packages/testpkg.flsc").read("(???)").evaluate;

// échelle des mels
FLSC_Interpreter().read("(mel2hz 1000)").evaluate;
FLSC_Interpreter().read("(hz2mel 1000)").evaluate;
FLSC_Interpreter().read("(mel2hz (hz2mel (* 100 (range1 20))))").evaluate;
FLSC_Interpreter().read("(hz2mel (* 100 (range1 20)))").evaluate;
