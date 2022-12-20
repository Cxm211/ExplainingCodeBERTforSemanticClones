def median(data) :
	new_list = sorted(data)
	if len(new_list) % 2 > 0 :
		return new_list [len(new_list) / 2]
	elif len(new_list) % 2 == 0 :
		return (new_list [(len(new_list) / 2)] + new_list [(len(new_list) / 2) - 1]) / 2.0


def median(lst) :
	quotient, remainder = divmod(len(lst), 2)
	if remainder :
		return sorted(lst) [quotient]
	return sum(sorted(lst) [quotient - 1 : quotient + 1]) / 2.

