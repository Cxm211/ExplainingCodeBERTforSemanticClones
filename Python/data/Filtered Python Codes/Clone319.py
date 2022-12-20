def __init__(self, maxlen, items = None) :
	self._maxlen = maxlen
	self.d = OrderedDict()
	if items :
		for k, v in items :
			self [k] = v


def __init__(self, maxlen, * a, ** k) :
	self.maxlen = maxlen
	self.d = dict(* a, ** k)
	while len(self) > maxlen :
		self.popitem()

