def __op__(self, op, args) :
	try :
		other = args [0]
	except IndexError :
		other = None
	print "%s %s %s" % (self, op, other)
	self, other = coerce(self, other)
	return getattr(self, op)(* args)


def __op__(self, op, other) :
	if other is None :
		print "%s(%s)" % (op, self)
		self, other = coerce(self, 0.0)
		return getattr(self, op)()
	else :
		print "%s %s %s" % (self, op, other)
		self, other = coerce(self, other)
		return getattr(self, op)(other)

