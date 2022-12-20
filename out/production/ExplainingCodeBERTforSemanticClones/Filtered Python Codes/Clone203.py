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
	if lst [0] < lst [1] :
		return minimum(lst [0 : 1] + lst [2 :])
	else :
		return minimum(lst [1 :])

