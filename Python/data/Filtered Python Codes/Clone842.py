def __call__(self, * args, ** kwargs) :
	if not hasattr(self, 'fn') :
		self.fn = args [0]
		return self
	print "In my decorator before call, with arg %s" % self.argument
	self.fn(* args, ** kwargs)
	print "In my decorator after call, with arg %s" % self.argument


def __call__(self, fn) :
	@ functools.wraps(fn)
	def decorated(* args, ** kwargs) :
		print "In my decorator before call, with arg %s" % self.arg
		fn(* args, ** kwargs)
		print "In my decorator after call, with arg %s" % self.arg
	return decorated

