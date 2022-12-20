def seq(start, end, step) :
	if step == 0 :
		raise ValueError("step must not be 0")
	sample_count = int(abs(end - start) / step)
	return itertools.islice(itertools.count(start, step), sample_count)


def seq(start, stop, step = 1, digit = 0) :
	x = float(start)
	v = []
	while x < = stop :
		v.append(round(x, digit))
		x += step
	return v

