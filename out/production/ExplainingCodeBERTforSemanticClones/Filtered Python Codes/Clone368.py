def median(lst) :
	sortedLst = sorted(lst)
	lstLen = len(lst)
	index = (lstLen - 1) / / 2
	if (lstLen % 2) :
		return sortedLst [index]
	else :
		return (sortedLst [index] + sortedLst [index + 1]) / 2.0


def median(lst) :
	quotient, remainder = divmod(len(lst), 2)
	if remainder :
		return sorted(lst) [quotient]
	return sum(sorted(lst) [quotient - 1 : quotient + 1]) / 2.

