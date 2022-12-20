def median(data) :
	new_list = sorted(data)
	if len(new_list) % 2 > 0 :
		return new_list [len(new_list) / 2]
	elif len(new_list) % 2 == 0 :
		return (new_list [(len(new_list) / 2)] + new_list [(len(new_list) / 2) - 1]) / 2.0


def median(array) :
	array = sorted(array)
	half, odd = divmod(len(array), 2)
	if odd :
		return array [half]
	return (array [half - 1] + array [half]) / 2.0

