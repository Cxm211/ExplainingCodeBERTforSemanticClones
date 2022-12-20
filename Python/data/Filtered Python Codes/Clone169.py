def get_most_ooo_word(words) :
	words = words [0].split()
	most = [words [0]]
	for word in words [1 :] :
		if word.count('o') > most [0].count('o') :
			most = [word]
		elif word.count('o') == most [0].count('o') :
			most.append(word)
	return most


def get_most_ooo_word(words) :
	if type(words) == list and len(words) > 0 :
		words = words [0].split()
	else :
		words = words.split()
	k = words [0]
	for i in range(1, len(words) - 1) :
		if words [i].count('o') > words [i - 1].count('o') :
			k = words [i]
	return (k)

