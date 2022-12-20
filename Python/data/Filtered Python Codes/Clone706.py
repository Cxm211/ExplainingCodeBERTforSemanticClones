def product(* args, ** kwds) :
	pools = map(tuple, args) * kwds.get('repeat', 1)
	result = [[]]
	for pool in pools :
		result = [x + [y] for x in result for y in pool]
	for prod in result :
		yield tuple(prod)


def product(ar_list) :
	if not ar_list :
		yield ()
	else :
		for a in ar_list [0] :
			for prod in product(ar_list [1 :]) :
				yield (a,) + prod

