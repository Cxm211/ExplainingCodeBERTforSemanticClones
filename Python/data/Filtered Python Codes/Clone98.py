def matcher(x) :
	l = [item for item in physical if item.lower() in x.lower()]
	length = len(l)
	if length == 0 :
		return 'other'
	elif length == 1 :
		return l [0]
	else :
		return 'mix'


def matcher(x) :
	val = None
	for p in physical :
		for y in x.split() :
			if p == y.lower() :
				if val is not None :
					return 'mix'
				val = p
		return val if val else 'other'

