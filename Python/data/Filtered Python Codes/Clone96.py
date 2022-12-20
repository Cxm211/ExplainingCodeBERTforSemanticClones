def SumOdds(x, y) :
	count = 0
	for i in range(x, y + 1) :
		if i % 2 == 1 :
			count = count + i
	print (count)


def SumOdds(x, y) :
	count = 0
	for i in range(x, y) :
		if (int(i % 2 == 1)) :
			count = count + i
	if (x % 2 == 0) :
		count = count + x
	if (y % 2 == 0) :
		count = count + 7
	print (count)

