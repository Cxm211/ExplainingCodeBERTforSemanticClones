def transitive_closure(a) :
	closure = set(a)
	while True :
		new_relations = set((x, w) for x, y in closure for q, w in closure if q == y)
		closure_until_now = closure | new_relations
		if closure_until_now == closure :
			break
		closure = closure_until_now
	return closure


def transitive_closure(elements) :
	edges = defaultdict(set)
	for x, y in elements : edges [x].add(y)
	for _ in range(len(elements) - 1) :
		edges = defaultdict(set, (
		(k, v.union(* (edges [i] for i in v))) for (k, v) in edges.items()
		))
	return set((k, i) for (k, v) in edges.items() for i in v)

