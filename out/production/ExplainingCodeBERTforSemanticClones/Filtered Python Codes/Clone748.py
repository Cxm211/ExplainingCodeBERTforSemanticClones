def listFunc(lst) :
	if len(lst) == 0 : return ''
	if len(lst) == 1 : return lst [0]
	firstPart = lst [: - 1]
	retFirst = ", ".join(firstPart)
	retSecond = ", and " + lst [- 1]
	return retFirst + retSecond;


def listFunc(List) :
	if len(List) == 0 : return ''
	if len(List) == 1 : return List [0]
	value = List [0]
	for item in List [1 : - 1] :
		value = value + ', ' + item
	return value + ', and ' + List [- 1]

