def split_at_first_false(pred, seq) :
	for i, item in enumerate(seq) :
		if not pred(item) :
			return seq [: i], seq [i :]


def split_at_first_false(pred, seq) :
	index = 0
	while index < len(seq) :
		if not pred(seq [index]) :
			return seq [: index], seq [index + 1 :]
		index += 1

