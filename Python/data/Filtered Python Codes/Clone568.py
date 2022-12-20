def stemming(verb) :
	suffix = ["ing", "ed", "es", "s"]
	for i in suffix :
		verb = verb.replace(i, "")
	return verb


def stemming(verb) :
	suffixes = ["ing", "ed", "es", "s"]
	stem = verb
	for suffix in suffixes :
		if stem.endswith(suffix) :
			stem = stem [: - len(suffix)]
			break
	return stem

