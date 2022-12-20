def flatten(container) :
	for i in container :
		if isinstance(i, (list, tuple)) :
			for j in flatten(i) :
				yield j
		else :
			yield i


def flatten(items, seqtypes = (list, tuple)) :
	for i, x in enumerate(items) :
		while i < len(items) and isinstance(items [i], seqtypes) :
			items [i : i + 1] = items [i]
	return items

