def fib(n) :
	global call_count
	call_count = call_count + 1
	if n < = 1 :
		return 1
	else :
		return fib(n - 1) + fib(n - 2)


def fib(n) :
	if n == 1 :
		return (1);
	elif n == 0 :
		return (0);
	else :
		return fib(n - 1) + fib(n - 2);

