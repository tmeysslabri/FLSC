FLSC_LetStar : FLSC_Let {
	letBindings {|context|
		var list = [nameList, valList].flop;
		^list.inject([],
			{|res, item| res ++ [[item[0], item[1].value(FLSC_Context(context, res))]]});
	}

	asFLSC {
		var letList;
		if (nameList.notEmpty) {
			letList = [nameList, valList].flop;
			letList = letList.collect {|item| "(" ++ item[0].asString +
				item[1].asFLSC ++ ")"};
			letList = letList[1..].inject(letList[0]) {|acc, item| acc + item};
		}
		{ letList = "" };
		^("(let* (" ++ letList ++ ") " ++ nodeVal.asFLSC ++ ")");
	}
}