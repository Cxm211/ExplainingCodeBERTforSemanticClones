def find(self, key) :
	if key == self.key :
		return self.data
	if key < self.key and self.left :
		return self.left.find(key)
	if key > self.key and self.right :
		return self.right.find(key)
	raise KeyError("No such thing")


def find(self, key) :
	gen = self._find(key)
	try :
		yield gen.next()
	except StopIteration :
		raise KeyError(key)
	for item in gen :
		yield item

