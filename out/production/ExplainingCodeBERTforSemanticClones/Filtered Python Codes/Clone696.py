def sublistExists(list, sublist) :
	for i in range(len(list) - len(sublist) + 1) :
		if sublist == list [i : i + len(sublist)] :
			return True
	return False


def sublistExists(x, y) :
	occ = [i for i, a in enumerate(x) if a == y [0]]
	for b in occ :
		if x [b : b + len(y)] == y :
			print 'YES-- SUBLIST at : ', b
			return True
		if len(occ) - 1 == occ.index(b) :
			print 'NO SUBLIST'
			return False

