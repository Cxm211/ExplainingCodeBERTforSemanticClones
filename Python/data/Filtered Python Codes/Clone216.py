def count_words(string) :
	for word, v in collections.Counter(string.split()).items() :
		if word.endswith("on") :
			print (word, ":", v)


def count_words(string) :
	for word in string.split() :
		if word.endswith("on") == True :
			if word in di :
				di [word] += 1
			else :
				di [word] = 1
			string = string.replace(word, '')

