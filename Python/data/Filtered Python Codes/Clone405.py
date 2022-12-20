def divisors(integer) :
	arr = []
	for x in range(2, integer - 1) :
		if integer % x == 0 :
			arr.append(x)


def divisors(integer) :
	arr = []
	for x in range(2, round(integer ** 0.5)) :
		if integer % x == 0 :
			arr.append(x)
	if len(arr) == 0 :
		return str(integer) + ' is prime'
	else :
		return arr

