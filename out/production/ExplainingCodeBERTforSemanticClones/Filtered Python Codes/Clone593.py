def decorator(function) :
	if after :
		return afterDecorator(function, event)
	else :
		return beforeDecorator(function, event)


def decorator(f) :
	@ wraps(f)
	def wrapped(* args, ** kargs) :
		event.fire(* args, ** kargs)
		return f(* args, ** kargs)
	return wrapped

