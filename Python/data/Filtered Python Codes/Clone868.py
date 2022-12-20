def run(self) :
	try :
		self.run2()
	finally :
		self.cleanup()


def run(self) :
	if TEST_THREAD_EXCEPTION :
		raise RuntimeError('OOPS!')
	print ('  other thread now running...')
	time.sleep(2)

