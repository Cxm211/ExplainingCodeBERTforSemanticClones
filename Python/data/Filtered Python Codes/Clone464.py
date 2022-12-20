def find_neighbors(pindex, triang) :
	neighbors = list()
	for simplex in triang.vertices :
		if pindex in simplex :
			neighbors.extend([simplex [i] for i in range(len(simplex)) if simplex [i] ! = pindex])
			'''
			this is a one liner for if a simplex contains the point we`re interested in,
			extend the neighbors list by appending all the *other* point indices in the simplex
			'''
	return list(set(neighbors))


def find_neighbors(tess) :
	neighbors = defaultdict(set)
	for simplex in tess.simplices :
		for idx in simplex :
			other = set(simplex)
			other.remove(idx)
			neighbors [idx] = neighbors [idx].union(other)
	return neighbors

