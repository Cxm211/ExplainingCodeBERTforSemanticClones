def is_rotation(a1, a2) :
	if len(a1) ! = len(a2) :
		return False
	double_array = a1 + a1
	return check_sublist(double_array, a2)


def is_rotation(a, b) :
	if len(a) == len(b) :
		da = deque(a)
		db = deque(b)
		for offset in range(len(a)) :
			if da == db :
				return True
			da.rotate(1)
	return False

