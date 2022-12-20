def deep_reverse(a) :
	a.reverse()
	for i in a :
		if is_list(i) :
			deep_reverse(i)
			print a


def deep_reverse(lst) :
	try :
		if len(lst) > 1 :
			return list(deep_reverse(item) for item in reversed(lst))
		return lst
	except TypeError :
		return lst

