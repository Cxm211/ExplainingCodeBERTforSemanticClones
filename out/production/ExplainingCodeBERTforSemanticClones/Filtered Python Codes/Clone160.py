def findKmersSet(k, stuff) :
	result = []
	for inner_list in stuff :
		iterators = [iter(inner_list [i :]) for i in xrange(k)]
		result.append([''.join(triple) for triple in zip(* iterators)])
	return result


def findKmersSet(k, stuff) :
	for line in data :
		line_list = []
		for i in range(0, int(len(line) - k + 1)) :
			line_list.append(line [i : i + k])
		kmers.append(line_list)

