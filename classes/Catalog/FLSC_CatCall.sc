FLSC_CatCall : FLSC_Catalog {
	var <subs, prefixes;

	*new {|expr|
		^super.new.catCallInit(expr);
	}

	catCallInit {|expr|
		flscString = expr[0];
		subs = expr[1].collect(_.collect {|e| FLSC_Catalog.newFrom(e)});
		prefixes = expr[2] ? [];
	}

	asPathExprPairList {
		// pour chaque liste de choix multiples
		^subs.collect {|list, i|
			var prefix = prefixes[i] ? "";
			list.inject([])
			// concatener les résultats des appels récursifs
			{|acc, it| acc ++ it.asPathExprPairList}
			// ajouter le préfixe
			.collect{|e| [prefix ++ e[0], e[1]]}
		}
			// effectuer le produit cartésien des listes obtenues
		.allTuples.collect
		// itérer sur les tuples
		{|item|
			// transformer l'élément en [liste de noms, liste d'expressions]
			item = item.flop;
			// concatener les noms (avec séparateur) pour obtenir le chemin
			[item[0].inject(flscString, _+/+_),
				// composer l'appel
				"(" ++ flscString ++ item[1].inject("") {|a, i| a++" "++i} ++ ")"];
		}
	}
}