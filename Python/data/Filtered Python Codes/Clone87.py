def default(self, o) :
	if isinstance(o, MyClass) :
		return o.__repr__()
	else :
		return super(self, o)


def default(self, obj) :
	if isinstance(obj, TYPES.values()) :
		key = '__%s__' % obj.__class__.__name__
		return {key : obj.__dict__}
	return json.JSONEncoder.default(self, obj)

