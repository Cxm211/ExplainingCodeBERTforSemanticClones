def div3() :
	divlist = []
	num = range(1, 10)
	for n in num :
		if n % 3 == 0 :
			for _ in xrange(20) :
				divlist.append(random.randint(0, 10))
	print divlist


def div3() :
	divlist = []
	num = range(1, 10)
	if (num % 3 == 0) :
		for _ in xrange(20) :
			divlist.append(random.randint(0, 10))
		print divlist

