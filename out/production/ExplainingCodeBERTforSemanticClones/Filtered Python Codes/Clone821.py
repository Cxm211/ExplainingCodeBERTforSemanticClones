def numPens(n) :
	if n < 0 :
		return False
	if n == 0 :
		return True
	for x in (24, 8, 5) :
		if numPens(n - x) :
			return True
	return False


def numPens(n) :
	if n < 5 :
		return False
	if n % 8 == 0 or n % 5 == 0 :
		return True
	else :
		return numPens(n - 8) or numPens(n - 5)

