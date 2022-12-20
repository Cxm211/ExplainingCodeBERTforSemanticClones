def merge_dicts(d1, d2) :
	try :
		intersection = d1.viewkeys() & d2
	except AttributeError :
		intersection = d1.keys() & d2
	if any(d1 [shared] ! = d2 [shared] for shared in intersection) :
		return {}
	return dict(d1, ** d2)


def merge_dicts(d1, d2) :
	merged = d1.copy()
	for k, v in d2.iteritems() :
		if k in merged :
			raise ValueError
		else :
			merged [k] = v
	return merged

