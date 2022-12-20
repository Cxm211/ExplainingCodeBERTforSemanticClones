def is_square(apositiveint) :
	x = apositiveint / / 2
	seen = set([x])
	while x * x ! = apositiveint :
		x = (x + (apositiveint / / x)) / / 2
		if x in seen : return False
		seen.add(x)
	return True

def is_square(integer) :
	root = math.sqrt(integer)
	if int(root + 0.5) ** 2 == integer :
		return True
	else :
		return False
