def median(data) :
	new_list = sorted(data)
	if len(new_list) % 2 > 0 :
		return new_list [len(new_list) / 2]
	elif len(new_list) % 2 == 0 :
		return (new_list [(len(new_list) / 2)] + new_list [(len(new_list) / 2) - 1]) / 2.0


def median(lst) :
	sortedLst = sorted(lst)
	lstLen = len(lst)
	index = (lstLen - 1) / / 2
	if (lstLen % 2) :
		return sortedLst [index]
	else :
		return (sortedLst [index] + sortedLst [index + 1]) / 2.0

