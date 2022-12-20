def acquire_data(arg) :
	if isinstance(arg, file) :
		data = arg.read()
	elif isinstance(arg, basestring) :
		data = open(arg, 'r').read()
	else :
		data = arg


def acquire_data(filename_or_list) :
	try :
		with open(filename_or_list) as f :
			data = list(f)
	except TypeError :
		data = list(filename_or_list)

