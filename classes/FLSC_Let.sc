FLSC_Let : FLSC_SemanticNode {
	// les associations: Array de Symbol, Array de FLSC_SemanticNode
	var nameList, valList;

	*new {|list, body|
		^super.new(body).letInit(list);
	}

	letInit {|list|
		var unflopped = list.flop;
		nameList = unflopped[0];
		valList = unflopped[1];
		^this;
	}

	asFLSC {
		var flopped = [nameList, valList].flop;
		var size = flopped.size;
		var letList = "";
		max(0, size-1).do({|i|
			letList = letList ++ "(" ++ flopped[i][0].asString ++
			" " ++ flopped[i][1].asFLSC ++ ") "
		});
		letList = letList ++ if(size > 0, {
			"(" ++ flopped[size-1][0].asString ++
			" " ++ flopped[size-1][1].asFLSC ++ ")"
		}, {""});
		^("(let (" ++ letList ++ ") " ++ nodeVal.asFLSC ++ ")");
	}
}