def reverse(s) :
	rev = [_t for _t in s]
	t = ''
	while len(rev) ! = 0 :
		t += rev.pop()
	return t


def reverse(text) :
	a = ""
	l = len(text)
	while (l > = 1) :
		a += text [l - 1]
		l -= 1
	return a

