def is_sorted(lst) :
	try :
		sorted(lst, cmp = my_cmp)
		return True
	except ValueError :
		return False


def is_sorted(lst, key = lambda x : x) :
	for i, el in enumerate(lst [1 :]) :
		if key(el) < key(lst [i]) :
			return False
	return True

