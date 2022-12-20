def print_table(data, cols, wide) :
	n, r = divmod(len(data), cols)
	pat = '{{:{}}}'.format(wide)
	line = '\n'.join(pat * cols for _ in range(n))
	last_line = pat * r
	print (line.format(* data))
	print (last_line.format(* data [n * cols :]))


def print_table(table) :
	longest_cols = [
	(max([len(str(row [i])) for row in table]) + 3) for i in range(len(table [0]))
	]
	row_format = "".join(["{:>" + str(longest_col) + "}" for longest_col in longest_cols])
	for row in table :
		print (row_format.format(* row))

