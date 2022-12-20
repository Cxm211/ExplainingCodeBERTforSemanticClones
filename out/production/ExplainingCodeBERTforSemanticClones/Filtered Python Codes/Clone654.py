def sum_even(a, b) :
	count = 0
	for i in range(a, b, 1) :
		if (i % 2 == 0) :
			count += i
	return count


def sum_even(a, b) :
	if ((a & 1) == 1) :
		a = a + 1
	if ((b & 1) == 1) :
		b = b - 1
	return ((a + b) / 2) * (1 + ((b - a) / 2))

