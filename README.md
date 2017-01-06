# FLSC
SuperCollider: Functional Language for Sound Composition

Auteur: Thomas Meyssonnier (<tmeyssonnier@u-bordeaux.fr>, LaBRI/CNRS/Université de Bordeaux)

Sous licence GPL v2 (en tant qu'extension de SuperCollider)

Extension SuperCollider pour l'écriture de partitions numériques en langage fonctionnel (type SCHEME ou LISP), selon les critères de la perception humaine.

Par 'partition numérique', on entend un ensemble de définitions de modules de synthèse (SynthDef) et un ensemble d'occurences de ces modules, exprimé sous la forme de messages de début et de fin (Score).

INSTALLATION:

- cloner le dépôt Git (https://github.com/tmeysslabri/FLSC) dans le répertoire d'extension de SuperCollider
- compiler le traducteur flsc2sc, en appelant 'make' dans extras/FLSC2SC/src/
[requiert gcc, et également flex et bison si les sources FLSC2SC.y et FLSC2SC.l ont changé]

Pour l'instant le logiciel est compatible GNU/Linux, mais peut-être également OSX et Win (tout écho à ce sujet est le bienvenu, je n'ai pas la possibilité de faire des tests moi-même).

UTILISATION:

L'interface utilisateur emploie la classe FLSC_Interpreter, en attente d'un fichier .schelp voir les exemples dans extras/flsc-test.scd. L'usage est relativement simple.

En ce qui concerne l'écriture de programmes en FLSC, se référer au manuel (extras/Manuel-FLSC.xhtml) et aux exemples (extras/examples/tests/*.flsc).

Merci pour tout retour au sujet de ce logiciel, qui est encore en phase expérimentale. Pour toute contribution éventuelle, merci de créer une branche distincte qui pourra être intégrée en temps utile, selon l'adéquation avec les objectifs du projet.
