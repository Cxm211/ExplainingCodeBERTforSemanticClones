def is_rotation(a, b) :
	for n in range(len(a)) :
		c = c = a [- n :] + a [: - n]
		if b == c :
			return True
	return False


def is_rotation(a, b) :
	if len(a) == len(b) :
		da = deque(a)
		db = deque(b)
		for offset in range(len(a)) :
			if da == db :
				return True
			da.rotate(1)
	return False

