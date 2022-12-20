def find_nth(s, substr, n) :
	i = 0
	while n > = 0 :
		n -= 1
		i = s.find(substr, i + 1)
	return i


def find_nth(s, x, n, i = 0) :
	i = s.find(x, i)
	if n == 1 or i == - 1 :
		return i
	else :
		return find_nth(s, x, n - 1, i + len(x))

