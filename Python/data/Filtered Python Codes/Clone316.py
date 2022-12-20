def count_chars(p) :
	indx = collections.Counter()
	with open(p) as f :
		for line in f :
			for c in line :
				indx [c] += 1
	print indx


def count_chars(p) :
	d = collections.defaultdict(int)
	for letter in open(p).read() :
		d [letter] += 1
	return d

