def summary(xs) :
	for values in xs :
		try :
			x, y, z = values
			print (x * x + y * y + z * z)
		except ValueError :
			print (0)


def summary(xs) :
	for item in xs :
		s = 0
		for value in item :
			s += value ** 2
		print (s)

