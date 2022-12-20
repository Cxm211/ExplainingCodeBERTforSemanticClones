def anti_vowel(text) :
	newText = text [:]
	for i in 'aeiouAEIOU' :
		newText = newText.replace(i, '')
	print (newText)
	return newText


def anti_vowel(text) :
	l = []
	s = ""
	for i in text :
		l.append(i)
	new_l = []
	for i in l :
		if i not in "aeiouAEIOU" :
			new_l.append(i)
	print ("".join(new_l))

