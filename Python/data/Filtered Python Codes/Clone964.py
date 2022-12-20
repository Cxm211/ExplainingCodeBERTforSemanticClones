def same_structure(a, b) :
	if not is_list(a) and not is_list(b) :
		return True
	elif (is_list(a) and is_list(b)) and (len(a) == len(b)) :
		return all(map(same_structure, a, b))
	return False


def same_structure(a, b) :
	if len(a) ! = len(b) :
		return False
	return all(is_list(x) and is_list(y) and same_structure(x, y) or
	not is_list(x) and not is_list(y) for x, y in zip(a, b))

