def Max(s) :
	if len(s) == 1 :
		return s [0]
	else :
		m = Max(s [1 :])
		if m > s [0] :
			return m
		else :
			return s [0]


def Max(lst) :
	l = len(lst)
	if l > 1 :
		mid = l / 2
		m1 = Max(lst [: mid])
		m2 = Max(lst [mid :])
		return m1 if m1 > m2 else m2
	return lst [0]

