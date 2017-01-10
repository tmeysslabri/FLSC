+ Array {
	asFLSCScoreSpec {|global = false|
		if(global)
		{^FLSC_GlobalListSpec(this)}
		{^FLSC_LocalListSpec(this)};
	}
}

+ FLSC_ScoreSpec {
	asFLSCScoreSpec { ^this; }
}

+ Object {
	asFLSCScoreSpec {^Error("Not compliant to asFLSCScoreSpec: %".format(this)).throw}
}
