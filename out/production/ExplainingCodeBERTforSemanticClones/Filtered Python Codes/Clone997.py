def summary(xs) :
	for item in xs :
		try :
			yield sum(i ** 2 for i in item)
		except ValueError :
			yield 0


def summary(xs) :
	for item in xs :
		s = 0
		for value in item :
			s += value ** 2
		print (s)

