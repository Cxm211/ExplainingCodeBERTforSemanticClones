def split(s, n) :
	new_list = []
	for i in range(0, len(s), n) :
		new_list.append(s [i : i + n])
	return new_list


def split(s, n) :
	if len(s) < n :
		return []
	elif len(s) == n :
		return [s]
	else :
		return split(s [: n], n) + split(s [n :], n)

