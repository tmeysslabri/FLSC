FLSC_SemanticNode {
	// la valeur que représente le noeud: nombre, symbole, sous-expression
	var <nodeVal;

	*new {|val|
		^super.new.nodeInit(val);
	}

	nodeInit {|val|
		nodeVal = val;
		^this;
	}

	value {|context|
		^nodeVal.value;
	}

	asFLSC {
		^nodeVal.asString;
	}
}