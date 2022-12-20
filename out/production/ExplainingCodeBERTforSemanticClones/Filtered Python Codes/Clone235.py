def flatten(l) :
	for el in l :
		if isinstance(el, collections.Iterable) and not isinstance(el, basestring) :
			for sub in flatten(el) :
				yield sub
		else :
			yield el


def flatten(x) :
	if isinstance(x, collections.Iterable) :
		return [a for i in x for a in flatten(i)]
	else :
		return [x]

