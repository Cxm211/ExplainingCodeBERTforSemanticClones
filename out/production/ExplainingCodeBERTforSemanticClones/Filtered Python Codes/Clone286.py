def pattern_match(sequence, patterns) :
	if len(sequence) == len(patterns) :
		return all(item in my_set for item, my_set in zip(sequence, patterns))
	else :
		return False


def pattern_match(sequence, patterns) :
	seq = set(sequence)
	u = set()
	for pattern in patterns :
		u.update(pattern)
	return seq.issubset(u)

