FLSC_SemanticNode {
	// la valeur que représente le noeud: nombre, symbole, sous-expression
	var <nodeVal;
	// les lignes de début et de fin
	var <start, <end;

	*new {|val|
		^super.new.nodeInit(val);
	}

	nodeInit {|val|
		nodeVal = val;
		^this;
	}

	value {|context, library, baseDir|
		var res;
		try {
			res = this.semanticValue(context, library, baseDir);
		} {|error|
			case
			{error.isKindOf(FLSC_LocError)}
			{error.throw}
			{error.isKindOf(FLSC_Error)}
			{
				if(start.notNil && end.notNil)
				{FLSC_LocError(error.errorString, start, end).throw}
				{error.throw}
			}
			{true}
			{error.reportError; Main.stop}
		}
		^res;
	}

	semanticValue {|context|
		^nodeVal.value;
	}

	setLines {|first, last|
		start = first;
		end = last;
	}

	asFLSC {
		^nodeVal.asString;
	}
}
