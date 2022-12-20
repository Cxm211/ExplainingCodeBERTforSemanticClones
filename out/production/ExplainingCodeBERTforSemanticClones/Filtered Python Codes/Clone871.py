def sublist(a, b) :
	last = 0
	for el_a in a :
		if el_a in b [last :] :
			last = b [last :].index(el_a)
		else :
			return False
	return True


def sublist(a, b) :
	if not a :
		return True
	for k in range(len(b)) :
		if a [0] == b [k] :
			return sublist(a [1 :], b [k + 1 :])
	return False

