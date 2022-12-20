def __init__(self, * args, ** kwargs) :
	super(Map, self).__init__(* args, ** kwargs)
	for arg in args :
		if isinstance(arg, dict) :
			for k, v in arg.iteritems() :
				self [k] = v
	if kwargs :
		for k, v in kwargs.iteritems() :
			self [k] = v


def __init__(self, d = None, create = True) :
	if d is None :
		d = {}
	supr = super(DictWrap, self)
	supr.__setattr__('_data', d)
	supr.__setattr__('__create', create)

