def find_word_horizontal(crosswords, word) :
	input_list = list(word)
	output_list = []
	row_index = - 1
	column_index = 0
	for outer_index, sublist in enumerate(crosswords) :
		for inner_index in xrange(0, (len(sublist) - len(input_list) + 1)) :
			if sublist [inner_index : inner_index + len(input_list)] == input_list :
				return [outer_index, inner_index]


def find_word_horizontal(crosswords, word) :
	for row_index, row in enumerate(crosswords) :
		print ('input: ', row_index, row)
		row_string = ''.join(row)
		print ('joined row: ', row_string)
		column_index = row_string.find(word)
		if (column_index > - 1) :
			return [row_index, column_index]

