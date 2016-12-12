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
		^nodeVal.value(FLSC_Context(context, this.letBindings(context), true));
	}

	letBindings {|context|
		var args = valList.collect(_.value(context));
		^[nameList, args].flop;
	}

/*
	value {|context|
		^FLSC_Call(FLSC_Lambda(nameList, nodeVal), valList).value(context);
	}
*/
	asFLSC {
		var letList;
		if (nameList.notEmpty) {
			letList = [nameList, valList].flop;
			letList = letList.collect {|item| "(" ++ item[0].asString +
				item[1].asFLSC ++ ")"};
			letList = letList[1..].inject(letList[0]) {|acc, item| acc + item};
		}
		{ letList = "" };
		^("(let (" ++ letList ++ ") " ++ nodeVal.asFLSC ++ ")");
	}
}