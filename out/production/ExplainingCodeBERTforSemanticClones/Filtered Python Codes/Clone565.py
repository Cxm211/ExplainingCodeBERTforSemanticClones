def brute_force() :
	for length in range(min_length, max_length + 1) :
		for p in product(chars, repeat = length) :
			guess = ''.join(p)
			if guess == password :
				return guess


def brute_force(string, length, goal) :
	if not length :
		if string == goal :
			return string
		return False
	for c in chars :
		s = brute_force(string + c, length - 1, goal)
		if s :
			return s
	return False

