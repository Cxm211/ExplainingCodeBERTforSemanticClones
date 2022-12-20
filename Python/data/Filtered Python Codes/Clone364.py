def median(data) :
	new_list = sorted(data)
	if len(new_list) % 2 > 0 :
		return new_list [len(new_list) / 2]
	elif len(new_list) % 2 == 0 :
		return (new_list [(len(new_list) / 2)] + new_list [(len(new_list) / 2) - 1]) / 2.0


def median(l) :
	half = len(l) / / 2
	l.sort()
	if not len(l) % 2 :
		return (l [half - 1] + l [half]) / 2.0
	return l [half]

