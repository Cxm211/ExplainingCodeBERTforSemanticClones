def run(cmd, timeout_sec) :
	proc = Popen(shlex.split(cmd), stdout = PIPE, stderr = PIPE)
	timer = Timer(timeout_sec, proc.kill)
	try :
		timer.start()
		stdout, stderr = proc.communicate()
	finally :
		timer.cancel()


def run(cmd, timeout_sec) :
	proc = subprocess.Popen(shlex.split(cmd), stdout = subprocess.PIPE, stderr = subprocess.PIPE)
	timeout = {"value" : False}
	timer = Timer(timeout_sec, kill_proc, [proc, timeout])
	timer.start()
	stdout, stderr = proc.communicate()
	timer.cancel()
	return proc.returncode, stdout.decode("utf-8"), stderr.decode("utf-8"), timeout ["value"]

