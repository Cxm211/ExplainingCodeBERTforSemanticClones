def Compare(left, ops, comparators) :
	for x, op, y in zip([left] + comparators [: - 1], ops, comparators) :
		if not op(x, y) :
			return False
	return True


def Compare(left, ops, comparators) :
	if not ops [0](left, comparators [0]) :
		return False
	for i, comparator in enumerate(comparators [1 :], start = 1) :
		if not ops [i](comparators [i - 1], comparator) :
			return False
	return True

