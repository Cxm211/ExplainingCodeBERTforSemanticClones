def square(x = None) :
	try :
		return float(x) ** 2
	except TypeError :
		print "You did not enter a real number"
		return None


def square(x = None) :
	if not isinstance(x, numbers.Number) or isinstance(x, numbers.Complex) :
		print ("you have not entered x")
	else :
		y = x ** 2
		return y

