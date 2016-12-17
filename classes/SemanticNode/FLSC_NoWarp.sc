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

	value {|context|
		var val = super.value(context);
		^if(val.isFLSCScoreSpec)
		{
			var varList = List().addAll(val.varList);
			noWarpList.do {|item| varList = varList.union(item.varList)};
			FLSC_SplitSpec(val.rate, val.varList, val, noWarpList)}
		{val}
	}
}