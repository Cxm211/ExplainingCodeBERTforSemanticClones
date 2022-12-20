def __setattr__(self, name, value) :
	if not hasattr(self, name) :
		raise AttributeError("Model instances do not accept arbitrary attributes")
	else :
		object.__setattr__(self, name, value)


def __setattr__(self, key, value) :
	setIsOK = False
	for item in self.__List :
		if key == item :
			setIsOK = True
	if setIsOK == True :
		object.__setattr__(self, key, value)
	else :
		raise TypeError("%r has no attributes %r" % (self, key))

