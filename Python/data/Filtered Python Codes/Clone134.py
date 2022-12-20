def find_nth(haystack, needle, n = 1) :
	if (hasattr(needle, 'finditer')) :
		matches = needle.finditer(haystack)
	else :
		matches = re.finditer(re.escape(needle), haystack)
	start_here = itertools.dropwhile(lambda x : x [0] < n, enumerate(matches, 1))
	try :
		return next(start_here) [1].start()
	except StopIteration :
		return - 1


def find_nth(s, x, n, i = 0) :
	i = s.find(x, i)
	if n == 1 or i == - 1 :
		return i
	else :
		return find_nth(s, x, n - 1, i + len(x))

