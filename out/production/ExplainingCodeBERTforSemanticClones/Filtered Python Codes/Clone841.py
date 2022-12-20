def base_and_num(number, base) :
	number = int(number)
	while number ! = 0 :
		digit = number % 10
		if digit > base :
			return False
		number = number / 10
	return True


def base_and_num(number, base) :
	for i in str(number) :
		if base < = int(i) :
			return False
	return True

