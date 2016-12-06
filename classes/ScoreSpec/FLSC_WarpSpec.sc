FLSC_WarpSpec {
	// la distorsion temporelle à laquelle on fait référence
	var timeWarp;
	// la spécification à laquelle on fait référence
	var subSpec;
	// la liste des valeurs référencées dans ce contexte temporel
	// ??? ne s'agit-il pas tout simplement de subSpec.varList ?
	// var varList;

	*new {|warp, spec|
		^super.new.warpSpecInit(warp, spec);
	}

	warpSpecInit {|warp, spec|
		timeWarp = warp;
		subSpec = spec;
		^this;
	}
}
