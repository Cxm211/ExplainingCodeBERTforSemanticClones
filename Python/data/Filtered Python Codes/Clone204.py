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


def minimum(lst) :
	if len(lst) == 1 :
		return lst [0]
	if lst [0] < lst [1] :
		return minimum(lst [0 : 1] + lst [2 :])
	else :
		return minimum(lst [1 :])

