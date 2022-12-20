def acquire_data(filename_or_list) :
	try :
		with open(filename_or_list) as f :
			data = list(f)
	except TypeError :
		data = list(filename_or_list)


def acquire_data(list_or_filename) :
	if isinstance(list_or_filename, str) :
		with open(list_or_filename, "r") as f :
			return acquire_data_from_file(f)
	else :
		return acquire_data_from_list(list_or_filename)

