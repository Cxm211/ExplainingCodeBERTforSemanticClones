def is_less(a, b) :
	i = 0
	while i < len(a) :
		if a [i] < b [i] :
			return True
		else :
			if a [i] > b [i] :
				return False
		i += 1
	return False


def is_less(a, b) :
	for i in range(len(a) - 1, - 1, - 1) :
		if a [i] < b [i] : return True
		elif a [i] > b [i] : return False
	return False

