def repeat(a, n, already_ran = 0) :
	if n == 0 :
		print (a * (n + already_ran))
	else :
		print (a * (n + already_ran))
		repeat(a, n - 1, already_ran + 1)


def repeat(string, times, lines_left = None) :
	print (string * times)
	if (lines_left is None) :
		lines_left = times
	lines_left = lines_left - 1
	if (lines_left > 0) :
		repeat(string, times, lines_left)

