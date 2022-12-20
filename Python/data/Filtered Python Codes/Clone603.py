def myfun(my_list, n, par1 = '') :
	new_list = ([my_fun2(i, j) for j in range(n)] for i in range(n))
	if par1 ! = '' :
		new_list = filter(eq(par1), new_list)
	return list(new_list)


def myfun(my_list, n, par1 = '') :
	if par1 == '' :
		outer = range(n)
	else :
		outer = (i for i in range(n) if my_fun2(i, n) == par1)
	return [[my_fun2(i, j) for j in range(n)] for i in outer]

