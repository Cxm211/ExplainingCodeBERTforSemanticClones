def square(x = None) :
	try :
		return float(x) ** 2
	except TypeError :
		print "You did not enter a real number"
		return None


def square(x = None) :
	if x is None :
		print "you have not entered x"
	else :
		y = x ** 2
		return y

