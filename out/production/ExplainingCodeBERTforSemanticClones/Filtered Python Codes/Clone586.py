def reverse(s) :
	t = - 1
	s2 = ''
	while abs(t) < len(s) + 1 :
		s2 = s2 + s [t]
		t = t - 1
	return s2


def reverse(text) :
	a = ""
	l = len(text)
	while (l > = 1) :
		a += text [l - 1]
		l -= 1
	return a

