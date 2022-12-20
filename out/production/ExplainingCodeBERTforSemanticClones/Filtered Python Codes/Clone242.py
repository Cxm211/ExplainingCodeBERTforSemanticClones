def flatten_list(seq) :
	if not seq :
		return []
	elif isinstance(seq [0], list) :
		return (flatten_list(seq [0]) + flatten_list(seq [1 :]))
	else :
		return [seq [0]] + flatten_list(seq [1 :])


def flatten_list(nested_list) :
	nested_list = deepcopy(nested_list)
	while nested_list :
		sublist = nested_list.pop(0)
		if isinstance(sublist, list) :
			nested_list = sublist + nested_list
		else :
			yield sublist

