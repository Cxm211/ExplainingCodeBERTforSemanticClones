def modify_duplicates_ordered(original) :
	result = []
	for val in original :
		while val in result :
			val += 0.0001
		result.append(val)


def modify_duplicates_ordered(original) :
	D = {}
	result = [];
	for value in original : D [value] = 0;
	for value in original :
		result.append(value + D [value] * 0.0001);
		D [value] += 1;
	return result;

