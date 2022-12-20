def reverse(text) :
	answer = ""
	while text :
		answer = text [0] + answer
		text = text [1 :]
	return answer


def reverse(text) :
	lst = []
	count = 1
	for i in range(0, len(text)) :
		lst.append(text [len(text) - count])
		count += 1
	lst = ''.join(lst)
	return lst

