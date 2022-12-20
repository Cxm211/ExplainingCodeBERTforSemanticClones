def powerset(items) :
	combo = []
	for r in range(len(items) + 1) :
		combo.append(list(combinations(items, r)))
	return combo


def powerset(seq) :
	if len(seq) < = 1 :
		yield seq
		yield []
	else :
		for item in powerset(seq [1 :]) :
			yield [seq [0]] + item
			yield item

