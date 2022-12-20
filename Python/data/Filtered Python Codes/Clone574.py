def reverse(string) :
	tmp = ""
	for i in range(1, len(string) + 1) :
		tmp += string [len(string) - i]
	return tmp


def reverse(text) :
	answer = ""
	while text :
		answer = text [0] + answer
		text = text [1 :]
	return answer

