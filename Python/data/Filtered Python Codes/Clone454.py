def split_at_first_false(pred, seq) :
	if not isinstance(seq, list) :
		seq = list(seq)
	for i, x in enumerate(seq) :
		if not pred(x) :
			return seq [: i], seq [i :]
	return seq, []


def split_at_first_false(pred, seq) :
	pos = 0
	for item in seq :
		if not pred(item) :
			return seq [: pos], seq [pos :]
		pos += 1

