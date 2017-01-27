FLSC_SemanticNode : FLSC_Object {
	// description du type
	classvar <typeDesc;

	// la valeur que représente le noeud: nombre, symbole, sous-expression
	var <nodeVal;
	// les lignes de début et de fin
	var <start, <end;

	*initClass {
		typeDesc = "Expression";
	}

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
			{error.isKindOf(FLSC_FatalError)}
			{error.throw}
			{error.isKindOf(FLSC_LocError)}
			{error.throw}
			{error.isKindOf(FLSC_Error)}
			{
				if(start.notNil && end.notNil)
				{FLSC_LocError(error.errorString, start, end).throw}
				{error.throw}
			}
			{true}
			{error.reportError; FLSC_FatalError("Non-FLSC error: see above").throw}
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
