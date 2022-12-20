def collatz(starting_value) :
	value = starting_value
	while value ! = 1 :
		value = (3 * value + 1) if value % 2 else (value / / 2)
		yield value


def collatz(number) :
	while number ! = 1 :
		if number % 2 == 0 :
			number //= 2
		else :
			number = 3 * number + 1
		print (number)

