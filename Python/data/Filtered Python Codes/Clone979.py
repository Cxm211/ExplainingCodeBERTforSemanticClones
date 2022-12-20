def wrapper(arg1) :
	try :
		return func(arg1)
	except MyException as e :
		print "Error:", e.args


def wrapper(arg1) :
	errors = []
	result = func(arg1)
	for k, v in result.iteritems() :
		error_nr = v % 2
		if error_nr > 0 :
			errors.append((k, v, error_nr))
	if errors :
		raise MyException, errors
	return result

