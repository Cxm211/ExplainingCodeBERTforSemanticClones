def __call__(self, file) :
	hash = self.algorithm()
	with open(file, 'rb') as f :
		for chunk in iter(lambda : f.read(4096), '') :
			hash.update(chunk)
	return hash.hexdigest()


def __call__(self, f) :
	def new_f() :
		print ("Entering", f.__name__)
		print ("p1=", self.p1)
		f()
		print ("Leaving", f.__name__)
	return new_f

