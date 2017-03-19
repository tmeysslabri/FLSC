FLSC_Catalog {
	var flscString;

	*newFrom {|expr|
		case
		// terminal: symbole
		{expr.isString} {^FLSC_CatSymb(expr)}
		// terminal: nombre
		{expr.isNumber} {^FLSC_CatNum(expr)}
		// expression composite (appel de fonction)
		// [selecteur, [N listes de sous-expressions], ?[N préfixes]]
		{^FLSC_CatCall(expr)};
	}

	writeDir {|baseDir = "flsc-catalog"|
		this.asPathExprPairList.do
		{|it|
			var path = if(baseDir.notNil)
			{baseDir +/+ it[0] ++ ".flsc"}
			{it[0] ++ ".flsc"};
			var dir = path.dirname;
			var file;
			var old = "";
			// vérifier si il existe un fichier identique
			if (File.exists(path)) {
				file = File.open(path, "r");
				old = file.readAllString;
				file.close;
			};
			// si le fichier est inexistant ou différent, l'écrire
			if (old != it[1])
			{
				File.mkdir(dir);
				file = File.open(path, "w");
				file.putString(it[1]);
				file.close;
			}
		}
	}
}
