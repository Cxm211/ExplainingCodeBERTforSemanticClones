def frange(a, b, stp = 1.0) :
	i = a + stp / 2.0
	while i < b :
		yield a
		a += stp
		i += stp


def frange(start, stop = None, step = 1) :
	if stop is None :
		for x in _xrange(int(ceil(start))) :
			yield x
	else :
		indices = (i for i in _xrange(0, int((stop - start) / step)))
		for i in indices :
			yield start + step * i

