def shift_n_letters(letter, n) :
	char_array = [chr(i) for i in range(97, 123)]
	result = ""
	for ch in list(message) :
		index = (char_array.index(ch) + n) % 26
		result += char_array [index]
	return result


def shift_n_letters(letter, n) :
	n_ = n % 26
	result = chr(ord(letter) + n_)
	if ord(result) > 122 :
		result = chr(ord(result) - 26)
	return result

