+ Array {
	encapsulate {
		^this.collect {|value|
			if(value.isFLSCUGen || value.isFLSCScoreSpec || value.isArray)
			{ value.encapsulate }
			{ value }
		}
	}
}