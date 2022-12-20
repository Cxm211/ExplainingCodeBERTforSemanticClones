def n_letter_dictionary(string) :
	result = {}
	for key, group in groupby(sorted(string.split(), key = lambda x : len(x)), lambda x : len(x)) :
		result [key] = list(group)
	return result


def n_letter_dictionary(my_string) :
	my_dict = {}
	for word in my_string.split() :
		word_length = len(word)
		if word_length in my_dict :
			if word not in my_dict [word_length] :
				my_dict [word_length].append(word)
		else :
			my_dict [word_length] = [word]

