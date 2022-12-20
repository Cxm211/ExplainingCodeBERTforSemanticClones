def run(cmd, timeout_sec) :
	proc = Popen(shlex.split(cmd), stdout = PIPE, stderr = PIPE)
	timer = Timer(timeout_sec, proc.kill)
	try :
		timer.start()
		stdout, stderr = proc.communicate()
	finally :
		timer.cancel()


def run(self) :
	self.process = subprocess.Popen(self.cmd, stdout = self.outFile, stderr = self.errFile)
	while (self.process.poll() is None and self.timeout > 0) :
		time.sleep(1)
		self.timeout -= 1
	if not self.timeout > 0 :
		self.process.terminate()
		self.timed_out = True
	else :
		self.timed_out = False

