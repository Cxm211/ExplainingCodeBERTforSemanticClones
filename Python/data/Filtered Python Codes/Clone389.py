def __getitem__(self, key) :
	try :
		temp = int(key)
		return "I am a number"
	except ValueError :
		return self.get(key, None)


def __getitem__(self, key) :
	if isinstance(numbers.Number) :
		print "I am a number"
	else :
		return super(cls, self).__getitem__(key)

