def __call__(self, n) :
	if n not in self.cache :
		if n == 0 :
			self.cache [n] = 1
		else :
			self.cache [n] = n * self.__call__(n - 1)
	return self.cache [n]


def __call__(self, f) :
	def new_f() :
		print ("Entering", f.__name__)
		print ("p1=", self.p1)
		f()
		print ("Leaving", f.__name__)
	return new_f

