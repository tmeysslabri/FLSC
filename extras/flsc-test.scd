// exemples de base
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

// exemple de lecture interactive de répertoire (extras/examples/tutorial)
FLSC_Interpreter().playDir(interactive: true);

// créer le catalogue (extras/examples/catalog) dans recordings (recordings/catalog)
// BROKEN
FLSC_Interpreter().recordDir;

// exemple de lecture d'un paquetage
FLSC_Interpreter().loadPackage("packages/testpkg.flsc").read("(???)").evaluate;
