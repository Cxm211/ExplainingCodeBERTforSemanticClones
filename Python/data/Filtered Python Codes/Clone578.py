def reverse(string) :
	tmp = ""
	for i in range(1, len(string) + 1) :
		tmp += string [len(string) - i]
	return tmp


def reverse(text) :
	a = ""
	l = len(text)
	while (l > = 1) :
		a += text [l - 1]
		l -= 1
	return a

