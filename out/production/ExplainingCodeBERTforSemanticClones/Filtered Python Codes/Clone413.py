def __init__(self, * args, ** kwds) :
	super(MyOrderedDict, self).__init__()
	if len(args) > 0 :
		for i in args [0] :
			super(MyOrderedDict, self).__setitem__(i.id, i)


def __init__(self, items, attrs) :
	super(IndexedList, self).__init__(items)
	self._attrs = tuple(attrs)
	self._index = {}
	_add = self._addindex
	for obj in self :
		_add(obj)

