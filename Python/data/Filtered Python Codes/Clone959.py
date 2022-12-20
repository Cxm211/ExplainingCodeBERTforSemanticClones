def md5sum(filename) :
	d = hashlib.md5()
	for buf in chunks(filename, 128) :
		d.update(buf)
	return d.hexdigest()


def md5sum(filename) :
	with open(filename, mode = 'rb') as f :
		d = hashlib.md5()
		while True :
			buf = f.read(4096)
			if not buf :
				break
			d.update(buf)
		return d.hexdigest()

