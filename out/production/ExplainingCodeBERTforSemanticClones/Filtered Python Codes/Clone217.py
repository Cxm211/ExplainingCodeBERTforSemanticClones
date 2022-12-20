def is_rotation(a1, a2) :
	if len(a1) ! = len(a2) :
		return False
	double_array = a1 + a1
	return check_sublist(double_array, a2)


def is_rotation(a, b) :
	for n in range(len(a)) :
		c = c = a [- n :] + a [: - n]
		if b == c :
			return True
	return False

