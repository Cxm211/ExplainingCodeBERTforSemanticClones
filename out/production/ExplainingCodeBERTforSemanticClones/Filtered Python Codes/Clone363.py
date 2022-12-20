def median(data) :
	new_list = sorted(data)
	if len(new_list) % 2 > 0 :
		return new_list [len(new_list) / 2]
	elif len(new_list) % 2 == 0 :
		return (new_list [(len(new_list) / 2)] + new_list [(len(new_list) / 2) - 1]) / 2.0


def median(lst) :
	n = len(lst)
	if n < 1 :
		return None
	if n % 2 == 1 :
		return sorted(lst) [n / / 2]
	else :
		return sum(sorted(lst) [n / / 2 - 1 : n / / 2 + 1]) / 2.0

