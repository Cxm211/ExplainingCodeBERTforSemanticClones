def remove_element(value, array) :
	shift = 0
	for index in xrange(len(array)) :
		try :
			array [index] = array [index + shift]
			while array [index] == value :
				shift += 1
				array [index] = array [index + shift]
		except IndexError :
			array [index] = None


def remove_element(value, array) :
	reading_idx = writing_idx = 0
	while reading_idx < len(array) :
		if array [reading_idx] ! = value :
			array [writing_idx] = array [reading_idx]
			writing_idx += 1
		reading_idx += 1
	while writing_idx < len(array) :
		array [writing_idx] = None
		writing_idx += 1

