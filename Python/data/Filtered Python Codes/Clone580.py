def reverse(text) :
	answer = ""
	while text :
		answer = text [0] + answer
		text = text [1 :]
	return answer


def reverse(text) :
	reversed_text = ""
	for n in range(len(text)) :
		reversed_text += text [- 1 - n]
	return reversed_text

