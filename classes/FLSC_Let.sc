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

	value {|context|
		^FLSC_Call(FLSC_Lambda(nameList, nodeVal), valList).value(context);
	}

	asFLSC {
		var flopped = [nameList, valList].flop;
		var letList;
		if (flopped.notEmpty) {
			letList = flopped.collect {|item| "(" ++ item[0].asString +
				item[1].asFLSC ++ ")"};
			letList = letList[0] ++ letList[1..].inject("") {|acc, item| acc + item};
		}
		{ letList = "" };
		^("(let (" ++ letList ++ ") " ++ nodeVal.asFLSC ++ ")");
	}
}