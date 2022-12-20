def mkdir_p(path) :
	try :
		os.makedirs(path)
	except OSError as exc :
		if exc.errno == errno.EEXIST and os.path.isdir(path) :
			pass
		else :
			raise


def mkdir_p(filename) :
	try :
		folder = os.path.dirname(filename)
		if not os.path.exists(folder) :
			os.makedirs(folder)
		return True
	except :
		return False

