def print_data(items) :
	i = 0
	while i < len(items) :
		print items [i]
		i += 1


def print_data(items) :
	it = iter(items)
	while True :
		try :
			print next(it)
		except StopIteration :
			break

