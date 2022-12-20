def merge_dicts(d1, d2) :
	elems = set(d1.items()) | set(d2.items())
	res = {}
	for k, v in elems :
		if k in res.keys() :
			return dict()
		res [k] = v;
	return res


def merge_dicts(d1, d2) :
	try :
		intersection = d1.viewkeys() & d2
	except AttributeError :
		intersection = d1.keys() & d2
	if any(d1 [shared] ! = d2 [shared] for shared in intersection) :
		return {}
	return dict(d1, ** d2)

