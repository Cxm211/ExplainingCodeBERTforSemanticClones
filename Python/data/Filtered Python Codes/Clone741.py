def pairsum_n(list1, value) :
	set1 = set(list1)
	if list1.count(value / 2) < 2 :
		set1.remove(value / 2)
	return set((min(x, value - x), max(x, value - x)) for x in filterfalse(lambda x : (value - x) not in set1, set1))


def pairsum_n(list1, value) :
	result = set()
	for n in combinations(list1, 2) :
		if sum(n) == value :
			result.add((min(n), max(n)))
	return list(result)

