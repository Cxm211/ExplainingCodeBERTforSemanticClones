def minimum(lst, current_min = None) :
	if not lst :
		return current_min
	if current_min is None :
		current_min = lst [0]
	elif lst [0] < current_min :
		current_min = lst [0]
	return minimum(lst [1 :], current_min)


def minimum(lst) :
	if len(lst) == 1 :
		return lst [0]
	first = lst [0]
	rest = lst [1 :]
	min_of_rest = minimum(rest)
	if first < min_of_rest :
		return first
	else :
		return min_of_rest

