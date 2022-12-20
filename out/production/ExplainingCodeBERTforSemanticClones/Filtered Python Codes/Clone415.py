def pop(self, key = None, default = object()) :
	if key is None :
		return self.popitem()
	return super(MyOrderedDict, self).pop(self.keys() [key], default = default)


def pop(self, ind = - 1) :
	try :
		obj = list.__getitem__(self, ind)
	except (IndexError, TypeError) :
		obj = self._index [ind]
		ind = list.index(self, obj)
	self._delindex(obj)
	return list.pop(self, ind)

