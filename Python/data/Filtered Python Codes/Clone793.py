def square(x) :
	try :
		y = [e ** 2 for e in x]
	except TypeError :
		y = x ** 2
	return y


def square(x) :
	if isinstance(x, int) :
		return x ** 2
	elif isinstance(x, list) :
		return [square(b) for b in x]
	else :
		raise ValueError("Only ints and list of ints/list of ints allowed")

