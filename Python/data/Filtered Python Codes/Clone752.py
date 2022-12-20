def fib(n) :
	if n == 2 :
		try :
			fib.two_count += 1
		except AttributeError :
			fib.two_count = 1
	if n == 0 or n == 1 :
		return n
	else :
		return fib(n - 1) + fib(n - 2)


def fib(n) :
	if n == 0 or n == 1 :
		return n, 0
	else :
		f1, count1 = fib(n - 1)
		f2, count2 = fib(n - 2)
		sum_counts = count1 + count2
		if n == 2 :
			sum_counts = 1
		return f1 + f2, sum_counts

