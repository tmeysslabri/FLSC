FLSC_AbstractUGen {
	isFLSCUGen { ^true }

	// méthode pour encapsuler au moyen d'une FLSC_VarUGen
	encapsulate { ^FLSC_VarUGen(this) }

	rate {^FLSC_Error("Not a signal: %".format(this)).throw}
}
