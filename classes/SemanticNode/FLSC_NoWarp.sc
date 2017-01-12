FLSC_NoWarp : FLSC_Let {
	// la liste des ScoreSpec sans distorsion
	var noWarpList;

	*new {|list, body|
		^super.new(list, body).noWarpInit;
	}

	noWarpInit {
		noWarpList = List();
	}

	letBindings {|context|
		^super.letBindings(context).collect {|pair|
			var val = pair[1];
			[pair[0],
				if(val.isFLSCScoreSpec) {noWarpList.add(val); FLSC_NoWarpSpec(val)} {val}
			]
		};
	}

	semanticValue {|context|
		// la valeur doit être une FLSC_ScoreSpec,
		// sinon on ne peut pas l'encapsuler et obtenir la résolution par le noWarpDict
		var val = super.value(context).asFLSCScoreSpec;
		// on créée la varList dans l'environnement du nowarp
		// elle inclut les variables référencées dans noWarpList
		var varList = List().addAll(val.varList);
		noWarpList.do {|item| varList = varList.union(item.varList)};
		// le résultat est une FLSC_SplitSpec
		^FLSC_SplitSpec(val.rate, val.varList, val, noWarpList);
	}
}
