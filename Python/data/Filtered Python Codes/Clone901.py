def avg(self) :
	if all(isinstance(item, int) for item in self) :
		return sum(self) / len(self)
	else :
		raise ValueError('Invalid item in list. All items need to be an integer.')


def avg(self) :
	tot = 0
	for item in self :
		if isinstance(item, int) :
			tot += item
		else :
			print ('Invalid item in list. All items need to be an integer.')
	return tot / len(self)

