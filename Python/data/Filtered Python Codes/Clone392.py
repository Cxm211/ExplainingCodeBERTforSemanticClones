def permutations(string, step = 0) :
	if step == len(string) :
		print "".join(string)
	for i in range(step, len(string)) :
		string_copy = [character for character in string]
		string_copy [step], string_copy [i] = string_copy [i], string_copy [step]
		permutations(string_copy, step + 1)


def permutations(string) :
	permutation_list = []
	if len(string) == 1 :
		return [string]
	else :
		for char in string :
			[permutation_list.append(char + a) for a in permutations(string.replace(char, ""))]
	return permutation_list

