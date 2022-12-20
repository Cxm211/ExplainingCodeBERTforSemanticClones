def find_nearest(array, values) :
	array = np.asarray(array)
	values = np.expand_dims(values, axis = - 1)
	indices = np.abs(array - values).argmin(axis = - 1)
	return array [indices]


def find_nearest(array, value) :
	idx = np.searchsorted(array, value, side = "left")
	if idx > 0 and (idx == len(array) or math.fabs(value - array [idx - 1]) < math.fabs(value - array [idx])) :
		return array [idx - 1]
	else :
		return array [idx]

