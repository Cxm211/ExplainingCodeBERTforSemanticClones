def factorial(n) :
	base = 1
	for i in range(n, 0, - 1) :
		base = base * i
	print base


def factorial(n) :
	result = 1
	i = n * (n - 1)
	while n > = 1 :
		result = result * n
		n = n - 1
	return result

