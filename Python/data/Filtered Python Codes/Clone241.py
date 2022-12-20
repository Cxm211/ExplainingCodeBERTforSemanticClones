def flatten(seq) :
	l = []
	for elt in seq :
		t = type(elt)
		if t is tuple or t is list :
			for elt2 in flatten(elt) :
				l.append(elt2)
		else :
			l.append(elt)
	return l


def flatten(x) :
	if isinstance(x, collections.Iterable) :
		return [a for i in x for a in flatten(i)]
	else :
		return [x]

