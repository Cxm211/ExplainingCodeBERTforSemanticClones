def transformFactorList(factorList) :
	twos = [x for x in factorList if x == 2]
	rest = [x for x in factorList if x ! = 2]
	if twos :
		rest.insert(0, 2 if len(twos) == 1 else "2 ^ %d" % len(twos))
	return rest


def transformFactorList(factorList) :
	oldsize = len(factorList)
	factorList = [f for f in factorList if f ! = 2]
	num2s = oldsize - len(factorList)
	if num2s == 0 :
		return []
	if num2s == 1 :
		return [2] + factorList
		return ['2 ^ %s' % num2s] + [factorList]

