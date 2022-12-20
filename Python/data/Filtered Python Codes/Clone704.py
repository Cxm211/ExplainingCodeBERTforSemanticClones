def __enter__(self) :
	self.interrupted = False
	self.released = False
	for sig in self.signals :
		self.original_handlers [sig] = signal.getsignal(sig)
		signal.signal(sig, self.handler)
	return self


def __enter__(self) :
	self.interrupted = False
	self.released = False
	self.original_handler = signal.getsignal(self.sig)
	def handler(signum, frame) :
		self.release()
		self.interrupted = True
	signal.signal(self.sig, handler)
	return self

