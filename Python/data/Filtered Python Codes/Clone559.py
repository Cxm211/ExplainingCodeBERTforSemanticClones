def access(obj, indexes) :
	try :
		return reduce(list.__getitem__, indexes, obj)
	except Exception :
		return None


def access(obj, indexes) :
	a = obj
	for i in indexes :
		try :
			a = a [i]
		except IndexError :
			return None
	return a

