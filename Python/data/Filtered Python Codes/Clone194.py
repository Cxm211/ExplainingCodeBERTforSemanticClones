def seriesrun(x, n) :
	power = 0
	s = 0
	while power < n :
		s += (- x) ** power
		power += 1
	return s


def seriesrun(x, n) :
	result = 0
	term = 1
	for _ in range(n) :
		result += term
		term *= - x
	return result

