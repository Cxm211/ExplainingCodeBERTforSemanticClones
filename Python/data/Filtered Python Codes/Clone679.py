def two_pair(ranks) :
	newlist = []
	for i in set(ranks) :
		if ranks.count(i) == 2 :
			newlist.append(i)
	newlist.sort(reverse = True)
	newlist = tuple(newlist)
	return None if newlist == () else newlist


def two_pair(ranks) :
	if len(set(ranks)) ! = 3 :
		return None
	pairs = []
	preceding = None
	for card in ranks :
		if card == preceding :
			pairs.append(card)
		preceding = card
	return tuple(pairs)

