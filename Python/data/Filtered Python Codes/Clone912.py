def countWords(s) :
	d = {}
	for word in s.split() :
		try :
			d [word] += 1
		except KeyError :
			d [word] = 1
	return d


def countWords(arg) :
	dd = co.defaultdict(int)
	for i in arg.split() :
		dd [i] += 1
	return dd

