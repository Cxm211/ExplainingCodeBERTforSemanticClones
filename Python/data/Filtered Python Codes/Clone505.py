def execute(cmd) :
	popen = subprocess.Popen(cmd, stdout = subprocess.PIPE, universal_newlines = True)
	for stdout_line in iter(popen.stdout.readline, "") :
		yield stdout_line
	popen.stdout.close()
	return_code = popen.wait()
	if return_code :
		raise subprocess.CalledProcessError(return_code, cmd)


def execute(command) :
	popen = subprocess.Popen(command, stdout = subprocess.PIPE, bufsize = 1)
	lines_iterator = iter(popen.stdout.readline, b"")
	while popen.poll() is None :
		for line in lines_iterator :
			nline = line.rstrip()
			print(nline.decode("latin"), end = "\r\n", flush = True)

