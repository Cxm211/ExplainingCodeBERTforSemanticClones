def toc(self) :
	self.tend = self.get_time()
	if self.indentation :
		if len(self.tstart) > 0 :
			self.elapsed = self.tend - self.tstart.pop()
		else :
			self.elapsed = None
	else :
		self.elapsed = self.tend - self.tstart


def toc() :
	import time
	if 'startTime_for_tictoc' in globals() :
		print "Elapsed time is " + str(time.time() - startTime_for_tictoc) + " seconds."
	else :
		print "Toc: start time not set"

