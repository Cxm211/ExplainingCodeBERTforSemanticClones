def find_subclasses(cls) :
	results = []
	for sc in cls.__subclasses__() :
		for obj in gc.get_objects() :
			if isinstance(obj, sc) :
				results.append(obj)
	return results


def find_subclasses(cls) :
	all_refs = gc.get_referrers(cls)
	results = []
	for obj in all_refs :
		if (isinstance(obj, tuple) and
		getattr(obj [0], "__mro__", None) is obj) :
			results.append(obj [0])
	return results

