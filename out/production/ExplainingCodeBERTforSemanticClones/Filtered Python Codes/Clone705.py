def release(self) :
	if self.released :
		return False
	for sig in self.signals :
		signal.signal(sig, self.original_handlers [sig])
	self.released = True
	return True


def release(self) :
	if self.released :
		return False
	signal.signal(self.sig, self.original_handler)
	self.released = True
	return True

