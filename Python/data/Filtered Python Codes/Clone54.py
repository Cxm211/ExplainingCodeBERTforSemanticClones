def isDecent(n) :
	digits = list(map(int, str(n)))
	for digit in digits :
		if digit ! = 3 and digit ! = 5 : return False
	return True


def isDecent(n) :
	if n == 0 :
		return False
	while n :
		if n % 10 not in (3, 5,) :
			return False
		n //= 10
	return True

