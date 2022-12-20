def num_input(s) :
	while True :
		try :
			return decimal.Decimal(raw_input(s))
		except decimal.InvalidOperation, e :
			print e.message


def num_input(prompt, error) :
	s = raw_input(prompt)
	for t in (int, float, complex) :
		try : return t(s)
		except ValueError : pass
	print error
	return num_input(prompt, error)

