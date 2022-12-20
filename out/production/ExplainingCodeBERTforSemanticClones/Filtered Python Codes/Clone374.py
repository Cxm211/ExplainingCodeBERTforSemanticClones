def ping(self, host) :
	res = False
	ping_param = "-n 1" if system_name().lower() == "windows" else "-c 1"
	resultado = os.popen("ping " + ping_param + " " + host).read()
	if "TTL=" in resultado :
		res = True
	return res


def ping(host) :
	import subprocess, platform
	ping_str = "-n 1" if platform.system().lower() == "windows" else "-c 1"
	args = "ping " + " " + ping_str + " " + host
	need_sh = False if platform.system().lower() == "windows" else True
	return subprocess.call(args, shell = need_sh) == 0

