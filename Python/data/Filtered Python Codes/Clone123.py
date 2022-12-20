def next(self) :
	try :
		self._doc = self._cursor.next()
	except StopIteration :
		self._doc = None
	return self


def next(self) :
	if self.cursor.hasNext() :
		data = self.cursor.next()
		self.set_data(data)
		return self
	else :
		raise StopIteration

