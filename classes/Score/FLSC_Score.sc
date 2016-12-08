FLSC_Score {
	// le FLSC_Bus de sortie
	var <outBus;
	// le dictionnaire [SynthDef.name: SynthDef] des définitions employées
	var <defDict;
	// la liste des FLSC_Bus employés dans ce sous-graphe
	var <busList;
	// la liste des FLSC_MsgPair contenus dans ce support temporel
	var <bundle;
	// la liste des FLSC_Bundle des sous supports temporels
	var <bundleList;
	*new {|out, defs, busses, msgs, bundles|
		^super.new.scoreInit(out, defs, busses, msgs, bundles);
	}

	scoreInit {|out, defs, busses, msgs, bundles|
		outBus = out;
		defDict = defs;
		busList = busses;
		bundle = msgs;
		bundleList = bundles;
		^this;
	}
}