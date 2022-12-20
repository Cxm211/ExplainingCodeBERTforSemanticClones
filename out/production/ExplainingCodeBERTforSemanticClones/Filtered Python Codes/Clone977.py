def wrapper(arg1) :
	result = func(arg1)
	for err in findError(result) :
		errors.append(err)
	print errors
	return result


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

