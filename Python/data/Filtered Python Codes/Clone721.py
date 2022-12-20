def window(seq, n = 2) :
	"Returns a sliding window (of width n) over data from the iterable"
	"   s -> (s0,s1,...s[n-1]), (s1,s2,...,sn), ...                   "
	it = iter(seq)
	result = tuple(islice(it, n))
	if len(result) == n :
		yield result
	for elem in it :
		result = result [1 :] + (elem,)
		yield result


def window(iterable, n) :
	els = tee(iterable, n)
	for i, el in enumerate(els) :
		for _ in xrange(i) :
			next(el, None)
	return izip(* els)

