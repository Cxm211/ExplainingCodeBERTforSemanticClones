def reverse(text) :
	reversed_text = ""
	for n in range(len(text)) :
		reversed_text += text [- 1 - n]
	return reversed_text


def reverse(text) :
	lst = []
	count = 1
	for i in range(0, len(text)) :
		lst.append(text [len(text) - count])
		count += 1
	lst = ''.join(lst)
	return lst

