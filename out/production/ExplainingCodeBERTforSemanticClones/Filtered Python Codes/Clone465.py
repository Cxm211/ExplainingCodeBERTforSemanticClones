def find_neighbors(tess, points) :
	neighbors = {}
	for point in range(points.shape [0]) :
		neighbors [point] = []
	for simplex in tess.simplices :
		neighbors [simplex [0]] += [simplex [1], simplex [2]]
		neighbors [simplex [1]] += [simplex [2], simplex [0]]
		neighbors [simplex [2]] += [simplex [0], simplex [1]]
	return neighbors


def find_neighbors(tess) :
	neighbors = defaultdict(set)
	for simplex in tess.simplices :
		for idx in simplex :
			other = set(simplex)
			other.remove(idx)
			neighbors [idx] = neighbors [idx].union(other)
	return neighbors

