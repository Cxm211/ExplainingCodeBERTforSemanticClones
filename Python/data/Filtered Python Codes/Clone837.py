def neclusters(l, K) :
	for splits in itertools.combinations(range(len(l) - 1), K - 1) :
		splits = [0] + [s + 1 for s in splits] + [None]
		yield [l [s : e] for s, e in zip(splits, splits [1 :])]


def neclusters(l, k) :
	for labels in itertools.product(range(k), repeat = len(l)) :
		partition = [[] for i in range(k)]
		for i, label in enumerate(labels) :
			partition [label].append(l [i])
		yield partition

