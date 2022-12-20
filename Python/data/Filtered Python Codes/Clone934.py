def unique(items) :
	seen = set()
	for i in xrange(len(items) - 1, - 1, - 1) :
		it = items [i]
		if it in seen :
			del items [i]
		else :
			seen.add(it)


def unique(x) :
	output = []
	y = {}
	for item in x :
		y [item] = ""
	for item in x :
		if item in y :
			output.append(item)
	return output

