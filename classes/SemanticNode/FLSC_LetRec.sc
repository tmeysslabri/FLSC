FLSC_LetRec : FLSC_Let {
	letBindings {|context|
		var res = super.letBindings(context);
		res.do {|item|
			var bind = item[1];
			if(bind.isKindOf(FLSC_Function)) {bind.addContext(res)}};
		^res;
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
		^("(letrec (" ++ letList ++ ") " ++ nodeVal.asFLSC ++ ")");
	}
}