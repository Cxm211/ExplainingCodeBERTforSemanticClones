def reverse(string) :
	tmp = ""
	for i in range(1, len(string) + 1) :
		tmp += string [len(string) - i]
	return tmp


def reverse(s) :
	rev = [_t for _t in s]
	t = ''
	while len(rev) ! = 0 :
		t += rev.pop()
	return t

