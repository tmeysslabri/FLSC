FLSC_AbstractUGen {
	// la liste des sous-graphes de FLSC_UGen
	var subGraphs;
	// la liste des variables référencées dans ce sous-graphe
	var <varList;
	// la liste de paires [UID, FLSC_Time] qui décrit les contrôles temporels dans ce sous-graphe
	var <timeControls;

	*new {|subs|
		^super.new.abstractUGenInit(subs);
	}

	abstractUGenInit {|subs|
		subGraphs = subs;
		varList = List();
		timeControls = List();
		subGraphs.do {|item|
			if(item.isFLSCUGen) {
				varList.addAll(item.varList);
				timeControls.addAll(item.timeControls);
			}
		};
		^this;
	}

	isFLSCUGen { ^true }
}