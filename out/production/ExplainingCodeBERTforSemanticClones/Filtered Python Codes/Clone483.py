def sanity_check(b, true_func, false_func) :
	if b :
		logfunc = log.debug
		execfunc = true_func
	else :
		logfunc = log.warning
		execfunc = false_func
	logfunc('exec: %s', execfunc.__name__)
	execfunc()


def sanity_check(test, name = 'undefined', ontrue = None, onfalse = None) :
	if test :
		log.debug(name)
		if ontrue is not None :
			ontrue()
	else :
		log.warn(name)
		if onfalse is not None :
			onfalse()

