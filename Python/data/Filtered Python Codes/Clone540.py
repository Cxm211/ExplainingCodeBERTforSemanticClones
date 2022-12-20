def touch(fname) :
	if os.path.exists(fname) :
		os.utime(fname, None)
	else :
		open(fname, 'a').close()


def touch(file_name) :
	if not os.path.exists(file_name) :
		return
	try :
		os.utime(file_name, None)
	except Exception :
		open(file_name, 'a').close()

