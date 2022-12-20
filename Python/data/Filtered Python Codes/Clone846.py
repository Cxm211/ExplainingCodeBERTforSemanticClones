def reader(fd) :
	with os.fdopen(fd, bufsize = bufsize) as f :
		while True :
			data = f.read(bufsize)
			if not data :
				break
			chomp(data)


def reader(rfd) :
	while True :
		try :
			data = os.read(rfd, bufsize)
			if not data :
				break
		except OSError :
			break

