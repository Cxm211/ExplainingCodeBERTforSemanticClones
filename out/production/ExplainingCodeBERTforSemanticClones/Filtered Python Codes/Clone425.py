def __next__(self) :
	try :
		return next(self.__iter)
	except StopIteration :
		self.__iter = None
		raise


def __next__(self) :
	if self.state == 10 :
		raise StopIteration
	self.state += 1
	return self.state - 1

