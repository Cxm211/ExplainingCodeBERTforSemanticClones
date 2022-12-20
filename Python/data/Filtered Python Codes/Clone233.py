def flatten(l) :
	for el in l :
		if isinstance(el, collections.Iterable) and not isinstance(el, basestring) :
			for sub in flatten(el) :
				yield sub
		else :
			yield el


def flatten(xs) :
	res = []
	def loop(ys) :
		for i in ys :
			if isinstance(i, list) :
				loop(i)
			else :
				res.append(i)
	loop(xs)
	return res

