def merge_dicts(d1, d2) :
	elems = set(d1.items()) | set(d2.items())
	res = {}
	for k, v in elems :
		if k in res.keys() :
			return dict()
		res [k] = v;
	return res


def merge_dicts(d1, d2) :
	merged = d1.copy()
	for k, v in d2.iteritems() :
		if k in merged :
			raise ValueError
		else :
			merged [k] = v
	return merged

