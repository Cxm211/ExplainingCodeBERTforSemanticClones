def find_mount_point(path) :
	path = os.path.abspath(path)
	orig_dev = os.stat(path).st_dev
	while path ! = '/' :
		dir = os.path.dirname(path)
		if os.stat(dir).st_dev ! = orig_dev :
			break
		path = dir
	return path


def find_mount_point(path) :
	if not os.path.islink(path) :
		path = os.path.abspath(path)
	elif os.path.islink(path) and os.path.lexists(os.readlink(path)) :
		path = os.path.realpath(path)
	while not os.path.ismount(path) :
		path = os.path.dirname(path)
		if os.path.islink(path) and os.path.lexists(os.readlink(path)) :
			path = os.path.realpath(path)
	return path

