def pascal(n) :
	array = [None for y in range(n)]
	row = [1]
	array [0] = row
	k = [0]
	for x in range(max(n, 0) - 1) :
		row = [l + r for l, r in zip(row + k, k + row)]
		array [x + 1] = row
	return array


def pascal(n) :
	a = [[int(i == 0) for j in range(n)] for i in range(n)]
	for i in range(n) :
		for j in range(1, 1 + i) :
			a [j] [i] = a [j] [i - 1] + a [j - 1] [i - 1]
	for line in a : print (line)

